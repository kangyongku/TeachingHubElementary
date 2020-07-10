package kr.co.kumsung.thub.domain;

public class QuizEvent {

	private String user_id = "";
	private String quiz_date = "";
	private int event_id = 0;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getQuiz_date() {
		return quiz_date;
	}
	public void setQuiz_date(String quiz_date) {
		this.quiz_date = quiz_date;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

}