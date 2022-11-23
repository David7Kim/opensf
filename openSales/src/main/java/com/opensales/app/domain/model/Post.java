package com.opensales.app.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="open_sf_posts")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    //게시판 아이디
    @Column(name="board_id")
    private long boardId;

    //게시글 내용
    @Column(name="contents")
    private String contents;
    
    //게시글 작성자
    @Column(name="writer")
    private String writer;

    //게시글 생성일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_date", updatable=false)
    private Date createdDate;
    
    //게시글 수정일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="updated_date")
    private Date updatedDate;
    
    @ManyToOne
    @JoinColumn(name="post_user_id")
    private User userId ;
    
    @OneToMany(mappedBy ="commentPostId" ,cascade= CascadeType.ALL , orphanRemoval = true)
    private Set<PostComments> postId;
    
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

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
