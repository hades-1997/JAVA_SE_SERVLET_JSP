<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Add Books</title>
    <style>
        <c:import url="css/style.jsp"></c:import>
    </style>
</head>

<body>
	<c:import url="header.jsp" />
	<c:choose>
        <c:when test="${sessionScope.Category == null}" >
            <c:redirect url="${initParam.hostURL}${pageContext.request.contextPath}/bookscontrollers.do" />
        </c:when>
     </c:choose>
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
                <h2>Add Books</h2>
                 <div class="" style="padding: 20px;"></div>
                 
                <form class="row g-3" method="POST" action="fileuploadservlet"  enctype="multipart/form-data" >
                    <div class="col-8 ">
                        <label for="file" class="form-label">Default file input example</label>
                        <input class="form-control" type="file" id="file" name="file">
                    </div>
                    <div class="mb-3"></div>
                    <div class="col-12">
                      <button type="submit" class="btn btn-primary" name="addImages">Upload Image</button>
                    </div>
                 </form>  
            </main>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>

</html>