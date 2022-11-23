package com.opensales.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensales.app.domain.model.Post;

@Repository
public interface PostRespository extends JpaRepository<Post,Long>{
    Post findById(long Id);
    List<Post>findByBoardId(long boardId);
    Page<Post> findAll(Pageable pageable);
}
