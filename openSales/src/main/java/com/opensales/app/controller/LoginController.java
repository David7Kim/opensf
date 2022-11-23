package com.opensales.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensales.app.domain.model.User;
import com.opensales.app.domain.model.UserProfile;
import com.opensales.app.repository.UserProfileRepository;
import com.opensales.app.repository.UserRepository;
import com.opensales.app.service.RegisterService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RegisterService registerService;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserProfileRepository userProfileRepository;

    /**
      * @Method Name : userLogin
      * @작성일 : 2022. 10. 27.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param params
      * @param request
      * @return
      */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = new User();
        UserProfile userProfile = new UserProfile();

        logger.info("userLoginSession : {}", request);
        logger.info("userLoginSession : {}", session);
        logger.info("userLogin : { }", params);
        Boolean msg = registerService.getUserInfoIsTrueFalse(params);

        if (!msg) {
            return "FAIL";
        } else {
            user = userRepository.findByUserAccount(params.get("user_account").toString());
            userProfile =userProfileRepository.findByUserProfileId(user);
            user.setPassword("비공개");
            userProfile.setUserProfileId(user);
            session.setAttribute("userInfo", userProfile);
            return "SUCCESS";
        }
    }
    /**
      * @Method Name : userLogOut
      * @작성일 : 2022. 10. 27.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param request
      */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void userLogOut(HttpServletRequest request) {
        HttpSession session = request.getSession();

        logger.info("userLoginSession : {}", request);
        logger.info("userLoginSession : {}", session);
            session.setAttribute("userInfo", "");
    }
}
