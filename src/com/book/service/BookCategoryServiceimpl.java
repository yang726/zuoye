package com.book.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.book.commons.MybatisUtils;
import com.book.dao.book.BookMapper;
import com.book.dao.category.CategoryMapper;
import com.book.pojo.BookCategory;
import com.book.pojo.BookInfo;

public class BookCategoryServiceimpl implements BookCategoryService {

	@Override
	public int deletecategoryById(Integer id) {
		SqlSession sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(CategoryMapper.class).deleteBookCategoryById(id);
		sqlSession.commit();
		MybatisUtils.closeSqlSession(sqlSession);
		return result;
	}
	@Override
	public int addNewcategory(String cate) {
		// TODO Auto-generated method stub

		SqlSession sqlSession = MybatisUtils.createSqlSession();
		int	result = sqlSession.getMapper(CategoryMapper.class).saveCategory(cate);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	@Override
	public List<BookCategory> getCategoryList() {
		SqlSession sqlSession = MybatisUtils.createSqlSession();
		List<BookCategory> list = sqlSession.getMapper(CategoryMapper.class).getCategoryList();
		MybatisUtils.closeSqlSession(sqlSession);
		return list;
	}
	@Override
	public BookCategory findcategoryBycate(String category) {
		SqlSession sqlSession = MybatisUtils.createSqlSession();
		BookCategory	cate = sqlSession.getMapper(CategoryMapper.class).findcategoryBycate(category);
		sqlSession.commit();
		sqlSession.close();
		return cate;
	}

}
