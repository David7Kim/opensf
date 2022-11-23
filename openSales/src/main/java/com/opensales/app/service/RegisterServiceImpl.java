package com.opensales.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.opensales.app.utils.DataTypeConverter;
import com.opensales.app.utils.OpenSecurity;
import com.opensales.app.domain.model.User;
import com.opensales.app.domain.model.UserProfile;
import com.opensales.app.repository.UserProfileRepository;
import com.opensales.app.repository.UserRepository;

@Service
public class RegisterServiceImpl implements RegisterService {
    
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserProfileRepository userProfileRepository;
    
    @Autowired
    DataTypeConverter dataTeypConverter;
    
    @Autowired
    OpenSecurity security;
    
    /**
      * @Method Name : makeUserAccount
      * @작성일 : 2022. 7. 4.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param params
      * @return
      * @throws ParseException
      * @throws NumberFormatException
      * @throws DataAccessException
      */
    @Override
    public String makeUserAccount(Map<String,Object> params ) throws ParseException, NumberFormatException, DataAccessException {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        String empNo=makeEmployeeNumber(params.get("user_account").toString()); //사번
        String path = dataTeypConverter.profileImageSave(params.get("usr_profile").toString(),empNo);
        
        if (!path.equals("")) {
            path.replaceAll("\\\\", "/");
        }
        user.setEmail(params.get("email").toString());
        user.setLastName(params.get("last_nm").toString());
        user.setFirstName(params.get("first_nm").toString());
        user.setUserName(params.get("user_name").toString());
        user.setUserAccount(params.get("user_account").toString());
        user.setPassword(params.get("password").toString()); // passworld 를 암호화 시켜서 디비에 insert 추후 찾기 위해서는 다시 복호화 하는 함수는 생성 필요 
        user.setUserEmployeeNumber(empNo); // 사번 생성 함수 호출
        userProfile.setUserProfileId(user);
        userProfile.setUserProfilePicPath(path);
        logger.info("makeUserAccount() FirstName : {} LastName : {} ", user.getFirstName() ,user.getLastName());
        logger.info("makeUserAccount() EmployeeNumber : {} ", user.getUserEmployeeNumber());
        
        //user Insert 사용자의 계정과 이메일이 존재 하지 않을 경우 와  값들이 정상적으로 들어 왔을 경우 save
        if (userRepository.findByUserAccount(user.getUserAccount()) == null ) { 
            if(userRepository.findByEmail(user.getEmail()) == null) {
                if (!user.getUserAccount().isEmpty() || user.getUserAccount() !=null  ) {
                    if (!user.getEmail().isEmpty() || user.getEmail() !=null  ) {
                        if (!user.getEmail().isEmpty() || user.getEmail() !=null  ) {
                            if (!user.getFirstName().isEmpty() || user.getFirstName() !=null  ) {
                                if (!user.getLastName().isEmpty() || user.getLastName() !=null  ) {
                                    if (!user.getPassword().isEmpty() || user.getPassword() !=null  ) {
                                        userRepository.save(user);
                                        userProfileRepository.save(userProfile);
                                        
                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                return "EMAIL";
            }
        }else {
            return "ACCOUNT";
        }
        return "SUCESS";
    }

    /**
      * @Method Name : deleteUserAccount
      * @작성일 : 2022. 7. 4.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param param
      */
    @Override
    public void deleteUserAccount(Map<String,Object> param) {
        
        User user = new User() ;
        
        logger.info("deleteUserAccount() FirstName : {} LastName : {} ", user.getFirstName() ,user.getLastName());
        
        //User Delete
        userRepository.delete(user);
    }

    /**
      * @Method Name : updateUserAccount
      * @작성일 : 2022. 7. 4.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : 유저의 프로필또는 계정 정보를 업데이트 합니다.
      * @param param
      */
    @Override
    public void updateUserAccount(Map<String,Object> param) {
        String empNo=makeEmployeeNumber(param.get("user_account").toString()); //사번
        String path = dataTeypConverter.profileImageSave(param.get("profile_pic").toString(),empNo);
        //User 정보를 가져와서 해당 유저에 사번 ,이메일 , 비밀번호를 업데이트 합니다.
        User user =userRepository.findByUserAccount(param.get("user_account").toString());
        UserProfile userProfile = userProfileRepository.findByUserProfileId(user);
        
        if (!path.equals("")) {
            path.replaceAll("\\\\", "/");
        }
        
        user.setEmail(param.get("email").toString());
        user.setPassword(param.get("password").toString());
        // 여기 부분도 다시 수정해야함 
        userProfile.setUserProfilePicPath(path);
        
        logger.info("updateUserAccount() FirstName : {} LastName : {} ", user.getFirstName() ,user.getLastName());
        logger.info("updateUserAccount() {} Changed Profile Picture", userProfile.getUserProfilePicPath());
        
        //User정보  Update Email , Password, ProfilePicture
        try{
            if (user.getEmail() != null || !user.getEmail().equals("")) {
                    if (user.getPassword() != null || !user.getPassword().equals("")) {
                        userRepository.save(user);
                        userProfileRepository.save(userProfile);
                    }
            }
        }catch(NullPointerException e){
            logger.info("updateUserAccount NullPointerException :{}", e);
            logger.info("UserModify Info Email :{}, Password : {} ", user.getEmail() , user.getPassword().isEmpty());
        }
    }

    /**
      * @Method Name : findUserAccountByEmail
      * @작성일 : 2022. 7. 4.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : 유저가 가입한 이메일을 통해서 Account를 찾습니다. 일단은 사용자가 설정한 문답으로 찾는 방식으로도 고려
      * @param email
      * @return
      */
    @Override
    public String findUserAccountByEmail(String email) {
        String message ="";
        User user = userRepository.findByEmail(email);
        //이메일에 가입 아이디를 전송 한뒤 상태값을 변경하여 팝업 호출을 결정 (SUCCESS : 출력 , FAIL : 출력X)
        try {
            if(userRepository.findByEmail(email) == null) {
                logger.info("findUserAccount() This email is not exist : { }", email);
                message="FAIL";
                
            } else {
                logger.info("findUserAccount() This email is exist : { }", email);
                message="SUCCESS";
            }
            
        }catch(NullPointerException e) {
            logger.info("findUserAccountByEmail() NullPointException");
            e.printStackTrace();
        }
        return message;
    }

    /**
      * @Method Name : getUserInfoIs
      * @작성일 : 2022. 7. 4.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param params
      * @return
      */
    @Override
    public Boolean getUserInfoIsTrueFalse(Map<String, Object> params) {
        
        String userAccount = params.get("user_account").toString();
        String userPassword  = params.get("user_password").toString();
        Boolean result = true ;
        //계정이 Null이 아닌경우에
        if (userRepository.findByUserAccount(userAccount) != null) {
            
            //계정 , 패스워드를 가져와서 변수에 넣어준다. (패스워드는 암호화 작업 필요)
            String account= userRepository.findByUserAccount(userAccount).getUserAccount();
            String password= userRepository.findByUserAccount(userAccount).getPassword();
            
            logger.info("getUserInfoIsTrueFalse() Account : {}" , account);
            
            //패스워드가 가져온 정보와 동일하면 로그인, 아닌경우 메세지 풀력
            if(userPassword.equals(password)) {
                logger.info("Password is Matched : {}");
                return result;
            }else {
                logger.info("Password is not Matched : {}");
                result = false;
                return result;
            }
        //계정이 Null인 경우에 ID_FAIL 메세지 출력 
        }else {
            logger.info("LoginInfo is not Matched");
            result= false;
            return result;
        }
    }
    //사번 생성 메소드  (날짜_계정 )
    // ex) 20220704_testAccount
    private String makeEmployeeNumber(String account){
        Date nowDate = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String nowDateFormat = date.format(nowDate);
        String empNo = nowDateFormat+"_"+account;
        
        return empNo;
    }

    /**
      * @Method Name : getuserInfo
      * @작성일 : 2022. 7. 20.
      * @작성자 : tykim
      * @수정자 : tykim
      * @변경이력 : 2022. 10. 28.
      * @Method 설명 :
      * @param userId
      * @return
      */
    @Override
    public UserProfile getuserInfo(String userId) {
        Long id=Long.parseLong(userId);
        User user =userRepository.findById(id);
        UserProfile userProfile =userProfileRepository.findByUserProfileId(user);
        user.setPassword("비공개");
        userProfile.setUserProfileId(user);
        return userProfile;
    }

    @Override
    public void setAdminToUserAccount(Boolean isAdmin) {
        
        
    }

    @Override
    public List<UserProfile> getUserInfoes() {
        //사용자 정보 가져오기
        List<UserProfile> userInfoes =userProfileRepository.findAll();
        logger.info("getUserInfoes() result : {} ",userInfoes );
        return userInfoes;
    }
}
