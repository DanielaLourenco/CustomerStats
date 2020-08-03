package customerstats.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IpBlacklistDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public IpBlacklistDAO() {

	}

	public String findByIP(String ip) {
		String result = "";
		try {
			String sql = "SELECT TOP 1 IP FROM IP_BLACKLIST WHERE IP = '"+ ip + "'";
			result = jdbcTemplate.queryForObject(sql, String.class);
		} 
		catch (EmptyResultDataAccessException e) {
			// 
		}
		return result;
	}



}
