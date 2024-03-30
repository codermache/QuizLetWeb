

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="js/bootstrapp.min.js"></script>
        <link href="css/font-awesome.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>    
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp"/>
        <div class="main">
            <div class="container" style="align-self: center; min-height: 50vh">
                <c:set var="userMail" value="${ param.userMail }"/>
                <c:choose >
                    <c:when  test="${ userMail != null }">
                        <c:set var="currentTime" value="${System.currentTimeMillis() }"/>
                        <c:set var="createTime" value="${ param.createTime }"/>
                        <c:choose>
                            <c:when test = "${ ((currentTim - createTime) / 1000 / 60) < 3 }">
                                <form action="${contextPath}/ResetPasswordController" method="post">
                                    <div class="row">
                                        <div class="col-md-3"></div>
                                        <div class="col-md-6">
                                            <label class="label control-label">Enter your new pass</label>
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-user"></span></span>
                                                <input type="password" class="form-control" name="newPass" placeholder="">
                                            </div>
                                            <label class="label control-label">Re-Enter your new pass</label>
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-lock"></span></span>
                                                <input type="password" class="form-control" name="confirmNewPass">
                                            </div>
                                            <c:if test="${mess != null}">
                                                <label class="label control-label">
                                                    <p style="color: red; font-weight: bold;">
                                                        <c:out value="${mess}"/>
                                                    </p>
                                                </label>
                                            </c:if>
                                            <div class="input-group">
                                                <button type="submit" id="submit" class="btn btn-success">Change password!</button>
                                                <input type="hidden" name="service" value="resetPage">
                                                <input type="hidden" name="userMail" value="<c:out value="${userMail}"/>">  
                                            </div>

                                        </div>
                                        <div class="col-md-3"></div>
                                    </div>  
                                </form>
                            </c:when>
                            <c:otherwise>
                                <p>Your link have over due</p>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <form action="${contextPath}/ResetPasswordController" method="post">
                            <div class="row">
                                <div class="col-md-3"></div>
                                <div class="col-md-6">
                                    <label class="label control-label">Enter your email</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span></span>
                                        <input type="text" class="form-control" name="enteredUserMail" placeholder="">
                                    </div>
                                    <br>
                                    <c:if test="${mess != null}">
                                                <label class="label control-label">
                                                    <p style="color: red; font-weight: bold;">
                                                        <c:out value="${mess}"/>
                                                    </p>
                                                </label>
                                            </c:if>
                                    <div class="input-group">                                        
                                        <button type="submit" id="submit" class="btn btn-success">Send confirm</button>
                                        <input type="hidden" name="service" value="resetPassword">
                                    </div>
                                </div>
                                <div class="col-md-3"></div>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
