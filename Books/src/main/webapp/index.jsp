<%@ page contentType="text/html;charset=UTF-8" language="java"
    import="com.ray.books.dbmodels.*
            , com.ray.books.helpers.*
            , java.sql.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">   
    <title>Books</title>
    <style>
        <c:import url="css/style.jsp"></c:import>
    </style>
</head>

<body>
	<c:choose>
        <c:when test="${sessionScope.Books == null}" >
            <c:redirect url="${initParam.hostURL}${pageContext.request.contextPath}/bookscontrollers.do" />
        </c:when>
    </c:choose>
	<c:import url="header.jsp"></c:import>
    <div class="container-fluid">
        <div class="row">
            <c:import url="navbar.jsp"></c:import>
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="chartjs-size-monitor">
                    <div class="chartjs-size-monitor-expand">
                        <div class=""></div>
                    </div>
                    <div class="chartjs-size-monitor-shrink">
                        <div class=""></div>
                    </div>
                </div>
                <div class="" style="padding: 5px;"></div>
                <h2>Tổng hợp sách</h2>
                 <div class="" style="padding: 20px;"></div>
                 <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title" style="float:left"><i class="fa fa-pencil"></i>Books</h3>
                         <div class="pull-right">
                         <c:if test="${sessionScope.authorized_user.decentral_id eq 1}">
                            <a href="${initParam.hostURL}${pageContext.request.contextPath}/Category/addBook.jsp" class="btn btn-success"><i class="fa fa-plus"></i></a>
                            <button type="button" data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger" id="button-delete" data-original-title="Xóa">
                                <i class="fa fa-trash-o"></i>
                            </button>
                        </c:if>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                    <div class="panel-body">
		                <div class="table-responsive">
		                    <table class="table table-striped table-sm">
		                        <thead>
		                            <tr>
		                                <th scope="col">STT</th>
		                                <th scope="col">Title</th>
		                                <th scope="col">Price</th>
		                                <th scope="col">Author</th>
		                                <th scope="col">Date</th>
		                                <th scope="col">Category</th>
		                                <th scope="col">#</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        <c:forEach var="tempBooks" items="${sessionScope.Books}" varStatus="iterationCount">
		                        	<c:url var="updateLink" value="${initParam.hostURL}${pageContext.request.contextPath}/bookscontrollers.do">
										<c:param name="command" value="LOAD"></c:param>
										<c:param name="bookId" value="${tempBooks.book_id}"></c:param>
									</c:url>
									<c:url var="deleteLink" value="${initParam.hostURL}${pageContext.request.contextPath}/bookscontrollers.do">
										<c:param name="command" value="DELETE"></c:param>
										<c:param name="bookId" value="${tempBooks.book_id}"></c:param>
									</c:url>
									<c:url var="viewLink" value="${initParam.hostURL}${pageContext.request.contextPath}/bookscontrollers.do">
										<c:param name="command" value="VIEW"></c:param>
										<c:param name="bookId" value="${tempBooks.book_id}"></c:param>
									</c:url>
		                            <tr>
		                                <td>${iterationCount.count}</td>
		                                <td>${tempBooks.title}</td>
		                                <td>${tempBooks.price}</td>
		                                <td>${tempBooks.author}</td>
		                                <td>${tempBooks.date_p}</td>
		                                <td>${tempBooks.getNameCategory()}</td>
		                                 
		                                 <td>
		                                 	<c:if test="${sessionScope.authorized_user.decentral_id eq 1}">
                                            <a href="${updateLink}"  class="btn btn-primary"><i class="fa fa-pencil"></i></a>
                                            <a href="${deleteLink}" class="btn btn-danger"  onclick="if(!confirm('Are you sure you want to delete this Books?')) return false;"><i class="fa fa-trash-o"></i></a>
                                             </c:if> 
                                             <a href="${viewLink}"  class="btn btn-success">
	                                             <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
												  <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
												  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
												</svg>
											</a>
                                        </td>
                                       
		                            </tr>
		                          </c:forEach>
		                        </tbody>
		                    </table>
		                </div>
		            </div>
            </main>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>

</html>