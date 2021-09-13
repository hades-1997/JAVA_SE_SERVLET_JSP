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
    <title>Sign IN</title>
    <style>
        @media (min-width: 768px){
            .container--mini {
                max-width: 380px;
                position: absolute;
                top: 50%;
                left: 50%;
                width: 343px;
                margin-left: -175px;
                margin-top: -250px;
            }

        }
        #wp-submit {
            width: 100%;
            background: #7952B3;
            color: #ffffff;
        }    
        .form-group {
        margin-bottom: 1.5rem;
        }
    </style>
</head>

<body>
    <div class="container" >
        <div class="container container--mini">

        <img class="img-fluid mx-auto d-block mb-5" src="https://themes.getbootstrap.com/wp-content/themes/bootstrap-marketplace/assets/images/elements/bootstrap-logo.svg" alt="">
                <div class="row" style="padding: 10px; text-align: center;color:red;"> Login Fail </div>
            <form  id="loginform" method="post" action="loginUser.do">
	                <div class="form-group">
	                    <label for="username">Username</label>
	                    <input type="text" name="username" id="username" class="form-control" value="${cookie.credentials_uid.value}" size="20">
	                </div>
	                <div class="form-group">
	                    <label for="password">Password</label>
	                    <input type="password" name="password" id="user_pass" class="form-control" value="${cookie.credentials_pwd.value}" size="20">
	                </div>
		                <p class="login-remember"><label>
		                <input name="rememberMe" type="checkbox" id="rememberMe" value="forever"> Remember Me</label></p>
		                <div class="form-group">
		                <input type="submit" name="submit" id="submit" class="btn btn-brand btn-block mb-4" value="Sign In">
		                <input type="hidden" name="redirect_to" value="https://themes.getbootstrap.com/my-account/">
	            	</div>
	            	<input type="hidden" name="dest" value="${param.dest}"/>
            </form>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>

</html>