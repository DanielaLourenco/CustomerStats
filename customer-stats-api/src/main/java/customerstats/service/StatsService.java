package customerstats.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import customerstats.model.HourlyStats;
import customerstats.model.Request;
import customerstats.repository.HourlyStatsDAO;

@Component
public class StatsService {

	@Autowired
	private IStatsService hourlyStatsService;
	@Autowired
	private HourlyStatsDAO hourlyStatsDAO;

	public ArrayList<Request> validRequestList;
	public ArrayList<Request> invalidRequestList;

	public StatsService() {		
		validRequestList = new ArrayList<Request>();
		invalidRequestList = new ArrayList<Request>();
	}

	public String retrieveStats(Integer customerId, Date date) {
		List<HourlyStats> hourlyStats = hourlyStatsDAO.retrieveStats(customerId, date);
		int countValidRequest = 0;
		int countInvalidRequest = 0;
		for(HourlyStats stats : hourlyStats) {
			countValidRequest += stats.getRequestCount();
			countInvalidRequest += stats.getInvalidCount();
		}

		String result = "{\"customerId\":\"" + customerId
				+ "\", \"date\":" + date
				+ ", \"requestCount\":\"" + countValidRequest
				+ "\", \"invalidCount\":\"" + countInvalidRequest +"\"}";

		return result;
	}

	public Integer processRequest(Request request) {
		// do something with the request
		return 201;		
	}

	// Execute every hour
	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void generateStatistics() {
		try{
			ArrayList<Integer> uniqueCustomerIds = new ArrayList<Integer>();
			// map the valid requests for customer: customerID and count
			HashMap<Integer, Integer> customerValidRequests = new HashMap<Integer, Integer>();
			for(Request request : validRequestList) {
				int customerId = request.getCustomerID();
				if(!uniqueCustomerIds.contains(customerId)) {
					uniqueCustomerIds.add(customerId);
				}

				if(!customerValidRequests.containsKey(customerId)) {
					customerValidRequests.put(customerId, 1);
				}
				else {
					customerValidRequests.put(customerId,customerValidRequests.get(customerId) + 1);
				}
			}
			// map the invalid requests for customer: customerID and count
			HashMap<Integer, Integer> customerInvalidRequests = new HashMap<Integer, Integer>();
			for(Request request : invalidRequestList) {
				int customerId = request.getCustomerID();
				if(!uniqueCustomerIds.contains(customerId)) {
					uniqueCustomerIds.add(customerId);
				}
				if(!customerInvalidRequests.containsKey(customerId)) {
					customerInvalidRequests.put(customerId, 1);
				}
				else {
					customerInvalidRequests.put(customerId,customerInvalidRequests.get(customerId) + 1);
				}
			}
			// create stats per customer
			ArrayList<HourlyStats> hourlyStatsList = new ArrayList<HourlyStats>();
			for (Integer customerId : uniqueCustomerIds) {
				HourlyStats stats = new HourlyStats();
				stats.setCustomerId(customerId);
				Timestamp time = new Timestamp(System.currentTimeMillis());
				@SuppressWarnings("deprecation")
				Integer id = time.getYear()+ time.getMonth() + time.getDay() + time.getHours()+time.getMinutes()+time.getSeconds() +time.getNanos();
				stats.setTimestamp(time);
				stats.setId(id);
				try {
					stats.setRequestCount(customerValidRequests.get(customerId));
				}
				catch(Exception e) {
					stats.setRequestCount(0);
				}

				try {
					stats.setInvalidCount(customerInvalidRequests.get(customerId));
				}
				catch(Exception e) {
					stats.setInvalidCount(0);
				}

				hourlyStatsList.add(stats);
			}	

			hourlyStatsService.saveAll(hourlyStatsList);

			// Ideally write information in a log file
			System.out.println("Stats successfuly saved at " + new Timestamp(System.currentTimeMillis()));			
		}
		catch(Exception e) {
			// Ideally write information in a log file
			System.out.println("Stats failed at " + new Timestamp(System.currentTimeMillis()));

		}
		finally {
			// clear lists
			validRequestList.clear();
			invalidRequestList.clear();
		}
	}
}
