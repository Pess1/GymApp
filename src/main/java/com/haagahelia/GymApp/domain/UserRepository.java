package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findUserByUsername(String username);
	
	List<User> findUserByEmail(String email);
	
	User findUserByUserid(long userid);

}
