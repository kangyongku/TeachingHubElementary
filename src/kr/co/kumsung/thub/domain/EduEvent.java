package kr.co.kumsung.thub.domain;

public class EduEvent {

	private String user_id = "";
	private String edu_day = "";
	private String reg_date = "";
	private String person_agreement ="";
	private String school = "";
	private String tel = "";
	
	public String getPerson_agreement() {
		return person_agreement;
	}
	public void setPerson_agreement(String person_agreement) {
		this.person_agreement = person_agreement;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEdu_day() {
		return edu_day;
	}
	public void setEdu_day(String edu_day) {
		this.edu_day = edu_day;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
}
