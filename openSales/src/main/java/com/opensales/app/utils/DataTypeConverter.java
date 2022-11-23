package com.opensales.app.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.access.InvocationFailureException;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataTypeConverter {
    private static final Logger logger = LoggerFactory.getLogger(DataTypeConverter.class);
   
    /**
      * @Method Name : converterStringToLong
      * @작성일 : 2022. 6. 21.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : 
      * @param object
      * @return
      */
    public Long converterStringToLong(Object object) {
        
        return Long.parseLong(object.toString());
        
    }
    public Date converterStringtoDate(Object object) throws ParseException  {
        
        String targetDate = object.toString();
        SimpleDateFormat convertDates = new SimpleDateFormat("yyyy-MM-dd");
        Date toDate = convertDates.parse(targetDate);
        
        return toDate;
        
    }
    
    /**
      * @Method Name : convJsonToMap
      * @작성일 : 2022. 6. 21.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param json
      * @return
      */
    public static Map<String, Object> convJsonToMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    /**
      * @Method Name : convJsonToList
      * @작성일 : 2022. 6. 21.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param json
      * @return
      */
    public static List<Object> convJsonToList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Object> list = null;
        try {
            list = objectMapper.readValue(json, new TypeReference<List<Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
      * @Method Name : convObjectToJson
      * @작성일 : 2022. 6. 21.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param dto
      * @return
      */
    public static String convObjectToJson(Object dto) {
        ObjectMapper objectMapper = new ObjectMapper();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return body;
    }
    
    /**
      * @Method Name : betweenStartAndEnd
      * @작성일 : 2022. 8. 2.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : 초, 분 , 시 , 일을 구하는 메소드입니다.
      * @param start
      * @param end
      * @param option
      * @return
      * @throws ParseException
      */
    public static Long betweenStartAndEnd (String start, String end , int option) throws ParseException {
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);
        Long result = null;
        
        // 밀리세컨드 단위로 차이 결과 도출
        long resultTime = endDate.getTime() - startDate.getTime(); 
        // 초 :1 , 분 : 2 , 시 : 3 , 일 : 4
        switch (option) {
        
        case 1: result = resultTime / 1000;
                break;
        case 2: result = resultTime / (60*1000);
                break;
        case 3: result = resultTime / (60*60*1000);
                break;
        case 4: result = resultTime / (24*60*60*1000);
                break;
                
        }
        return result;
        
    }
    
    /**
      * @Method Name : profileImageSave
      * @작성일 : 2022. 10. 28.
      * @작성자 : tkyim
      * @변경이력 : 
      * @Method 설명 :
      * @param base64Url
      * @return
      */
    public static String profileImageSave(String base64Url,String empnNo) {
        String data = "";
        String type ="jpg";
        String path ="D:\\reactProject\\opensales\\public\\images\\"+empnNo+"_profile_pic"+"."+type;
        byte[] imageBytes = null;
        
        try {
            //이미지가 존재 할 경우 선언한 Path와 Image를 path에 write하지만 
            if (base64Url.contains(",")) {
                data= base64Url.split(",")[1];
                imageBytes = DatatypeConverter.parseBase64Binary(data);
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
                ImageIO.write(bufImg, type, new File(path));
                path = "\\images\\"+empnNo+"_profile_pic"+"."+type;
            //이미지가 존재 하지 않을 경우 선언한 path는 빈값으로 반환된다.
            }else {
                path ="";
            }
            
        }catch(IOException e) {
            logger.info("profileImageSave IOException " );
            e.printStackTrace();
            
        }catch(InvocationFailureException e) {
            logger.info("profileImageSave Converting Failure ");
            e.printStackTrace();
        }
        return path;
        
    }

}
