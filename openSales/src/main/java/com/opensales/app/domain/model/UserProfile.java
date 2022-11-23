package com.opensales.app.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
  * @FileName : UserProfile.java
  * @Project : openSales
  * @Date : 2022. 10. 27. 

  * @작성자 : kimdonghyeon
  * @변경이력 :
  * @프로그램 설명 :
  */
@Entity
@Table(name="open_sf_user_profile")
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="user_home_option")
    private String userHomeOption;
    
    @Column(name="user_security_puzzle_id")
    private Long userSecurityId;
    
    @Column(name="user_sercurity_loop_first")
    private Long userSecurityFirst;
    
    @Column(name="user_sercurity_loop_second")
    private Long userSecuritySecond;
    
    @Column(name="user_sercurity_loop_third")
    private Long userSecurityThird;
    
    @Column(name="user_is_admin")
    private Boolean userIsAdmin;
    
    @Column(name="user_profile_pic_path")
    private String userProfilePicPath;
    
    @OneToOne
    @JoinColumn(name="user_profile_id")
    private User userProfileId ;
    
    //@OneToOne(mappedBy ="userProfile" , cascade= CascadeType.ALL , orphanRemoval= true)
    /*@OneToOne
    @JoinColumn(name="user_profile_id")
    private Calendar calendarUserProfile;*/

    public long getId() {
        return id;
    }

    public Boolean getUserIsAdmin() {
        return userIsAdmin;
    }

    public void setUserIsAdmin(Boolean userIsAdmin) {
        this.userIsAdmin = userIsAdmin;
    }

    public String getUserProfilePicPath() {
        return userProfilePicPath;
    }

    public void setUserProfilePicPath(String userProfilePicPath) {
        this.userProfilePicPath = userProfilePicPath;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserHomeOption() {
        return userHomeOption;
    }

    public void setUserHomeOption(String userHomeOption) {
        this.userHomeOption = userHomeOption;
    }

    public Long getUserSecurityId() {
        return userSecurityId;
    }

    public void setUserSecurityId(Long userSecurityId) {
        this.userSecurityId = userSecurityId;
    }

    public Long getUserSecurityFirst() {
        return userSecurityFirst;
    }

    public void setUserSecurityFirst(Long userSecurityFirst) {
        this.userSecurityFirst = userSecurityFirst;
    }

    public Long getUserSecuritySecond() {
        return userSecuritySecond;
    }

    public void setUserSecuritySecond(Long userSecuritySecond) {
        this.userSecuritySecond = userSecuritySecond;
    }

    public Long getUserSecurityThird() {
        return userSecurityThird;
    }

    public void setUserSecurityThird(Long userSecurityThird) {
        this.userSecurityThird = userSecurityThird;
    }

    public User getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(User userProfileId) {
        this.userProfileId = userProfileId;
    }
}
