package com.opensales.app.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.opensales.app.domain.model.UserProfile;


public interface RegisterService {
    public String makeUserAccount(Map<String,Object> user) throws ParseException; 
    public void deleteUserAccount(Map<String,Object> user);
    public void updateUserAccount(Map<String,Object> user);
    public void setAdminToUserAccount(Boolean isAdmin); 
    public String findUserAccountByEmail(String email);
    public Boolean getUserInfoIsTrueFalse(Map<String,Object>params);
//    public User getuserInfo (String userAccount);
    public UserProfile getuserInfo (String userId);
    public List<UserProfile> getUserInfoes();
}
