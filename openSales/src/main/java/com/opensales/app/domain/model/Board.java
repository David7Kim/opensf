package com.opensales.app.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name="open_sf_board")
public class Board {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    
    //게시판명
    @Column(name="board_nm")
    private String board_nm;
    
    //게시판 생성일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_date" , updatable = false)
    private Date created_date;
    
    //게시판 수정일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="updated_date")
    private Date updated_date;
    
    @PrePersist
    protected void onCreate() {
        updated_date = created_date = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_date = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoard_nm() {
        return board_nm;
    }

    public void setBoard_nm(String board_nm) {
        this.board_nm = board_nm;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }
}
