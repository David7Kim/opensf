package com.opensales.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensales.app.domain.model.PostComments;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComments,Long>{
    
//    Page<PostComments> findByCommentPostId(PageRequest pageRequest,Long postId);
    
}
