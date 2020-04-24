package com.haagahelia.GymApp;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.GymApp.domain.News;
import com.haagahelia.GymApp.domain.NewsRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
final public class NewsRepoTest {
	
	@Autowired
	private NewsRepository nRepo;
	
	//Testing for finding with header
	@Test
	public void findNewsByHeaderShouldReturnNews() {
		News news = new News("Test", "test", "", "", "");
		
		nRepo.save(news);
		
		List<News> newsList = nRepo.findNewsByHeader("Test");
		
		assertThat(newsList).isNotEmpty();
		
		assertThat(newsList.get(0).getHeader()).isEqualTo("Test");
	}
	
	
	//Testing if my own method of finding by id works and also test if we can create new news
	@Test
	public void findNewsBynewsidShouldReturnNews() {
		News news = new News("Test", "test", "", "", "");
		
		nRepo.save(news);
		
		Long id = news.getNewsid();
		
		News testNews = nRepo.findNewsBynewsid(id);
		
		assertThat(testNews.getHeader()).isEqualTo("Test");
	}
	
	//Testing for deleting news
	@Test
	public void testDeleteNews() {
		News news = new News("Test", "test", "", "", "");
		
		nRepo.save(news);
		
		Long id = news.getNewsid();
		
		nRepo.deleteById(id);
		
		News testNews = nRepo.findNewsBynewsid(id);
		
		assertThat(testNews).isNull();
	}
	

}
