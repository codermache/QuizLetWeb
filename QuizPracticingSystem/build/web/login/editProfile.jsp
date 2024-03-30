
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${contextPath}/css/stylelogin.css" media="screen">

        <title>JSP Page</title>
        <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i">

    </head>
    <body class="u-body">
        <c:if test="${currUser == null}">
            <c:redirect url="/login/login.jsp"></c:redirect>
        </c:if>
        <header class="u-align-left u-clearfix u-header u-header" id="sec-ad7d"></header>
        <div class="u-clearfix u-sheet u-sheet-1">
            <img class="u-image u-image-default u-preserve-proportions u-image-1" src="${contextPath}/images/login/logologin2.png" alt="" data-image-width="210" data-image-height="92">
            <div class="u-form u-form-1">
                <form action="${contextPath}/UserProfileController?service=editProfile" method="POST" class="u-clearfix u-form-spacing-15 u-form-vertical u-inner-form" style="padding: 15px;" source="custom" name="form">
                    <c:if test="${mess != null}">
                        <div class="u-form-group u-form-name">
                            <p style="color: red; font-weight: bold;">
                                <c:out value="${mess}"/>
                            </p>
                        </div>
                    </c:if>
                    <div class="u-form-group u-form-name">
                        <p>Enter your username:</p>
                        <input type="text" value="${currUser.getUserName()}"  name="userName" class="u-border-1 u-border-grey-30 u-input u-input-rectangle" required="">
                    </div>
                    <div class="u-form-group u-form-name">
                        <p>Enter your phone:</p>
                        <input type="text" value="${currUser.getUserMobile()}"  name="userMobile" class="u-border-1 u-border-grey-30 u-input u-input-rectangle" required="">
                    </div>
                    <div class="u-form-group u-form-name">
                        <c:if test="${currUser.isGender()}">
                            <p>Gender:</p>
                            <input type="radio" name="gender" value="Male" checked> Male
                            <br>
                            <input type="radio" name="gender" value="Female"> Female
                        </c:if>
                        <c:if test="${!currUser.isGender()}">
                            <p>Gender:</p>
                            <input type="radio" name="gender" value="Male" > Male
                            <br>
                            <input type="radio" name="gender" value="Female" checked> Female
                        </c:if>                        
                    </div>
                    <div class="u-align-center u-form-group u-form-submit">
                        <button class="u-btn u-btn-submit u-button-style" type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
