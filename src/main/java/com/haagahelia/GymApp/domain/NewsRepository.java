package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
	
	List<News> findNewsByHeader(String header);
	
	News findNewsBynewsid(long newsid);

}
