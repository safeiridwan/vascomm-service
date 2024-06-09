package com.vascomm.repository;

import com.vascomm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
    User findByUserId(String id);
    @Query("SELECT u FROM User u WHERE u.userId = ?1 AND u.userStatus = ?2")
    User findByUserIdAndUserStatus(String id, Boolean status);
}
