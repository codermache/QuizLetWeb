
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lesson Details Page</title>
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
        <c:if test="${ sessionScope.role.getUserRoleName().equalsIgnoreCase('admin') || sessionScope.role.getUserRoleName().equalsIgnoreCase('Expert')}">
            <%-- Check If user registedSubject is avaiable not, if not redirect to load information --%>
            <c:if test="${updateLesson == null}">
                <c:redirect url="jsp/courseContentDetail.jsp"/>
            </c:if>        
            <div class="main">
                <div class="container" style="align-self: center; min-height: 50vh">
                    <%-- Start form --%>
                    <form action="${contextPath}/lessonController" method="POST">
                        <div class="row">
                            <%-- Bootstrap to center form --%>
                            <div class="col-md-3"></div>
                            <div class="col-md-6">
                                <%-- Display messages, if any --%>
                                <div>
                                    <c:if test="${message != null}" >
                                        <h5 style="color:red"> <c:out value="${message}"/> </h5>
                                    </c:if>
                                </div>
                                <br>
                                <h1>Lesson Details</h1>
                                <label class="label control-label">Name</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <input class="form-control" type="text" name="lessonName" required value="${updateLesson.getLessonName()}">
                                </div>

                                <label class="label control-label">Type</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <select class="form-control" name="lessonTypeId">
                                        <option value="0" >Choose</option>
                                        <c:forEach items="${listLessonType}" var="lessonType">
                                            <option  value="${lessonType.getLessonTypeId()}" <c:if test="${updateLesson.getLessonTypeId() == lessonType.getLessonTypeId()}">selected</c:if>>
                                                <c:out value="${lessonType.getLessonTypeName()}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Order</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="number" name="lessonOrder" min="1" max="50" required value="${updateLesson.getLessonOrder()}">
                                    </div>
                                </div>

                                <div style="clear: both;">
                                    <label class="label control-label">videoLink</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="textarea" name="videoLink" value="${updateLesson.getVideoLink()}">
                                    </div>
                                </div>

                                <div style="clear: both;">
                                    <label class="label control-label">Content</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" style="height: 100px;" type="textarea" name="content" value="${updateLesson.getContent()}">
                                    </div>
                                </div>

                                <div>
                                    <label class="label control-label">Status</label>
                                    <select id="inputState" class="form-control" style="border: 1px solid #ced4da" name="lessonStatus">
                                        <c:choose>
                                            <c:when test="${updateLesson.isStatus()}">
                                                <option selected value="1">Active</option>
                                                <option value="0">Inactive</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option  value="1">Active</option>
                                                <option selected value="0">Inactive</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                                <%-- Submit form --%>
                                <div class="input-group" >
                                    <input type="hidden" name="subjectId" value="${subjectId}">
                                    <input type="hidden" name="updateLessonId" value="${updateLesson.getLessonId()}">
                                    <input type="hidden" name="service" value="updateLessonInformation">
                                    <button style="margin-left: auto; margin-right: auto; " type="submit" id="submit" class="btn btn-success">Submit</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:if>
        <c:if test="${ !sessionScope.role.getUserRoleName().equalsIgnoreCase('admin') && !sessionScope.role.getUserRoleName().equalsIgnoreCase('Expert')}">
            <h2 style="text-align: center;">You don't have the right to access this page</h2>
        </c:if>
        <%-- Include footer page --%>
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
