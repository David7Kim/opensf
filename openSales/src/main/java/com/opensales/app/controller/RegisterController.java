package com.opensales.app.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensales.app.service.RegisterService;

@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    
    @Autowired
    RegisterService registerService;
    
    //계정 생성 
    @ResponseBody
    @RequestMapping(value = "/regist/account", method = RequestMethod.POST)
    public String makeAccount(@RequestBody HashMap<String,Object> param ) throws Exception {
        
        //Boolean 형태로 1,0 값을 가져와서 FAIL ,SUCCESS를 return 
        String msg =registerService.makeUserAccount(param);
        logger.info("makeAccount() msg : {} ", msg);
        if (!msg.equals("SUCCESS")) {
            logger.info("makeAccount() result : {} ", msg);
            return msg;
        }
        logger.info("makeAccount() result : {} ", msg);
        return msg;
    }
    /*//계정 CSV 생성
    @ResponseBody
    @RequestMapping(value ="/regist/account/csv", method = RequestMethod.POST)
    public Excel*/
    
    //계정 삭제
    @ResponseBody
    @RequestMapping(value="/regist/account/delete" , method= RequestMethod.POST)
    public void deleteAccount(@RequestBody HashMap<String, Object>param)throws Exception{
        
        registerService.deleteUserAccount(param);
    }
    
    //계정 수정
    @ResponseBody
    @RequestMapping(value="/regist/account/update" , method= RequestMethod.POST)
    public void updateAccount(@RequestBody HashMap<String, Object>param)throws Exception{
        
        registerService.updateUserAccount(param);
    }
    //계정찾기
    @ResponseBody
    @RequestMapping(value = "/regist/find/{email}", method = {RequestMethod.GET})
    public String findUserAccount(@PathVariable("email") String email ) {
        
        logger.info("findUserAccount  email : {} " , email );
        return registerService.findUserAccountByEmail(email);
    }
}
