package com.opensales.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensales.app.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
      public User findByUserAccount(String userAccount);
      public User findByEmail(String email);
      public User findById(Long userId);
      
}
