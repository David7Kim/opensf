package com.opensales.app.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="open_sf_post_comments")
public class PostComments {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    
    //게시물 ID
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post commentPostId;
    
    //댓글 원 댓글ID
    @Column(name ="comment_origin_id")
    private Long commentOriginId;
    
    //댓글 작성자 Id
    @Column(name="comment_user_id")
    private long commentWriterId;
    
    //댓글 작성자
    @Column(name="comment_writer")
    private String commentWriter;

    //댓글 내용
    @Column(name="commenet_content")
    private String contents;

    //댓글 생성일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_date", updatable=false)
    private Date createdDate;
    
    //댓글 수정일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="updated_date")
    private Date updatedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getCommentPostId() {
        return commentPostId;
    }

    public void setCommentPostId(Post commentPostId) {
        this.commentPostId = commentPostId;
    }

    public Long getCommentOriginId() {
        return commentOriginId;
    }

    public void setCommentOriginId(Long commentOriginId) {
        this.commentOriginId = commentOriginId;
    }

    public long getCommentWriterId() {
        return commentWriterId;
    }

    public void setCommentWriterId(long commentWriterId) {
        this.commentWriterId = commentWriterId;
    }

    public String getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(String commentWriter) {
        this.commentWriter = commentWriter;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
    
}
