package com.book.dao.category;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.book.pojo.BookCategory;

public interface CategoryMapper {
	List<BookCategory> getCategoryList();
	int saveCategory(String cate);

	int deleteBookCategoryById(Integer id);
	BookCategory findcategoryBycate(@Param("category")String category);
}
