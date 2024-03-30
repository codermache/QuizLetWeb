

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>

    <body>
        <%-- Check If user is logged in or not, if not redirect to index --%>
        <c:if test="${sessionScope.currUser == null}">
            <c:redirect url="/index.jsp"/>
        </c:if>
        <%-- Include header page --%>
        <jsp:include page="/jsp/header.jsp"/>
        <div class="main">

            <%-- Login form --%>
            <div class="container" style="align-self: center; min-height: 50vh; margin-top: 3vh;">
                    <div class="row">
                        <%-- Bootstrap to center form --%>
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <%-- Start form --%>
                            <form action="${contextPath}/changePassword" method="POST" name="changePassword" id="changePassword">
                                <%--Input old password--%>
                                <div class="row" style="margin-top: 3vh;">
                                    <div class="col-md-4" style="text-align: right"><label class="label control-label">Old Password</label></div>
                                    <div class="col-md-8">
                                        <input type="password" class="form-control" name="oldPassword" placeholder="Enter old password" required>
                                    </div>
                                </div>
                                <%--Input new password--%>
                                <div class="row" style="margin-top: 3vh;">
                                    <div class="col-md-4" style="text-align: right"><label class="label control-label">New Password</label></div>
                                    <div class="col-md-8">
                                        <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="Enter new password" onkeyup='check();' required>
                                    </div>
                                </div>
                                <%--Input confirm new password--%>
                                <div class="row" style="margin-top: 3vh;">
                                    <div class="col-md-4" style="text-align: right"><label class="label control-label">Confirm Password</label></div>
                                    <div class="col-md-8">
                                        <input type="password" class="form-control" name="rePassword" id="rePassword" placeholder="Confirm new password" onkeyup='check();' required>
                                    </div>
                                </div>
                                <div id="message" style="text-align: center; margin-top: 2vh"></div>
                                <br>
                                <%-- Submit form --%>
                                <div class="row">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-4">
                                        <button type="submit" id="submit" class="btn btn-success">Change password!</button>
                                    </div>
                                    <div class="col-md-4"></div>
                                </div>
                            
                            </form>
                            <%-- Display messages, if any --%>
                            <div style="text-align: center; margin-top: 2vh">
                                <h6 style="color:${color}"><c:out value="${message}"/></h6>
                            </div>
                        </div>
                        <div class="col-md-3"></div>
                    </div>
            </div>

        </div>
        <%-- Include footer page --%>
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>

<script>
    <%-- Check if new password and re-enter password match or not --%>
    var check = function () {
        if (document.getElementById('newPassword').value == document.getElementById('rePassword').value) {
            document.getElementById('message').style.color = 'green';
            document.getElementById('message').innerHTML = 'Valid';
            document.getElementById('submit').disabled = false;
        } else {
            document.getElementById('message').style.color = 'red';
            document.getElementById('message').innerHTML = 'New password dont match';
            document.getElementById('submit').disabled = true;
        }
    }
</script>
