package com.haagahelia.GymApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.GymApp.web.GymAppController;
import com.haagahelia.GymApp.web.LiftController;
import com.haagahelia.GymApp.web.NewsController;
import com.haagahelia.GymApp.web.UserController;
import com.haagahelia.GymApp.web.WorkoutController;

@RunWith(SpringRunner.class)
@SpringBootTest
class GymAppApplicationTests {
	@Autowired
	private GymAppController gaController;
	
	@Autowired
	private LiftController lController;
	
	@Autowired
	private NewsController nController;
	
	@Autowired
	private UserController uController;
	
	@Autowired
	private WorkoutController wController;

	@Test
	void contextLoads() throws Exception {
		assertThat(gaController).isNotNull();
		
		assertThat(lController).isNotNull();
		
		assertThat(nController).isNotNull();
		
		assertThat(uController).isNotNull();
		
		assertThat(wController).isNotNull();
		
	}

}
