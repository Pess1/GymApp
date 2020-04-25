package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	//Method for finding a user by username
	User findUserByUsername(String username);
	
	//Method for finding users by their email
	List<User> findUserByEmail(String email);
	
	//Method that returns user instead of optional<user>
	User findUserByUserid(long userid);

}
