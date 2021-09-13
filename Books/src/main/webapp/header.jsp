 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
        <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="#"
            style="padding-top: .75rem; padding-bottom: .75rem;">Company name</a>
        <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse"
            data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false"
            aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search"
            style="">
        <div class="navbar-nav">
        <c:choose>
        <c:when test="${sessionScope.authorized_user.decentral_id  == null}" >
            <div class="nav-item text-nowrap">
                <a class="nav-link px-3" href="${initParam.hostURL}${pageContext.request.contextPath}/login.jsp">Login</a>
            </div>
        </c:when>
        <c:when test="${sessionScope.authorized_user.decentral_id  != null}" >
            <div class="nav-item text-nowrap">
                <a class="nav-link px-3" href="${initParam.hostURL}${pageContext.request.contextPath}/invalidatesessionandremovecookies.do">Sign out</a>
            </div>
        </c:when>
    </c:choose>
        </div>
    </header>