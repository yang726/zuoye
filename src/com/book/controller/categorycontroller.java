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
		
		// ����ͼ��id��ȡͼ����Ϣ
		String category=request.getParameter("category");
		BookCategory result = bcy.findcategoryBycate(category);
		// ��book�������request��
		request.setAttribute("book", result);
		request.getRequestDispatcher("user/index.jsp").forward(request, response);
		return;
		
	}
	private void show(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		// ���÷��񣬻�ȡͼ�������Ϣ�б�
		List<BookCategory> categories = bcy.getCategoryList();
		// �ѷ�����Ϣ�б����request��
		request.setAttribute("categories", categories);
		response.sendRedirect("admin/category-mgr.jsp");
	}
	private void addbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// ���մ��ݹ����Ĳ���
		String cate= request.getParameter("category");
		if(StringUtils.isNullOrEmpty(cate)) {
			request.setAttribute("message","������Ϣ����Ϊ��");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		// ���÷��������ӷ���
		int result = bcy.addNewcategory(cate);
		if(result != 0) {
			request.setAttribute("message","��ӷ���ɹ�");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		else {
			request.setAttribute("message","��ӷ���ʧ��");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		
	}
	private void deletebook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// ���ղ���
		String sId = request.getParameter("id");
		if(StringUtils.isNullOrEmpty(sId)) {
			request.setAttribute("msg","����Ĳ���");
			request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
			return;
		}
		Integer id = Integer.valueOf(sId);
		// ���÷������ɾ��
		int result = bcy.deletecategoryById(id);
		if(result == 0) {
			request.setAttribute("msg","ɾ������ʧ��");
		}
		else {
			request.setAttribute("msg","ɾ������ɹ�");
		}
		request.getRequestDispatcher("admin/category-mgr.jsp").forward(request,response);
	
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
