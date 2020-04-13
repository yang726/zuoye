package com.book.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.book.commons.MybatisUtils;
import com.book.dao.book.BookMapper;
import com.book.pojo.BookInfo;

public class BookInfoServiceImpl implements BookInfoService {
	private SqlSession sqlSession;
	@Override
	public int getcount(String bookName) {
		sqlSession = MybatisUtils.createSqlSession();
		int count = sqlSession.getMapper(BookMapper.class).getcount(bookName);
		MybatisUtils.closeSqlSession(sqlSession);
		return count;
	}

	@Override
	public List<BookInfo> getBookList(String bookName, int from, int pageSize) {
		sqlSession = MybatisUtils.createSqlSession();
		List<BookInfo> list = sqlSession.getMapper(BookMapper.class).getBookList(bookName, from, pageSize);
		MybatisUtils.closeSqlSession(sqlSession);
		return list;
	}

	@Override
	public int deleteBookById(Integer id) {
	
		SqlSession sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(BookMapper.class).deleteBookById(id);
		sqlSession.commit();
		MybatisUtils.closeSqlSession(sqlSession);
		return result;
	}

	@Override
	public int alterBookById(BookInfo book) {
		SqlSession sqlSession = MybatisUtils.createSqlSession();
	    int	result = sqlSession.getMapper(BookMapper.class).alterBookById(book);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int addNewBook(BookInfo book) {
	
		SqlSession sqlSession = MybatisUtils.createSqlSession();
		int	result = sqlSession.getMapper(BookMapper.class).addNewBook(book);
		sqlSession.commit();
		sqlSession.close();
		return result;
		
	}
	@Override
	public BookInfo findBookById(int id) {
		SqlSession sqlSession = MybatisUtils.createSqlSession();
		BookInfo	book = sqlSession.getMapper(BookMapper.class).findBookById(id);
		sqlSession.commit();
		sqlSession.close();
		return book;
	}

}
