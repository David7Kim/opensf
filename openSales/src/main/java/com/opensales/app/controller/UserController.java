package com.opensales.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensales.app.domain.model.UserProfile;
import com.opensales.app.service.RegisterService;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    RegisterService registerService; 
    @ResponseBody
    @RequestMapping(value = "/user/info/{userId}", method = RequestMethod.GET)
    public UserProfile getUserInfo(@PathVariable("userId") String userId ) throws Exception {
        UserProfile userProfile =registerService.getuserInfo(userId);
        logger.info("getUserInfo() result : {} ",userProfile );
        
        return userProfile;
    }
    
    @ResponseBody
    @RequestMapping(value="/user/infoes" , method=RequestMethod.GET)
    public List<UserProfile>getUserInfoes(){
        
        return registerService.getUserInfoes();
    }
}
