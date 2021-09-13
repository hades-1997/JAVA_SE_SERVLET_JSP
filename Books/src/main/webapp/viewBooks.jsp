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
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">   
    <title>Books</title>
    <style>
        <c:import url="css/style.jsp"></c:import>
        <c:import url="css/view.jsp"></c:import>
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
                   <div class="panel-body">
					 <section id="event-about">
	                    <div class="container-fluid">
	                      <div class="row align-items-center">
	                        <div class="col-lg-4 px-0 align-self-stretch py-11" style="position: relative;">
	                          <div class="bg-holder rounded-lg-right" style="
	                          
	                          background-image: url('${initParam.hostURL}${pageContext.request.contextPath}/FileDisplayServlet/${books.images}'); 
	                          
	                          filter: blur(0px); opacity: 1; transform: matrix(1, 0, 0, 1, 0, 0);" 
	                          
	                          data-zanim-md="{&quot;delay&quot;:0.1,&quot;animation&quot;:&quot;zoom-out&quot;}
	                          "></div>
	                        </div>
	                        <div class="col-10 col-lg-6 col-xl-5 mb-6 mb-lg-0 px-0 mx-auto mt-6 mt-lg-0">
	                          <div class="position-relative border border-300">
	                            <div class="py-3 position-absolute l-0 t-0 mt-6"></div>
	                            <div class="py-6 px-5 px-lg-7 px-xl-8">
	                              
	                              <h4 class="mb-3 mt-5">${books.title}</h4>
	                              
	                              <ul class="text-sans-serif style-check pl-0">
	                                <li>Author: ${books.author}</li>
	                                <li>Price:$ ${books.price} </li>
	                                <li>Date Time: ${books.date_p}</li>
	                                <li>Category: ${books.getNameCategory()}</li>

	                              </ul>
	                              
	                              <p class="text-600 font-italic lead">${books.summary}</p>
	                            </div>
	                          </div>
	                        </div>
	                      </div>
	                    </div><!-- end of .container-->
	                  </section>
	            </div>
            </main>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>

</html>