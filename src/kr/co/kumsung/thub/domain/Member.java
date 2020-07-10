package kr.co.kumsung.thub.domain;

/**
 * 회원 Object
 * @author mikelim
 *
 */
public class Member {

	private int    id               = 0;
	private String userId           = "";
	private String passwd           = "";
	private String name             = "";
	private String teacher          = "";
	private String job              = "";
	private String jobName          = ""; //직업 이름 COMMONCODE 참조
	private String hpNum            = "";	// '-' 추가한 휴대폰 번호
	private String hp1              = "";
	private String hp2              = "";
	private String hp3              = "";
	private String writedate        = "";
	private String is_sms           = "";
	private String isremail         = "";
	private String tel1             = "";
	private String tel2             = "";
	private String tel3             = "";
	private String email            = "";
	private String zipcode          = "";
	private String address1         = "";
	private String address2         = "";
	private String schoolInfoCharge = "";
	private String visitreason      = "";
	private String connectDate;
	private int    connectCount;
	private int    authId           = 0;
	private int    loginCount       = 0;
	private String authGranter      = "";
	private String adminDate        = "";
	private String authType         = "";
	private String smartAuth        = "";
	private String accessAuth       = "";
	private String learningAuth     = "";
	private String boardAuth        = "";
	private String regDate          = "";
	private String subCategory      = "";
	private String subCategoryName  = "";
	private String teacherAuthType  = "";
	private String ip               = "";
	private String isFinallyAuth    = "";
	private String finallyAuthDate  = "";
	private String schoolNm         = "";
	private String schoolTel        = "";
	private String schoolFax        = "";
	private String schoolZipcode    = "";
	private String schoolAddress1   = "";
	private String schoolAddress2   = "";
	private String authTypeEtc      = "";
	private int    num              = 0 ;
	private String siteCode	        = "";
	private String actionDate       = "";
	private String actionMode       = "";
	private String targetName       = "";
	private String targetId	        = "";
	private String fgclass          = "";
	private String schoolinfoseq    = "";
	private String address          = "";
	
	
	
	public String getSchoolinfoseq() {
		return schoolinfoseq;
	}
	public void setSchoolinfoseq(String schoolinfoseq) {
		this.schoolinfoseq = schoolinfoseq;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFgclass() {
		return fgclass;
	}
	public void setFgclass(String fgclass) {
		this.fgclass = fgclass;
	}
	public String getTeacherAuthType() {
		return teacherAuthType;
	}
	public void setTeacherAuthType(String teacherAuthType) {
		this.teacherAuthType = teacherAuthType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
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
	public String getWritedate() {
		return writedate;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public String getHpNum() {
		return hpNum;
	}
	public void setHpNum(String hpNum) {
		this.hpNum = hpNum;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getSchoolInfoCharge() {
		return schoolInfoCharge;
	}
	public void setSchoolInfoCharge(String schoolInfoCharge) {
		this.schoolInfoCharge = schoolInfoCharge;
	}
	public String getIs_sms() {
		return is_sms;
	}
	public void setIs_sms(String is_sms) {
		this.is_sms = is_sms;
	}
	public String getIsremail() {
		return isremail;
	}
	public void setIsremail(String isremail) {
		this.isremail = isremail;
	}
	public String getVisitreason() {
		return visitreason;
	}
	public void setVisitreason(String visitreason) {
		this.visitreason = visitreason;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getSmartAuth() {
		return smartAuth;
	}
	public void setSmartAuth(String smartAuth) {
		this.smartAuth = smartAuth;
	}
	public String getAccessAuth() {
		return accessAuth;
	}
	public void setAccessAuth(String accessAuth) {
		this.accessAuth = accessAuth;
	}
	public String getLearningAuth() {
		return learningAuth;
	}
	public void setLearningAuth(String learningAuth) {
		this.learningAuth = learningAuth;
	}
	public String getBoardAuth() {
		return boardAuth;
	}
	public void setBoardAuth(String boardAuth) {
		this.boardAuth = boardAuth;
	}
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String getFinallyAuthDate() {
		return finallyAuthDate;
	}
	public void setFinallyAuthDate(String finallyAuthDate) {
		this.finallyAuthDate = finallyAuthDate;
	}
	public String getIsFinallyAuth() {
		return isFinallyAuth;
	}
	public void setIsFinallyAuth(String isFinallyAuth) {
		this.isFinallyAuth = isFinallyAuth;
	}
	public String getSchoolNm() {
		return schoolNm;
	}
	public void setSchoolNm(String schoolNm) {
		this.schoolNm = schoolNm;
	}
	public String getSchoolTel() {
		return schoolTel;
	}
	public void setSchoolTel(String schoolTel) {
		this.schoolTel = schoolTel;
	}
	public String getSchoolFax() {
		return schoolFax;
	}
	public void setSchoolFax(String schoolFax) {
		this.schoolFax = schoolFax;
	}
	public String getSchoolZipcode() {
		return schoolZipcode;
	}
	public void setSchoolZipcode(String schoolZipcode) {
		this.schoolZipcode = schoolZipcode;
	}
	public String getSchoolAddress1() {
		return schoolAddress1;
	}
	public void setSchoolAddress1(String schoolAddress1) {
		this.schoolAddress1 = schoolAddress1;
	}
	public String getSchoolAddress2() {
		return schoolAddress2;
	}
	public void setSchoolAddress2(String schoolAddress2) {
		this.schoolAddress2 = schoolAddress2;
	}
	public String getAuthTypeEtc() {
		return authTypeEtc;
	}
	public void setAuthTypeEtc(String authTypeEtc) {
		this.authTypeEtc = authTypeEtc;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public String getConnectDate() {
		return connectDate;
	}
	public void setConnectDate(String connectDate) {
		this.connectDate = connectDate;
	}
	public int getConnectCount() {
		return connectCount;
	}
	public void setConnectCount(int connectCount) {
		this.connectCount = connectCount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionMode() {
		return actionMode;
	}
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCod(String siteCod) {
		this.siteCode = siteCode;
	}
	public String getAuthGranter() {
		return authGranter;
	}
	public void setAuthGranter(String authGranter) {
		this.authGranter = authGranter;
	}
	public String getAdminDate() {
		return adminDate;
	}
	public void setAdminDate(String adminDate) {
		this.adminDate = adminDate;
	}
	
}
