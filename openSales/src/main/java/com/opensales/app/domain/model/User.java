package com.opensales.app.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

@Entity
@Table(name = "open_sf_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //사용자명
    @Column(name="user_name")
    private String userName;

    //사용자 계정
    @Column(name="user_account")
    @NotNull
    private String userAccount;
    
    //사용자 사번
    @Column(name="user_employee_number")
    private String userEmployeeNumber;
    
    //사용자 생성일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_date", updatable=false)
    private Date createdDate;
    
    //사용자 수정일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="updated_date")
    private Date updatedDate;

    //사용자 명
    @Column(name="first_name")
    @NotNull
    private String firstName;

    //사용자 성
    @Column(name="last_name")
    @NotNull
    private String lastName;

    //사용자 이메일
    @Column(name="email")
    @NotNull
    private String email;

    //사용자 패스워드
    @Column(name="password")
    private String password;
    
    @OneToOne(mappedBy ="userProfileId" , cascade= CascadeType.ALL , orphanRemoval= true)
    private UserProfile userProfileId;
    
    @OneToMany(mappedBy ="userId" , cascade= CascadeType.ALL, orphanRemoval = true)
    private Set<Post> postId;
    
    @OneToMany(mappedBy ="scheduleUserId" ,cascade= CascadeType.ALL, orphanRemoval = true)
    private Set<Calendar> calendar;
    
    @PrePersist
    protected void onCreate() {
        updatedDate = createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserEmployeeNumber() {
        return userEmployeeNumber;
    }

    public void setUserEmployeeNumber(String userEmployeeNumber) {
        this.userEmployeeNumber = userEmployeeNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
