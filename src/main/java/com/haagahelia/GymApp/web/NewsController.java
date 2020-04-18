package com.haagahelia.GymApp.web;

import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.News;
import com.haagahelia.GymApp.domain.NewsForm;
import com.haagahelia.GymApp.domain.NewsRepository;

@Controller
public class NewsController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private NewsRepository newsRepo;
	
	//Controller for adding new news
	@RequestMapping(value = "/addnews", method = RequestMethod.GET)
	public String addNews(Model model) {
		NewsForm newsForm = new NewsForm();
		model.addAttribute("newsform", newsForm);
			
		return "addnews";
	}
		
	//Controller for saving new news
	@RequestMapping(value = "/savenews", method = RequestMethod.POST)
	public String saveNews(@Valid @ModelAttribute("newsform") NewsForm newsForm, BindingResult bindingResult) {
		if(!bindingResult.hasErrors()) {
			News news = new News();
				
			news.setHeader(newsForm.getHeader());
			news.setChapter1(newsForm.getChapter1());
			news.setChapter2(newsForm.getChapter2());
			news.setChapter3(newsForm.getChapter3());
			news.setChapter4(newsForm.getChapter4());
				
			newsRepo.save(news);
		} else {
			return "redirect:/addnews";
		}
			
		return "redirect:/home";
	}
	
	//Controller for editing news
	@RequestMapping(value="/editnews/{id}")
	public String editNews(@PathVariable("id") Long id, Model model) {
		NewsForm newsForm = new NewsForm();
		News news = newsRepo.findNewsBynewsid(id);
			
		newsForm.setId(news.getNewsid());
		newsForm.setHeader(news.getHeader());
		newsForm.setChapter1(news.getChapter1());
		newsForm.setChapter2(news.getChapter2());
		newsForm.setChapter3(news.getChapter3());
		newsForm.setChapter4(news.getChapter4());
			
		model.addAttribute("newsform", newsForm);
			
		return "editnews";
	}
		
	//Controller for saving edited news
	@RequestMapping(value = "/save_edited_news/{id}", method = RequestMethod.POST)
	public String saveEditedNews(@Valid @ModelAttribute("newsform") NewsForm newsForm, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(!bindingResult.hasErrors()) {
			News news = newsRepo.findNewsBynewsid(id);
				
			news.setHeader(newsForm.getHeader());
			news.setChapter1(newsForm.getChapter1());
			news.setChapter2(newsForm.getChapter2());
			news.setChapter3(newsForm.getChapter3());
			news.setChapter4(newsForm.getChapter4());
				
			newsRepo.save(news);
				
		} else {
			return "redirect:../editnews/" + id;
		}
			
		return "redirect:../home";
	}
	
	@RequestMapping(value = "/deletenews/{id}", method = RequestMethod.GET)
	public String deleteNews(@PathVariable("id") Long id) {
		newsRepo.deleteById(id);
		return "redirect:../home";
	}
	
}
