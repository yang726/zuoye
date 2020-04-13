package com.book.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.book.pojo.BookCategory;
import com.book.pojo.BookInfo;
import com.book.pojo.Pager;
import com.book.service.BookInfoService;
import com.book.service.BookInfoServiceImpl;
import com.mysql.jdbc.StringUtils;

/**
 * Servlet implementation class BookController
 */
@WebServlet("/BookController")
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookInfoService bis = new BookInfoServiceImpl();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		if ("show".equals(op)) {
			showInfo(request,response);
		}else if("delete".equals(op)) {
			deletebook(request,response);
		}
		else if("add".equals(op)){
		addbook(request,response);
		}else if("alter".equals(op)) {
	    addbook(request,response);
		}else if("find".equals(op)) {
	   findidbook(request,response);
		}
	}
	private void findidbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 根据图书id获取图书信息
		int  id=Integer.parseInt(request.getParameter("id"));
		BookInfo result = bis.findBookById(id);
		// 把book对象放入request中
		request.setAttribute("book", result);
		request.getRequestDispatcher("BookController?op=add").forward(request, response);
		return;
	}

	private void alterbook(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("utf-8");
		String bookName =request.getParameter("bookName");
		String author =request.getParameter("author");
		String publisher =request.getParameter("publisher");
		String photo = request.getParameter("photo");
		Double price = Double.parseDouble(request.getParameter("price"));
		int id =Integer.parseInt(request.getParameter("id"));
		int categoryId =Integer.parseInt(request.getParameter("categoryId"));
		try {
			String realPath = getServletContext().getRealPath("/static/file");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletContext servletContext = this.getServletConfig().getServletContext();
			java.io.File repository = (java.io.File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(2 * 1024 * 1024); // 2M
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> items = upload.parseRequest(request);
			java.util.Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
				} else {
					String fileName = item.getName();
					String contentType = item.getContentType();
					if (contentType.equals("image/png") || contentType.equals("image/jpeg")
							|| contentType.equals("image/gif")) {
						String rand = UUID.randomUUID().toString();
						photo = rand + fileName.substring(fileName.lastIndexOf("."));
						java.io.File uploadedFile = new java.io.File(realPath, photo);
						item.write(uploadedFile);
					} else {
						photo = null;// 没有上传文件
					}
				}
			}
			// 构造BookInfo对象
			BookInfo book = new BookInfo(id, bookName, author, categoryId, publisher, price, photo);
			// 修改数据库中对应的书籍
			bis.alterBookById(book);
			// 返回相应数据
			request.setAttribute("message", "修改书籍成功");

			request.getRequestDispatcher("BookController?op=show").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	private void addbook(HttpServletRequest request, HttpServletResponse response) {
	
		response.setCharacterEncoding("utf-8");
		// 声明变量，用于获取前端数据
		String bookName =request.getParameter("bookName");
		String author =request.getParameter("author");
		String publisher =request.getParameter("publisher");
		String photo = request.getParameter("photo");
		Double price = Double.parseDouble(request.getParameter("price"));
		int categoryId =Integer.parseInt(request.getParameter("categoryId"));
		try {
			String realPath = getServletContext().getRealPath("/static/file");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(2*1024*1024);	// 2M
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> items = upload.parseRequest(request);
			java.util.Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();
			    if (item.isFormField()) {
			    	String name = item.getFieldName();
					String value = item.getString();
			    } else {					
			        String fileName = item.getName();	
			        String contentType = item.getContentType();
			        if(contentType.equals("images/png") 
			        		|| contentType.equals("images/jpeg")
			        		|| contentType.equals("images/gif")) {
			        	String rand = UUID.randomUUID().toString();
			        	photo = rand+fileName.substring(fileName.lastIndexOf("."));
				        java.io.File uploadedFile = new java.io.File(realPath,photo);
				        item.write(uploadedFile);
				     }
			        else {
			        	request.setAttribute("message", "只能为PNG或JPG或GIF图片");
			        	request.getRequestDispatcher("/book_mgr").forward(request,response);
			        	return;
			        }
			    }
			}
	        BookInfo book = new BookInfo(null, bookName, author, categoryId, publisher, price, photo);
	        bis.addNewBook(book);

	        request.setAttribute("message", "添加书籍成功");
			request.getRequestDispatcher("BookController?op=show").forward(request, response);
	        return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void deletebook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	
		String sId = request.getParameter("id");
		if(StringUtils.isNullOrEmpty(sId)) {
			request.setAttribute("infor", "删除图书失败");
			request.getRequestDispatcher("BookController?op=show").forward(request, response);
			return;
		}
		Integer id = Integer.valueOf(sId);

		int result = bis.deleteBookById(id);
		if(result != 0)
			request.setAttribute("infor", "删除图书成功");
		else
			request.setAttribute("infor", "删除图书失败");
		request.getRequestDispatcher("BookController?op=show").forward(request, response);
		return;

		
	}

	private void showInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		showPageList(request, response);
		response.sendRedirect("user/index.jsp");
	}

	private void showPageList(HttpServletRequest request, HttpServletResponse response) {
		String pageIndex = request.getParameter("pageIndex");
		String bookName = request.getParameter("bookName");
		int currPage = 0;
		Pager pg = new Pager();
		int totalCount = bis.getcount(bookName);
		pg.setTotalCount(totalCount);
		if (StringUtils.isNullOrEmpty(pageIndex)) {
			currPage = 1;
		}else {
			if(Integer.parseInt(pageIndex)<=0) {
				currPage = 1;
			}else if(Integer.parseInt(pageIndex)>=pg.getTotalPages()) {
				currPage = pg.getTotalPages();
			}else {
				currPage = Integer.parseInt(pageIndex);
			}
		}
		pg.setCurrPage(currPage);
		int from = (currPage-1)*pg.getPageSize();
		List list = bis.getBookList(bookName, from, pg.getPageSize());
		pg.setPageLists(list);
		request.getSession().setAttribute("pg", pg);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
