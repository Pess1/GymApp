package com.haagahelia.GymApp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class News {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long newsid;
	
	@Column(name = "header", nullable=false)
	private String header;

	@Column(name = "chapter1", nullable = false)
	private String chapter1;
	
	@Column(name = "chapter2", nullable = false)
	private String chapter2;
	
	@Column(name = "chapter3", nullable = false)
	private String chapter3;
	
	@Column(name = "chapter4", nullable = false)
	private String chapter4;
	
	public News() {
		
	}
	
	public News(String header, String chapter1, String chapter2, String chapter3, String chapter4) {
		this.setHeader(header);
		this.setChapter1(chapter1);
		this.setChapter2(chapter2);
		this.setChapter3(chapter3);
		this.setChapter4(chapter4);
	}

	public long getNewsid() {
		return newsid;
	}

	public void setNewsid(long newsid) {
		this.newsid = newsid;
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
	
	public String toString() {
		return this.header + this.chapter1 + this.chapter2 + this.chapter3 + this.chapter4;
	}
}
