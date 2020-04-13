<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>图书网后台管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mgr.css"/>
  </head>  
  <body>
    <div id="container">
    	<div id="header"><h1>智远教育--图书网后台管理系统</h1></div>
    	<div id="info">张三，您好！&nbsp;&nbsp;<a href="admin-user-login.html">登出</a></div>
    	<div class="menu">
    		<ul>
    			<li><a href="admin-home.html">首页</a></li>
    			<li><a href="category-mgr.html">图书分类管理</a></li>
    			<li><a href="book-mgr.html">图书管理</a></li>
    			<li><a href="#">购书订单管理</a></li>
    		</ul>	
    	</div>
    	<div id="main">
			<div class="section-left">    	
				<h2>编辑图书信息</h2>
				<p style="color: red;">${message }</p>
				<form action="BookController?op=alter&${book.id }" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value="${book.id }" />
					<p>图书书名：<input type="text" name="bookName" value="${book.bookName }"  /></p>
					<p>图书作者：<input type="text" name="author" value="${book.author }"  /></p>
					<p>图书分类：<input type="text" name="categoryId" value="${book.categoryId }"  />
						
					</p>
					<p>图书售价：<input type="number" name="price" value="${book.price }" step="0.01" /></p>
					<p>图书出版社：<input type="text" name="publisher" value="${book.publisher }"  /></p>  
					<c:choose>
						<c:when test="${!empty book.photo }">
							<p>当前图片：<img id="preview" src="${pageContext.request.contextPath}/static/file/${book.photo}"/></p>
						</c:when>
						<c:otherwise>
							<p>当前图片：<img id="preview" src="" style="display: none;"/></p>
						</c:otherwise>
					</c:choose>
					<p>图书图片：<input type="file" name="photo" onchange="viewImage(this)" value="${book.photo }" /></p>   				 				
					<p><input type="submit" value=" 修 改 "  />&nbsp;</p>					
				</form>
			</div>
			<div class="section-right"></div>			
			<div class="cf"></div>
		</div>  	
				<div id="footer"><p>版权所有&copy;智远教育</p></div>
	</div>
  </body>
</html>
