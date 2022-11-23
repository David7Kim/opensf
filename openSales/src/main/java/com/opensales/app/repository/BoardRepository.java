package com.opensales.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensales.app.domain.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{
    

}
