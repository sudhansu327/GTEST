package com.litmus.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.litmus.app.dao.User;

@Component
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u WHERE u.username= :username and u.userpassword=:userpassword")
	List<User> findByUsernamepassword(@Param("username") String username,@Param("userpassword") String userpassword);
	
	
	@Query("SELECT u FROM User u WHERE u.username= :username")
	List<User> findByUsername(@Param("username") String username);
	
	
	@Modifying
	@Query("UPDATE User u SET u.userpassword=:userpassword WHERE u.username= :username")
	List<User> modifyUserPassword(@Param("username") String username, @Param("userpassword") String userpassword);
}
