package com.haagahelia.GymApp.domain;

import javax.validation.constraints.Size;

//Form for saving or editing news
public class NewsForm {
	
	private long id;
	
	@Size(min=1, max=255)
	private String header;

	@Size(min=0, max=255)
	private String chapter1;
	
	@Size(min=0, max=255)
	private String chapter2;
	
	@Size(min=0, max=255)
	private String chapter3;
	
	@Size(min=0, max=255)
	private String chapter4;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getChapter1() {
		return chapter1;
	}

	public void setChapter1(String chapter1) {
		this.chapter1 = chapter1;
	}

	public String getChapter2() {
		return chapter2;
	}

	public void setChapter2(String chapter2) {
		this.chapter2 = chapter2;
	}

	public String getChapter3() {
		return chapter3;
	}

	public void setChapter3(String chapter3) {
		this.chapter3 = chapter3;
	}

	public String getChapter4() {
		return chapter4;
	}

	public void setChapter4(String chapter4) {
		this.chapter4 = chapter4;
	}

}
