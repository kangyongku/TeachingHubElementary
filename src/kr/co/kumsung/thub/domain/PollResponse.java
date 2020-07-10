package kr.co.kumsung.thub.domain;
/**
 * 설문조사 설문지 작성
 * @author jhg
 *
 */
public class PollResponse {

	private int    pollId        = 0;
	private int    entryId       = 0;
	private int    itemId        = 0;
	private int    sequence      = 0;
	private int    answerCnt     = 0;
	private int    entryCnt      = 0;	
	private String target        = "";
	private String title         = "";
	private String type          = "";
	private String answer        = "";
	private String userCnt       = "";
	private String subject       = "";
	private String startDate     = "";
	private String endDate       = "";
	private String regDate       = "";
	private String isUse         = "";
	private String progress      = "";
	private String jobName       = "";
	private String schoolName    = "";
	private String regId         = "";
	private String name          = "";
	private String multiple      = "N";
	private String ansyn         = "N";	
    private String date_710      = "";
	private String date_711      = "";
	private String date_712      = "";
	private String date_713      = "";
    private String date_714      = "";
    private String date_715      = "";
    private String date_716      = "";
    private String date_717      = "";
	private String date_718      = "";
    private String date_719      = "";    
    private String date_720      = "";
    private String date_721      = "";
    private String date_722      = "";
    private String date_723      = "";
    private String date_724      = "";
    private String date_725      = "";
    private String date_726      = "";
    private String date_727      = "";    
    private String date_728      = "";
	private String date_729      = "";
    private String date_730      = "";
    private String date_731      = "";
    private String date_801      = "";
    private String date_802      = "";
    private String date_803      = "";
    private String date_804      = "";
    private String date_805      = "";
    private String date_806      = "";
    private String date_807      = "";    
    private String date_808      = "";
	private String date_809      = "";		
	private String userid        = "";
	private String eventid       = "";	
	private String answer1       = "";
	private String answer2       = "";
	private String answer3       = "";
	private String answer4       = "";
	private String hp1           = "";
	private String hp2           = "";
	private String hp3           = "";
	private String schoolinfoseq = "";
	private String schoolnm      = "";
	private String address       = "";
	private String zipcode       = "";
	private String usernm        = "";
	
	
	
