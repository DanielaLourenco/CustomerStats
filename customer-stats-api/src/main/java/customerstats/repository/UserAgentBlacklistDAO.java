package customerstats.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserAgentBlacklistDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserAgentBlacklistDAO() {

	}

	public String findByUserID(String ua) {

		String result = "";
		try {
			String sql = "SELECT TOP 1 UA FROM UA_BLACKLIST WHERE UA = '"+ ua + "'";
			result = jdbcTemplate.queryForObject(sql, String.class);
		} 
		catch (EmptyResultDataAccessException e) {
			// 
		}
		return result;
	}	
}