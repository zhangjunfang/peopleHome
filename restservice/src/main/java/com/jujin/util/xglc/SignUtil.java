package com.jujin.util.xglc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Title: EncryptUtils
 * Description: 加密解密工具类(西瓜理财)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class SignUtil {

	private static final String securityKey = "A1A064C2548FD2180C20623191D6D530";
	public static final String signKey = "F69C0602A23957FC2C73C52132DBCDA6";
	/**
	 *  MD5大写32位加密
	* Title: string2MD5
	* Description: 
	* @param inStr
	* @return
	 */
    public static String encryptMD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().toUpperCase();  
    }  
    
    /**
     * AES128加密
    * Title: encrypt
    * Description: 
    * @param userAccessKey
    * @param securityKey
    * @return
     */
    private static byte[] encrypt(String userAccessKey, String securityKey) {
            try {           
	            	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	                random.setSeed(securityKey.getBytes());
                    KeyGenerator kgen = KeyGenerator.getInstance("AES");
                    kgen.init(128, random);
                    SecretKey secretKey = kgen.generateKey();
                    byte[] enCodeFormat = secretKey.getEncoded();
                    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                    Cipher cipher = Cipher.getInstance("AES");// 创建密码器
                    byte[] byteContent = userAccessKey.getBytes("utf-8");
                    cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
                    byte[] result = cipher.doFinal(byteContent);
                    return result; // 加密
            } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
            } catch (InvalidKeyException e) {
                    e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
            } catch (BadPaddingException e) {
                    e.printStackTrace();
            }
            return null;
    }
    
    /**
     * BASE64加密
    * Title: encryptBASE64
    * Description: 
    * @param key
    * @return
    * @throws Exception
     */
    private static String encryptBASE64(byte[] key){  
        return (new BASE64Encoder()).encodeBuffer(key).replaceAll("\r\n", "");  
    } 
    
    /**
     * BASE64解密
    * Title: decryptBASE64
    * Description: 
    * @param key
    * @return
    * @throws IOException
     */
    private static byte[] decryptBASE64(String key) throws IOException {
    	return (new BASE64Decoder()).decodeBuffer(key);
    }
    
    /**解密 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     */  
    private static byte[] decrypt(byte[] content, String password) {  
            try {  
            	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(password.getBytes());
                     KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                     kgen.init(128, random);  
                     SecretKey secretKey = kgen.generateKey();  
                     byte[] enCodeFormat = secretKey.getEncoded();  
                     SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
                     Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
                    cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
                    byte[] result = cipher.doFinal(content);  
                    return result; // 加密   
            } catch (NoSuchAlgorithmException e) {  
                    e.printStackTrace();  
            } catch (NoSuchPaddingException e) {  
                    e.printStackTrace();  
            } catch (InvalidKeyException e) {  
                    e.printStackTrace();  
            } catch (IllegalBlockSizeException e) {  
                    e.printStackTrace();  
            } catch (BadPaddingException e) {  
                    e.printStackTrace();  
            }  
            return null;  
    }  
    
    /**
     * 西瓜理财userAccessKey-AES加密
    * Title: encryptAccessKey
    * Description: 
    * @param userAccessKey
    * @return
     */
    public static String encryptAccessKey(String userAccessKey){
    	return encryptBASE64(encrypt(userAccessKey, securityKey));
    }
    
    /**
     * 西瓜理财userAccessKey-AES解密
    * Title: decryptAccessKey
    * Description: 
    * @param encryptKey
    * @return
     */
    public static String decryptAccessKey(String encryptKey){
    	String userAccessKey = "";
    	try {
    		userAccessKey = new String(decrypt(decryptBASE64(encryptKey), securityKey));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userAccessKey;
    }
    
	/**
	 * 签名验证
	* Title: checkSign
	* Description: 
	* @param sign
	* @param paramString
	* @return
	 */
	public static boolean checkSign(String sign,String checkStr){
		String signStr = SignUtil.encryptMD5(checkStr+SignUtil.signKey);
		if(sign.equals(signStr)){
			return true;
		}else{
			return false;
		}
	}
    
    public static void main(String[] args) throws Exception {
    	//MD5加密
		String str = "WRwC642szEP3uaGcZXtwoXjyEIaYML+pbc44ndh/CeGgJSBwHMcDyLNes0FUAqM0";
//		System.out.println(encryptBASE64(str.getBytes()));
    	//对称加密
//    	String userAccessKey = "xxbcoder";
    	System.out.println(encryptAccessKey(str));
    	
    	
		
	}
}
