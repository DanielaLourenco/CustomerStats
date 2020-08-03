package customerstats.model;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IP_BLACKLIST")
public class UserAgentBlacklist {
	@Column(name = "ua")
	@Id
	String ua;

	public String getUserAgent() {
		return ua;
	}

	public void setUserAgent(String ua) {
		this.ua = ua;
	}
}
