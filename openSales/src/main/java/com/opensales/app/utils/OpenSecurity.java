package com.opensales.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class OpenSecurity {

    private static final Logger logger = LoggerFactory.getLogger(OpenSecurity.class);
    
    public String encryptSHA256(String str) {
        String sha= "";
        
        try {
            //SHA-256 인스턴스 
            MessageDigest digst = MessageDigest.getInstance("SHA-256");
            digst.update(str.getBytes());
            byte byteData[] = digst.digest();
            StringBuffer stringBuffer = new StringBuffer();
            
            for (int i =0; i < byteData.length; i++) {
                stringBuffer.append(Integer.toString((byteData[i]&0xff) + 0x100,16).substring(1));
            }
            sha = stringBuffer.toString();
            
        }catch (NoSuchAlgorithmException e) {
            logger.info("encryptSHA256() Error");
            sha= null;
        }
        return sha;
        
    }
    
}
