package com.book.service;

import java.util.List;

import com.book.pojo.BookInfo;

public interface BookInfoService {
	int getcount(String bookName);
	List<BookInfo> getBookList(String bookName,int from,int pageSize);
	int deleteBookById(Integer id);
	int alterBookById(BookInfo book);
	int addNewBook(BookInfo book);
	BookInfo findBookById(int id);
}
