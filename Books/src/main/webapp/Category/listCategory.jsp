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
        <c:import url="../css/style.jsp"></c:import>
    </style>
</head>

<body>
	<c:import url="../header.jsp"></c:import>
	<c:choose>
        <c:when test="${sessionScope.Category == null}" >
            <c:redirect url="${initParam.hostURL}${pageContext.request.contextPath}/categorycontrollers.do" />
        </c:when>
    </c:choose>
    <div class="container-fluid">
        <div class="row">
            <c:import url="../navbar.jsp"></c:import>
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="chartjs-size-monitor">
                    <div class="chartjs-size-monitor-expand">
                        <div class=""></div>
                    </div>
                    <div class="chartjs-size-monitor-shrink">
                        <div class=""></div>
                    </div>
                </div>
                <div class="" style="padding: 10px;"></div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title" style="float:left"><i class="fa fa-pencil"></i> Danh sách của thể loại</h3>
                         <div class="pull-right">
                            <a href="${initParam.hostURL}${pageContext.request.contextPath}/Category/addCategory.jsp" data-toggle="tooltip" data-placement="top" title="" class="btn btn-success" data-original-title="Thêm mới"><i class="fa fa-plus"></i></a>
                            <button type="button" data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger" id="button-delete" data-original-title="Xóa">
                                <i class="fa fa-trash-o"></i>
                            </button>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                    <div class="panel-body">
		                <div class="table-responsive">
		                    <table class="table table-striped table-sm">
		                        <thead>
		                            <tr>
		                                <th scope="col">STT</th>
		                                <th scope="col">Name</th>
		                                <th scope="col">#</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        <c:forEach var="tempCategory" items="${sessionScope.Category}" varStatus="iterationCount">
		                        	<c:url var="updateLink" value="${initParam.hostURL}${pageContext.request.contextPath}/categorycontrollers.do">
										<c:param name="command" value="LOAD"></c:param>
										<c:param name="categoryId" value="${tempCategory.category_id}"></c:param>
									</c:url>
									<c:url var="deleteLink" value="${initParam.hostURL}${pageContext.request.contextPath}/categorycontrollers.do">
										<c:param name="command" value="DELETE"></c:param>
										<c:param name="categoryId" value="${tempCategory.category_id}"></c:param>
									</c:url>
		                            <tr>
		                                <td>${iterationCount.count}</td>
		                                <td>${tempCategory.name_category}</td>
		                                <td>
                                            <a href="${updateLink}"  class="btn btn-primary"><i class="fa fa-pencil"></i></a>
                                            <a href="${deleteLink}" class="btn btn-danger"  onclick="if(!confirm('Are you sure you want to delete this city?')) return false;"><i class="fa fa-trash-o"></i></a>
                                        </td>
		                            </tr>
		                        </c:forEach>
		                        </tbody>
		                    </table>
		                </div>
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