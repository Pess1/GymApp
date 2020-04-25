package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
	//Method for finding news by header
	List<News> findNewsByHeader(String header);
	
	//Method for returning news object instead of an optional<news>
	News findNewsBynewsid(long newsid);

}
