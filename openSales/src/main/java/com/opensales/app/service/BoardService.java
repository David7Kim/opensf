package com.opensales.app.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.opensales.app.domain.model.Post;
import com.opensales.app.domain.model.PostComments;

public interface BoardService  {
    
    //insert 
    public void addBoard(Map<String,Object>param);
    public Boolean addPost(Map<String,Object> param) throws ParseException;
    public void addComment(Map<String,Object> param);
    
    //GET about Board, post
    public String getBoardList(Boolean authoriz);
    public List<Post> getBoardPostLists(Long boardId);
    public Post getPost(Long postId);
    public Page<Post> getPostList(int page , int size);
    
    public Page<PostComments> getPostCommentList(int page, int size ,Long postId);
    
    //update
    public void updateBoardPostList(Map<String,Object>params);
    public void updatePostComment(Map<String,Object>params);
    //delete
    public void deleteBoardPost(Long postId);
    public void deletePostComment(Long commentId);
    
    
}
