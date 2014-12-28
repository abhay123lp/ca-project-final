package dao;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @date_created Dec 21, 2014
 * @email annvcit@gmail.com
 */
public class DistinguishedName {
	private String c; // country name
	private String st; // state
	private String l; // locality
	private String orgName; // oranization name
	private String orgUnit; // unit name
	private String cn; // common name
	private String emailAddress;
	private String password;

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOn() {
		return orgUnit;
	}

	public void setOn(String un) {
		this.orgUnit = un;
	}

	public String getCn() {
		return convertCN(this.cn);
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "\"/C=" + this.c 
				+ "/ST=" + this.st 
				+ "/L=" + this.l 
				+ "/O=" + this.orgName 
				+ "/OU=" + this.orgUnit 
				+ "/CN=" + this.cn
				+ "/emailAddress=" + this.emailAddress 
				+ "\"";
	}

	private String convertCN(String cn) {
		return cn.replaceAll(" ", "_");
	}
}
