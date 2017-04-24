package com.yyx.utils.security;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.yyx.core.security.AbBase64;
import com.yyx.utils.excepetion.AppWebApiException;
import com.yyx.utils.excepetion.AppWebApiExceptionEnum;


/** 
 * @author:yanyating
 * @createTime:2016年3月31日
 */
public class AES256Util {

	public static boolean initialized = false;  
    
    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";  
      
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
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
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
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  
            byte[] decoded = cipher.doFinal( bytes);  
            result = new String(decoded, "UTF-8");  
        }catch(Exception e){  
            e.printStackTrace();  
        	throw new AppWebApiException(AppWebApiExceptionEnum.aes_decode_error.code,
					AppWebApiExceptionEnum.aes_decode_error.msg);
        }  
        return result;  
    }  
      
    public static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    } 
    

	
	
	
    public static void main(String[] args) {
		
    	String content = "{\"password\" : \"123qwe\",\"userName\" : \"15300023088\"}";  
        String password = "FJ+7NCGKsxs7YLU4ZUSkN7D6F8r5M9FS";
    	
    	  
          
        byte[] encryptResult = Aes256Encode(content, password.getBytes());  
        System.out.println(new String(encryptResult));
        System.out.println("密文：" +AbBase64.encode(encryptResult) ); 
        
//        String content = "NFJIWUNMWEVMb3hVQXVJVVVFVXB4ZmtHbzk1QXpBS3djN3VERGhTVjV0SnoyZ3IySWdlUHJxYXlaWDR1S1l6ZUlpaVlPK2cvZjhCQlcvSEg1TjN1MEE9PQ";  
//        String password = "ZqkeW9F9N0tGMdS5twS8Q2m5N3L6Y3Xz";  
//        String s=AbBase64.encode(AbBase64.encode(encryptResult),"UTF-8");
//        System.out.println(AbBase64.decode("2ViKp0ri3Pr3mv247GkKqUBZEtpf2+lKh+gqBJ8qQYuoyP4QJzcsnOohmP9p07XNLC078E/V/b/1YkCwTbYPFw==" ));
//        String decryptResult = Aes256Decode(AbBase64.decode(content ), password.getBytes());  
//        System.out.println("解密：" +decryptResult); 
    	
	}
	
}
