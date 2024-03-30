

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
        <c:if test="${ sessionScope.role.getUserRoleName().equalsIgnoreCase('admin') || sessionScope.role.getUserRoleName().equalsIgnoreCase('Expert')}">
            <%-- Check If user registedSubject is avaiable not, if not redirect to load information --%>
            <c:if test="${subjectList == null}">
                <c:redirect url="/QuizListController?service=getQuizDetailInformation"/>
            </c:if>        
            <div class="main">
                <%-- Login form --%>
                <div class="container" style="align-self: center; min-height: 50vh">
                    <%-- Start form --%>
                    <form action="${contextPath}/QuizListController" onsubmit="return validateForm()" method="POST" >
                        <div class="row">
                            <%-- Bootstrap to center form --%>
                            <div class="col-md-3"></div>
                            <div class="col-md-6">
                                <h1>Quiz Detail</h1>
                                <label class="label control-label">Quiz Name</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <input class="form-control" type="text" name="quizName" required>
                                </div>

                                <label class="label control-label">Subject</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <select class="form-control" name="subject">
                                        <option value="0">Choose</option>
                                        <c:forEach items="${subjectList}" var="subject">
                                            <option value="${ subject.getSubjectId()}"><c:out value="${subject.getSubjectName()}" /></option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Duration (in minute)</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="number" name="duration" min="1" max="90" required>
                                    </div>
                                </div>

                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Exam Level</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <select class="form-control" name="examLevel">
                                            <option value="0">Choose</option>
                                            <c:forEach items="${quizLevelList}" var="quizLevel">
                                                <option value="${ quizLevel.getQuizLevelId()}"><c:out value="${quizLevel.getQuizLevelName()}" /></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Pass Rate (%)</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="number" name="passRate" min="1" max="100" required>
                                    </div>
                                </div>

                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Test Type</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <select class="form-control" name="testType">
                                            <option value="0">Choose</option>
                                            <c:forEach items="${testTypeList}" var="testType">
                                                <option value="${ testType.getTestTypeId()}"><c:out value="${testType.getTestTypeName()}" /></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Number of Question</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="number" name="numbetOfQuestion" min="1" max="50" required>
                                    </div>
                                </div>

                                <div style="width: 50%;float: left;padding-right: 10px;">
                                    <label class="label control-label">Question Dimension</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <select class="form-control" name="dimensionType">
                                            <option value="0">Choose</option>
                                            <c:forEach items="${dimensionTypeList}" var="dimensionType">
                                                <option value="${ dimensionType.getDimensionTypeId()}"><c:out value="${dimensionType.getDimensionTypeName()}" /></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div style="clear: both;">
                                    <label class="label control-label">Description</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" style="height: 100px;" type="textarea" name="description" >
                                    </div>
                                </div>
                                <%-- Display messages, if any --%>
                                <div>
                                    <c:if test="${message != null}" >
                                        <h5 style="color:red"> <c:out value="${message}"/> </h5>
                                    </c:if>
                                </div>
                                <br>
                                <%-- Submit form --%>
                                <div class="input-group" >
                                    <button style="margin-left: auto; margin-right: auto; " type="submit" id="submit" class="btn btn-success">Submit</button>
                                    <input type="hidden" name="service" value="createQuiz">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

            </c:if>
            <c:if test="${ !sessionScope.role.getUserRoleName().equalsIgnoreCase('admin') && !sessionScope.role.getUserRoleName().equalsIgnoreCase('Expert')}">
                <h2 style="text-align: center;">You don't have the right to access this page</h2>
            </c:if>
            <%-- Include footer page --%>
            <jsp:include page="/jsp/footer.jsp"/>
    </body>
    
</html>
<head>
  <script>
    function validateForm() {
      var x = document.forms["myForm"]["numbetOfQuestion"].value;
      if (x < 1 || x > 10) {
        alert("Vui lòng nhập số >0");
        return false;
      }
    }
  </script>
</head>
