package customerstats.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import customerstats.model.HourlyStats;

@Repository
public class HourlyStatsDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public HourlyStatsDAO() {

	}

	public List<HourlyStats> retrieveStats(Integer customerId, Date date) {

		Date nextDay = new java.sql.Date( date.getTime() + 24*60*60*1000);

		String sql = "SELECT * FROM HOURLY_STATS WHERE CUSTOMER_ID = ? AND TIME >= ? AND TIME < ?";
		try {
			List<HourlyStats> hourlyStats = jdbcTemplate.query(
					sql,
					new Object[]{customerId, date, nextDay},
					new BeanPropertyRowMapper<HourlyStats>(HourlyStats.class));

			return hourlyStats;
		}	
		catch(Exception e) {
			return null;
		}
	}
}
