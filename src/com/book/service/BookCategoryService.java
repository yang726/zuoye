package com.book.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.book.pojo.BookCategory;
import com.book.pojo.BookInfo;

public interface BookCategoryService {
	int deletecategoryById(Integer id);
	int addNewcategory(String category);
	List<BookCategory> getCategoryList();
	BookCategory findcategoryBycate(@Param("category")String category);
}
