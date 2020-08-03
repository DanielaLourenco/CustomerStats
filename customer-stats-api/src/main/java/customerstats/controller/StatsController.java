package customerstats.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import customerstats.model.Customer;
import customerstats.model.Request;
import customerstats.repository.IpBlacklistDAO;
import customerstats.repository.UserAgentBlacklistDAO;
import customerstats.service.ICustomerService;
import customerstats.service.StatsService;

@RestController
public class StatsController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IpBlacklistDAO ipBlacklistDAO;
	@Autowired
	private UserAgentBlacklistDAO uaBlacklistDAO;	
	@Autowired
	private StatsService statsService;

	@RequestMapping(value = "/stats/{id}/{date}", method = RequestMethod.GET)
	String getEmployee(@PathVariable Integer id, @PathVariable Date date){
		String a = statsService.retrieveStats(id, date);

		return  a;
	}

	@RequestMapping(value = "/stats", method = RequestMethod.POST)
	Integer addEmployee(@RequestBody String requestText){
		Request request = new Request();		
		try {

			request = new ObjectMapper().readValue(requestText, Request.class);

			// request format is correct, check if customer exists			
			Optional<Customer> customer = customerService.findById(request.getCustomerID());
			if (!customer.isPresent())
			{	
				// there is no customer with this id, the request should not be added to the hourly stats
				String message = "There is no customer with the id " + request.getCustomerID() + " in the DB.";
				request = null;
				throw new Exception(message);
			}
			// check if customer is active
			else if(!customer.get().getActive()) {
				throw new Exception("Inactive user.");
			}

			// request format is correct, now check blacklists
			if (!ipBlacklistDAO.findByIP(request.getRemoteIP()).isEmpty())
			{
				throw new Exception("IP adress was found in the blacklist.");
			}

			if (!uaBlacklistDAO.findByUserID(request.getUserID()).isEmpty())
			{
				throw new Exception("UserID was found in the blacklist.");
			}			

			// save valid request in a list
			statsService.validRequestList.add(request);
			// Request accepted! 
			return statsService.processRequest(request);

		} catch (JsonProcessingException e) {
			// error in the json, gotta retrieve customer id from badly formatted String

			// check if it is possible to identify the client 			
			if(requestText.contains("customerID")) {

				String id = requestText.split("\"customerID\":")[1];
				int customerId = Integer.parseInt(id.split(",")[0]);

				Request invalidRequest = new Request();
				invalidRequest.setCustomerID(customerId);

				// check for time stamp
				if(requestText.contains("timestamp")) {
					String time = requestText.split("\"timestamp\":")[1];
					try {
						Timestamp ts = new ObjectMapper().readValue(time, Timestamp.class);
						invalidRequest.setTimestamp(ts);
					} catch (Exception e1) {
						invalidRequest.setTimestamp(new Timestamp(System.currentTimeMillis()));
						System.out.println("Invalid timestamp");
					}									   
				}

				statsService.invalidRequestList.add(invalidRequest);
			}

			System.out.println(e.getMessage());
			return 401;
		}
		catch (Exception e) {
			// request format was correct, another exception happened
			if(request != null)
				statsService.invalidRequestList.add(request);
			System.out.println(e.getMessage());
			return 401;
		}      
	}
}
