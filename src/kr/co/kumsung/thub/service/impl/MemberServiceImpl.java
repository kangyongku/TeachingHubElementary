package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
//import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.MemberDao;
import kr.co.kumsung.thub.domain.ChoiceBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.util.ResultMap;


public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDao memberDao;

	@Override
	public Member get(String userId) {
		return memberDao.get(userId);
	}
	
	public int matchPassword(Map<String,Object> params){
		return memberDao.matchPassword(params);
	}
	
	@Override
	public List<Member> getMemberList(Map<String, Object> params){
		return (List<Member>) memberDao.getMemberList(params);
	}
	
	@Override
	public int getMemberTotalList(Map<String, Object> params){
		return (Integer) memberDao.getMemberTotalList(params);
	}
	
	@Override
	public Member getMemberDetail(int id){
		return (Member)memberDao.getMemberDetail(id);
	}
	
	@Override
	public void insertMember(Map<String, Object> params){
		memberDao.insertMember(params);
	}
	
	@Override
	public void updateMember(Map<String, Object> params){
		memberDao.updateMember(params);
	}
	
	@Override
	public void deleteMember(int id){
		memberDao.deleteMember(id);
	}
	
	@Override
	public void updateMemberTeacher(int id){
		memberDao.updateMemberTeacher(id);
	}

	@Override
	public Member getFindUserInfo(String userId) {
		return memberDao.getFindUserInfo(userId);
	}
	
	@Override
	public void insertAuth(Map<String, Object> params){
		memberDao.insertAuth(params);
	}
	
	@Override
	public List<Member> getAuthList(Map<String, Object> params){
		return (List<Member>) memberDao.getAuthList(params);
	}
	
	@Override
	public int getAuthTotalList(Map<String, Object> params){
		return (Integer) memberDao.getAuthTotalList(params);
	}
	
	@Override
	public Member getAuthDetail(int authId){
		return (Member) memberDao.getAuthDetail(authId);
	}
	
	@Override
	public void updateAuth(Map<String, Object> params){
		memberDao.updateAuth(params);
	}

	@Override
	public void deleteAuth(int authId){
		memberDao.deleteAuth(authId);
	}

	@Override
	public Member getAdminLogin(Map<String, Object> params) {
		return memberDao.getAdminLogin(params);
	}
	
	@Override
	public int getAuthUserDup(String userId){
		return (Integer) memberDao.getAuthUserDup(userId);
	}
	
	@Override
	public Member getMemberInfo(String userId){
		return (Member) memberDao.getMemberInfo(userId);
	}
	
	@Override
	public int getLearningAuth(Map<String,Object> params){
		return (Integer) memberDao.getLearningAuth(params);
	}

	@Override
	public List<ChoiceBook> getMyBooks(String userId) {
		return (List<ChoiceBook>) memberDao.getMyBooks(userId);
	}
	
	@Override
	public int getMyBooksCount(String userId) {
		return memberDao.getMyBooksCount(userId);
	}
	
	@Override
	public int isDuplicatedMyBook(Map<String,Object> params){
		return memberDao.isDuplicatedMyBook(params);
	}

	@Override
	public int insertMyBook(Map<String, Object> params) {
		return memberDao.insertMyBook(params);
	}

	@Override
	public void deleteMyBook(Map<String, Object> params) {
		memberDao.deleteMyBook(params);
	}

	@Override
	public void updateSubCategory(Map<String, Object> params) {
		memberDao.updateSubCategory(params);
	}

	@Override
	public boolean sendEmail(String target, String subject ,String contents){
		
//		final String username = "thub";
//      final String password = "kumsung2013)(*&";  

		//final String username = "teaching.kumsung@gmail.com";
		//final String username ="purunet.lys@gmail.com";
        //final String password = "dmltjq7847";
		
        //Properties props = new Properties();
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.host", "127.0.0.1");
//        props.put("mail.smtp.port", "25");
//        props.put("mail.smtp.ssl.trust", "127.0.0.1");
//-------------------------------------------------------
     // G-Mail SMTP 사용시
        /*props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("teaching.kumsung@gmail.com"));
            message.setRecipients(Message.RecipientType.TO ,
                InternetAddress.parse(target));            
            message.setSubject(subject);
            message.setContent(contents , "text/html; charset=utf-8");
            Transport.send(message);
            
            System.out.println("######## : " + subject);
            System.out.println("######## : 메일발송");
            
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }*/
        String host = "smtp.gmail.com";
        String username = "teaching.kumsung@gmail.com";
        String password = "ysblhpeclieisfzs";
         
        //properties 설정
        Properties props = new Properties();
        props.put("mail.smtps.auth", "true");
        // 메일 세션
        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
 
        // 메일 관련
        try {
        msg.setSubject(subject);
        //msg.setText(contents);
        msg.setContent(contents , "text/html; charset=utf-8");
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target));
        
        // 발송 처리
        Transport transport = session.getTransport("smtps");
        transport.connect(host, username, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();  
        
        return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
	}
	
	@Override
	public boolean sendEmailFromMe(String target, String from , String subject ,String contents){
		
//		final String username = "thub";
//      final String password = "kumsung2013)(*&";
		final String username = "teaching.kumsung@gmail.com";
        final String password = "ysblhpeclieisfzs";
        
        Properties props = new Properties();
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.host", "127.0.0.1");
//        props.put("mail.smtp.port", "25");

        // G-Mail SMTP 사용시
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO ,
                InternetAddress.parse(target));            
            message.setSubject(subject);        
            message.setContent(contents , "text/html; charset=utf-8");
            Transport.send(message);
            
            System.out.println("######## : " + subject);
            System.out.println("######## : 메일발송");
            
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
	}

	@Override
	public void updateMemberInfo(Map<String, Object> params) {
		memberDao.updateMemberInfo(params);
	}

	@Override
	public void sendSms(Map<String, Object> params) {
		memberDao.sendSms(params);
	}

	@Override
	public void insertSmsSendInfo(Map<String, Object> params) {
		memberDao.insertSmsSendInfo(params);
	}

	@Override
	public int getSmsSendListCount() {
		return memberDao.getSmsSendListCount();
	}

	@Override
	public List<ResultMap> getSmsSendList(Map<String, Object> params) {
		return memberDao.getSmsSendList(params);
	}

	@Override
	public Member getFindMemberByInfo(Map<String, Object> params) {
		return memberDao.getFindMemberByInfo(params);
	}

	@Override
	public void updateMemberPasswd(Map<String, Object> params) {
		memberDao.updateMemberPasswd(params);
	}
	
	@Override
	public void insertSdf(String userId){
		memberDao.insertSdfdown(userId);
	}

	@Override
	public void updateLoginCount(Map<String,Object> params) {
		memberDao.updateLoginCount(params);
	}
	@Override
	public void updateLoginCountDefault(Map<String, Object> params) {
		memberDao.updateLoginCountDefault(params);
	}

	@Override
	public int getAccessTotalList(Map<String, Object> params) {
		return (Integer) memberDao.getAccessTotalList(params);
	}

	@Override
	public List<Member> getAccessList(Map<String, Object> params) {
		return (List<Member>) memberDao.getAccessList(params);
	}

	@Override
	public Member getFindAccessUserInfo(String userId) {
		return memberDao.getFindAccessUserInfo(userId);
	}

	@Override
	public int updateAccessMember(Map<String, Object> params) {
		return memberDao.updateAccessMember(params);
	}

	@Override
	public int deleteAccessMember(Map<String, Object> params) {
		return memberDao.deleteAccessMember(params);
	}

	@Override
	public int getMemberAccessTotalList(Map<String, Object> params) {
		return (Integer) memberDao.getMemberAccessTotalList(params);
	}

	@Override
	public List<Member> getMemberAccessList(Map<String, Object> params) {
		return (List<Member>) memberDao.getMemberAccessList(params);
	}

	@Override
	public void insertAccessLog(Map<String, Object> params) {
		memberDao.insertAccessLog(params);
		
	}

	//에러사항나오면 삭제
	@Override
	public int getMemberSchoolInfo(String user_id) {
		// TODO Auto-generated method stub
		return 0;
	}
	


}