    public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getHp1() {
		return hp1;
	}
	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}
	public String getHp2() {
		return hp2;
	}
	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}
	public String getHp3() {
		return hp3;
	}
	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}
	public String getSchoolinfoseq() {
		return schoolinfoseq;
	}
	public void setSchoolinfoseq(String schoolinfoseq) {
		this.schoolinfoseq = schoolinfoseq;
	}
	public String getSchoolnm() {
		return schoolnm;
	}
	public void setSchoolnm(String schoolnm) {
		this.schoolnm = schoolnm;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	public String getAnswer4() {
		return answer4;
	}
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public String getDate_710() {
		return date_710;
	}
	public void setDate_710(String date_710) {
		this.date_710 = date_710;
	}
	public String getDate_711() {
		return date_711;
	}
	public void setDate_711(String date_711) {
		this.date_711 = date_711;
	}
	public String getDate_712() {
		return date_712;
	}
	public void setDate_712(String date_712) {
		this.date_712 = date_712;
	}
	public String getDate_713() {
		return date_713;
	}
	public void setDate_713(String date_713) {
		this.date_713 = date_713;
	}
	public String getDate_714() {
		return date_714;
	}
	public void setDate_714(String date_714) {
		this.date_714 = date_714;
	}
	public String getDate_715() {
		return date_715;
	}
	public void setDate_715(String date_715) {
		this.date_715 = date_715;
	}
	public String getDate_716() {
		return date_716;
	}
	public void setDate_716(String date_716) {
		this.date_716 = date_716;
	}
	public String getDate_717() {
		return date_717;
	}
	public void setDate_717(String date_717) {
		this.date_717 = date_717;
	}
	public String getDate_718() {
		return date_718;
	}
	public void setDate_718(String date_718) {
		this.date_718 = date_718;
	}
	public String getDate_719() {
		return date_719;
	}
	public void setDate_719(String date_719) {
		this.date_719 = date_719;
	}
	public String getDate_720() {
		return date_720;
	}
	public void setDate_720(String date_720) {
		this.date_720 = date_720;
	}
	public String getDate_721() {
		return date_721;
	}
	public void setDate_721(String date_721) {
		this.date_721 = date_721;
	}
	public String getDate_722() {
		return date_722;
	}
	public void setDate_722(String date_722) {
		this.date_722 = date_722;
	}
	public String getDate_723() {
		return date_723;
	}
	public void setDate_723(String date_723) {
		this.date_723 = date_723;
	}
	public String getDate_724() {
		return date_724;
	}
	public void setDate_724(String date_724) {
		this.date_724 = date_724;
	}
	public String getDate_725() {
		return date_725;
	}
	public void setDate_725(String date_725) {
		this.date_725 = date_725;
	}
	public String getDate_726() {
		return date_726;
	}
	public void setDate_726(String date_726) {
		this.date_726 = date_726;
	}
	public String getDate_727() {
		return date_727;
	}
	public void setDate_727(String date_727) {
		this.date_727 = date_727;
	}
	public String getDate_728() {
		return date_728;
	}
	public void setDate_728(String date_728) {
		this.date_728 = date_728;
	}
	public String getDate_729() {
		return date_729;
	}
	public void setDate_729(String date_729) {
		this.date_729 = date_729;
	}
	public String getDate_730() {
		return date_730;
	}
	public void setDate_730(String date_730) {
		this.date_730 = date_730;
	}
	public String getDate_731() {
		return date_731;
	}
	public void setDate_731(String date_731) {
		this.date_731 = date_731;
	}
	public String getDate_801() {
		return date_801;
	}
	public void setDate_801(String date_801) {
		this.date_801 = date_801;
	}
	public String getDate_802() {
		return date_802;
	}
	public void setDate_802(String date_802) {
		this.date_802 = date_802;
	}
	public String getDate_803() {
		return date_803;
	}
	public void setDate_803(String date_803) {
		this.date_803 = date_803;
	}
	public String getDate_804() {
		return date_804;
	}
	public void setDate_804(String date_804) {
		this.date_804 = date_804;
	}
	public String getDate_805() {
		return date_805;
	}
	public void setDate_805(String date_805) {
		this.date_805 = date_805;
	}
	public String getDate_806() {
		return date_806;
	}
	public void setDate_806(String date_806) {
		this.date_806 = date_806;
	}
	public String getDate_807() {
		return date_807;
	}
	public void setDate_807(String date_807) {
		this.date_807 = date_807;
	}
	public String getDate_808() {
		return date_808;
	}
	public void setDate_808(String date_808) {
		this.date_808 = date_808;
	}
	public String getDate_809() {
		return date_809;
	}
	public void setDate_809(String date_809) {
		this.date_809 = date_809;
	}
	private String date_813    = "";
    private String date_814    = "";
    private String date_815    = "";
    private String date_816    = "";
    private String date_817    = "";
    private String date_818    = "";
    private String date_819    = "";
    private String date_820    = "";
    private String date_821    = "";
    private String date_822    = "";
    private String date_823    = "";
    private String date_824    = "";
    private String date_825    = "";
    private String date_826    = "";
    private String date_827    = "";
    private String date_828    = "";
    private String date_829    = "";
    private String date_830    = "";
    private String date_831    = "";
    private String date_901    = "";
    private String date_902    = "";
    private String date_903    = "";
    private String date_904    = "";
    private String date_905    = "";
    private String date_906    = "";
    private String date_907    = "";
    private String date_908    = "";
    private String date_909    = "";		
    private String cnt         = "";
    
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getDate_813() {
		return date_813;
	}
	public void setDate_813(String date_813) {
		this.date_813 = date_813;
	}
	public String getDate_814() {
		return date_814;
	}
	public void setDate_814(String date_814) {
		this.date_814 = date_814;
	}
	public String getDate_815() {
		return date_815;
	}
	public void setDate_815(String date_815) {
		this.date_815 = date_815;
	}
	public String getDate_816() {
		return date_816;
	}
	public void setDate_816(String date_816) {
		this.date_816 = date_816;
	}
	public String getDate_817() {
		return date_817;
	}
	public void setDate_817(String date_817) {
		this.date_817 = date_817;
	}
	public String getDate_818() {
		return date_818;
	}
	public void setDate_818(String date_818) {
		this.date_818 = date_818;
	}
	public String getDate_819() {
		return date_819;
	}
	public void setDate_819(String date_819) {
		this.date_819 = date_819;
	}
	public String getDate_820() {
		return date_820;
	}
	public void setDate_820(String date_820) {
		this.date_820 = date_820;
	}
	public String getDate_821() {
		return date_821;
	}
	public void setDate_821(String date_821) {
		this.date_821 = date_821;
	}
	public String getDate_822() {
		return date_822;
	}
	public void setDate_822(String date_822) {
		this.date_822 = date_822;
	}
	public String getDate_823() {
		return date_823;
	}
	public void setDate_823(String date_823) {
		this.date_823 = date_823;
	}
	public String getDate_824() {
		return date_824;
	}
	public void setDate_824(String date_824) {
		this.date_824 = date_824;
	}
	public String getDate_825() {
		return date_825;
	}
	public void setDate_825(String date_825) {
		this.date_825 = date_825;
	}
	public String getDate_826() {
		return date_826;
	}
	public void setDate_826(String date_826) {
		this.date_826 = date_826;
	}
	public String getDate_827() {
		return date_827;
	}
	public void setDate_827(String date_827) {
		this.date_827 = date_827;
	}
	public String getDate_828() {
		return date_828;
	}
	public void setDate_828(String date_828) {
		this.date_828 = date_828;
	}
	public String getDate_829() {
		return date_829;
	}
	public void setDate_829(String date_829) {
		this.date_829 = date_829;
	}
	public String getDate_830() {
		return date_830;
	}
	public void setDate_830(String date_830) {
		this.date_830 = date_830;
	}
	public String getDate_831() {
		return date_831;
	}
	public void setDate_831(String date_831) {
		this.date_831 = date_831;
	}
	public String getDate_901() {
		return date_901;
	}
	public void setDate_901(String date_901) {
		this.date_901 = date_901;
	}
	public String getDate_902() {
		return date_902;
	}
	public void setDate_902(String date_902) {
		this.date_902 = date_902;
	}
	public String getDate_903() {
		return date_903;
	}
	public void setDate_903(String date_903) {
		this.date_903 = date_903;
	}
	public String getDate_904() {
		return date_904;
	}
	public void setDate_904(String date_904) {
		this.date_904 = date_904;
	}
	public String getDate_905() {
		return date_905;
	}
	public void setDate_905(String date_905) {
		this.date_905 = date_905;
	}
	public String getDate_906() {
		return date_906;
	}
	public void setDate_906(String date_906) {
		this.date_906 = date_906;
	}
	public String getDate_907() {
		return date_907;
	}
	public void setDate_907(String date_907) {
		this.date_907 = date_907;
	}
	public String getDate_908() {
		return date_908;
	}
	public void setDate_908(String date_908) {
		this.date_908 = date_908;
	}
	public String getDate_909() {
		return date_909;
	}
	public void setDate_909(String date_909) {
		this.date_909 = date_909;
	}
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getAnsyn() {
		return ansyn;
	}
	public void setAnsyn(String ansyn) {
		this.ansyn = ansyn;
	}	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getAnswerCnt() {
		return answerCnt;
	}
	public void setAnswerCnt(int answerCnt) {
		this.answerCnt = answerCnt;
	}
	public int getEntryCnt() {
		return entryCnt;
	}
	public void setEntryCnt(int entryCnt) {
		this.entryCnt = entryCnt;
	}
	public String getUserCnt() {
		return userCnt;
	}
	public void setUserCnt(String userCnt) {
		this.userCnt = userCnt;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
