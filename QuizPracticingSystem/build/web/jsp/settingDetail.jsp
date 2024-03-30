
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Setting List Page</title>
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
        <c:if test="${ sessionScope.role.getUserRoleName().equalsIgnoreCase('admin')}">
            <div class="main">
                <%-- Login form --%>
                <div class="container" style="align-self: center; min-height: 50vh">
                    <%-- Start form --%>
                    <div class="row">
                        <%-- Bootstrap to center form --%>
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <h1>Setting Detail</h1>
                            <div>
                                <c:if test="${message != null}" >
                                    <h5 style="color:red"> <c:out value="${message}"/> </h5>
                                </c:if>
                            </div>
                            <form action="${contextPath}/SystemSettingController" method="POST">
                                <c:choose>
                                    <c:when test="${objectId != null}">
                                        <input type="text" name="service" value="editSetting" hidden="" readonly="">

                                        <label class="label control-label">Setting Id</label>
                                        <div class="form-group">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-user"></span>
                                            </span>
                                            <input class="form-control" type="text" name="objectId" value="${objectId}" readonly="">
                                        </div>

                                        <label class="label control-label">Setting Type</label>
                                        <div class="form-group">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-user"></span>
                                            </span>
                                            <input type="text" name="settingType" value="${field}" hidden="" readonly="">
                                            <input class="form-control" type="text" name="fieldName" value="${fieldName}" readonly="">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" name="service" value="addSetting" hidden="" readonly="">

                                        <label class="label control-label">Setting Type</label>
                                        <div class="form-group">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-user"></span>
                                            </span>
                                            <select class="form-control" name="settingType" required="">
                                                <option value="userRole">User Role</option>
                                                <option value="postCate">Post Category</option>
                                                <option value="subjectCate">Subject Category</option>
                                                <option value="testType">Test Type</option>
                                                <option value="quizLevel">Quiz Level</option>
                                                <option value="lessonType">Lesson Type</option>
                                                <option value="dimensionType">Dimension Type</option>
                                            </select>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <label class="label control-label">Setting Name</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <input class="form-control" type="text" name="settingName" value="${objectName}" required="">
                                </div>

                                <label class="label control-label">Setting Status</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <select class="form-control" name="settingStatus" required="">
                                        <option value="true" <c:if test="${objectStatus}">selected </c:if>>Active</option>
                                        <option value="false" <c:if test="${!objectStatus}">selected </c:if>>Inactive</option>
                                        </select>
                                    </div>

                                    <div class="input-group" >
                                        <button style="margin-left: auto; margin-right: auto; " type="submit" id="submit" class="btn btn-success">Submit</button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-3"></div>
                        </div>
                    </div>
                </div>
        </c:if>
        <c:if test="${ !sessionScope.role.getUserRoleName().equalsIgnoreCase('admin')}">
            <h2 style="text-align: center;">You don't have the right to access this page</h2>
        </c:if>
        <%-- Include footer page --%>
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>