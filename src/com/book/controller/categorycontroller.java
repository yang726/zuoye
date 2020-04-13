package com.book.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.pojo.BookCategory;
import com.book.pojo.BookInfo;
import com.book.service.BookCategoryService;
import com.book.service.BookCategoryServiceimpl;
import com.mysql.jdbc.StringUtils;
@WebServlet("/categorycontroller")
public class categorycontroller extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private BookCategoryService bcy=new BookCategoryServiceimpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		 if("delete".equals(op)) {
			deletebook(request,response);
		}
		else if("add".equals(op)){
		addbook(request,response);
		}else if ("show".equals(op)){
			show(request,response);
		}else if("find".equals(op)) {
			findcate(request,response);
		}
	}
	private void findcate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 根据图书id获取图书信息
		String category=request.getParameter("category");
		BookCategory result = bcy.findcategoryBycate(category);
		// 把book对象放入request中
		request.setAttribute("book", result);
		request.getRequestDispatcher("user/index.jsp").forward(request, response);
		return;
		
	}
	private void show(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		// 调用服务，获取图书分类信息列表
		List<BookCategory> categories = bcy.getCategoryList();
		// 把分类信息列表放入request中
		request.setAttribute("categories", categories);
		response.sendRedirect("admin/category-mgr.jsp");
	}
	private void addbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 接收传递过来的参数
		String cate= request.getParameter("category");
		if(StringUtils.isNullOrEmpty(cate)) {
			request.setAttribute("message","分类信息不能为空");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		// 调用服务，完成添加分类
		int result = bcy.addNewcategory(cate);
		if(result != 0) {
			request.setAttribute("message","添加分类成功");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		else {
			request.setAttribute("message","添加分类失败");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		
	}
	private void deletebook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 接收参数
		String sId = request.getParameter("id");
		if(StringUtils.isNullOrEmpty(sId)) {
			request.setAttribute("msg","错误的操作");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		Integer id = Integer.valueOf(sId);
		// 调用服务，完成删除
		int result = bcy.deletecategoryById(id);
		if(result == 0) {
			request.setAttribute("msg","删除分类失败");
		}
		else {
			request.setAttribute("msg","删除分类成功");
		}
		request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
	
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
