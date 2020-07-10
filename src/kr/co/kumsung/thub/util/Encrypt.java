package kr.co.kumsung.thub.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 암호화 모듈
 * @author mikelim
 *
 */
public class Encrypt {
	
	public static String key()
	{
		return "kumsungteachinghubenckey";
	}
	
	public static Key getKey() throws Exception
	{
		DESedeKeySpec desKeySpec = new DESedeKeySpec(key().getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
	}
	
	public static String encode(String str) throws Exception
	{	
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE , getKey());
		
		byte[] inputBytes = str.getBytes("EUC-KR");
		byte[] outputBytes = cipher.doFinal(inputBytes);
		
		String result = Base64.encodeBase64String(outputBytes);
		return result;
	}
	
	public static String decode(String str) throws Exception
	{
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE , getKey());
		
		byte[] inputBytes = Base64.decodeBase64(str);
        byte[] outputBytes = cipher.doFinal(inputBytes);
        
        String result = new String(outputBytes, "EUC-KR");
        return result;
	}
	
}
