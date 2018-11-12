package com.simple.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES加密
 * 在这段代码可以运行之前，还有一个问题需要解决。Java本身限制密钥的长度最多128位，而AES256需要的密钥长度是256位，
 * 因此需要到Java官网上下载一个Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files。
 * 在Java SE的下载页面(http://www.oracle.com/technetwork/java/javase/downloads/index.html)下面的Additional Resources那里会有下载链接。
 * 下载后打开压缩包，里面有两个jar文件。
 * 把这两个jar文件解压到JRE目录下的lib/security文件夹，覆盖原来的文件。这样Java就不再限制密钥的长度了
 * @author zhengfy1
 */
public class AESEncrypt {
	public static boolean initialized = false;  
    
    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";  
     
    private static SecretKeySpec getKey(byte[] key) throws Exception {
    	KeyGenerator kgen = KeyGenerator.getInstance("AES");  
    	kgen.init(256, new SecureRandom(key));//只有128,192,256三种长度可选  
    	SecretKey secretKey = kgen.generateKey();  
    	byte[] enCodeFormat = secretKey.getEncoded();  
    	return new SecretKeySpec(enCodeFormat, ALGORITHM);
    }
    
    
    public static byte[] Aes256Encode(String str, String key) {
    	try {
    		
			return Aes256Encode(str,key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /** 
     * @param  String str  要被加密的字符串 
     * @param  byte[] key  加/解密要用的长度为32的字节数组（256位）密钥 
     * @return byte[]  加密后的字节数组 
     */  
    public static byte[] Aes256Encode(String str, byte[] key){  
        initialize();  
        byte[] result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
           //SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            SecretKeySpec keySpec = getKey(key);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    public static String Aes256Decode(String content, String key){
    	try {
			return Aes256Decode(content.getBytes("UTF-8"),key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String Aes256Decode(byte[] bytes, String key){
    	try {
			return Aes256Decode(bytes,key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /** 
     * @param  byte[] bytes  要被解密的字节数组 
     * @param  byte[] key    加/解密要用的长度为32的字节数组（256位）密钥 
     * @return String  解密后的字符串 
     */  
    public static String Aes256Decode(byte[] bytes, byte[] key){  
        initialize();  
        String result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            //SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            SecretKeySpec keySpec = getKey(key); 
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  
            byte[] decoded = cipher.doFinal(bytes);  
            result = new String(decoded, "UTF-8");  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    private static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }  
    
    public static void main(String[] args) {
    	String key = "1234567890qwertyuiopasdfghadfas";
    	System.out.println(Aes256Decode(AESEncrypt.Aes256Encode("asdfghjkl", key),key));
	}
}
