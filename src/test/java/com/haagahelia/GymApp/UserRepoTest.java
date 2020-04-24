package com.haagahelia.GymApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.GymApp.domain.User;
import com.haagahelia.GymApp.domain.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
final public class UserRepoTest {
	
	@Autowired
	private UserRepository uRepo;
	
	//Testing for finding with username which is unique
	@Test
	public void findUserByUsernameShouldReturnUser() {
		User user = uRepo.findUserByUsername("admin");
		
		assertThat(user.getUsername()).isEqualTo("admin");
	}
	
	//Testing finding with email which doesnt have to be unique
	@Test
	public void findUserByEmailShouldReturnListUsers() {
		List<User> users = uRepo.findUserByEmail("admin@email.com");
		
		assertThat(users.get(0).getEmail()).isEqualTo("admin@email.com");
	}
	
	//Testing my own method for finding user by id since default returns <optional> user
	@Test
	public void findUserByUserIdShouldReturnUser() {
		Long id = uRepo.findUserByUsername("admin").getUserId();
		
		User user = uRepo.findUserByUserid(id);
		
		assertThat(user).isNotNull();
		
		assertThat(user.getUsername()).isEqualTo("admin");
	}
	
	//Testing for creating a new user
	@Test
	public void createNewUser() {
		User user = new User("uTest", "uTest", "utest@email.com", "USER", 90, 180, "Male");
		
		uRepo.save(user);
		
		User testUser = uRepo.findUserByUsername("uTest");
		
		assertThat(testUser.getEmail()).isNotNull();
	}
	
	//Testing for deleting a user
	@Test
	public void deleteUser() {
		User user = new User("uTest", "uTest", "utest@email.com", "USER", 90, 180, "Male");
		
		uRepo.save(user);
		
		Long id = uRepo.findUserByUsername("uTest").getUserId();
		
		uRepo.deleteById(id);
		
		User testUser = uRepo.findUserByUsername("uTest");
		
		assertThat(testUser).isNull();
		
	}

}
