
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Details</title
        <%-- CSS,JS--%>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" ></script>
        <link rel="stylesheet" href="${contextPath}/css/questionDetails.css">
    </head>
    <body>
        <%-- Include header page --%>
        <jsp:include page="header.jsp"/>
        <c:if test="${ sessionScope.role.getUserRoleName().equalsIgnoreCase('admin') || sessionScope.role.getUserRoleName().equalsIgnoreCase('Expert')}">
            <%-- Check If user is avaiable not, if not redirect to load information --%>
            <c:if test="${updateQuestion == null}">
                <c:redirect url="jsp/questionList.jsp"/>
            </c:if>  

            <div class="main">
                <div class="container" style="align-self: center; min-height: 50vh">
                    <h1 style="text-align: center;">Question Detail</h1>
                    <%-- Start form --%>
                    <form action="${contextPath}/quizController" method="POST">
                        <div class="row">
                            <div class="col-md-6">
                                <h2 style="text-align: center;">Question</h2>
                                <label class="label control-label">Subject</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <select class="form-control" name="subject">

                                        <c:forEach items="${listSubject}" var="subject">
                                            <option value="${subject.getSubjectId()}"<c:if test="${updateQuestions.getSubjectId() == subject.getSubjectId()}">selected</c:if>>

                                                <c:out value="${subject.getSubjectName()}" /></option>
                                            </c:forEach>
                                    </select>
                                </div>

                                <div >
                                    <label class="label control-label">Dimension</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <select class="form-control" name="dimension">

                                            <c:forEach items="${listDimension}" var="dimension">
                                                <option value="${dimension.getDimensionId()}"<c:if test="${updateQuestions.getDimensionId() == dimension.getDimensionId()}">selected</c:if>>
                                                    <c:out value="${dimension.getDimensionName()}" /></option>
                                                </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div >
                                    <label class="label control-label">Lesson</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <select class="form-control" name="lesson">

                                            <c:forEach items="${listLesson}" var="lesson">
                                                <option value="${lesson.getLessonId()}"<c:if test="${updateQuestions.getLessonId() == lesson.getLessonId()}">selected</c:if>>   
                                                    <c:out value="${lesson.getLessonName()}" /></option>
                                                </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div>
                                    <label class="label control-label">Status</label>
                                    <select id="inputState" class="form-control" style="border: 1px solid #ced4da" name="questionStatus">
                                        <c:choose>
                                            <c:when test="${updateQuestion.isStatus()}">
                                                <option selected value="1">Available</option>
                                                <option value="0">Disabled</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option  value="1">Available</option>
                                                <option selected value="0">Disabled</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>

                                <div>
                                    <label class="label control-label">Question Content</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="textarea" name="content" value="${updateQuestion.getContent()}">
                                    </div>
                                </div>

                                <div>
                                    <label class="label control-label">Media</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="textarea" name="media" value="${updateQuestion.getMedia()}">
                                    </div>
                                </div>
                                <div>
                                    <label class="label control-label">Explanation</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="textarea" name="explanation" value="${updateQuestion.getExplanation()}">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-1"></div>
                            <div class="form-group col-md-5">
                                <h2 style="text-align: center">Answer</h2>
                                <div>
                                    <label class="label control-label">True Answer</label><small style="color:red">(required)</small>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input type="hidden" name="trueAnswerId" value="${trueAnswer.getAnswerId()}">
                                        <input class="form-control" type="textarea" name="trueAnswer" value="${trueAnswer.getAnswerContent()}">
                                    </div>
                                </div>
                                <div>
                                    <label class="label control-label">Wrong Answer</label><small style="color:red">(required)</small>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input type="hidden" name="wrongAnswer1Id" value="${wrongAnswer.get(0).getAnswerId()}">
                                        <input class="form-control" type="textarea" name="wrongAnswer1" value="${wrongAnswer.get(0).getAnswerContent()}">
                                    </div>
                                </div>
                                <div>
                                    <label class="label control-label">Wrong Answer</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input type="hidden" name="wrongAnswer2Id" value="${wrongAnswer.get(1).getAnswerId()}">
                                        <input class="form-control" type="textarea" name="wrongAnswer2" <c:if test="${wrongAnswer.size() >= 2}"> value="${wrongAnswer.get(1).getAnswerContent()}"</c:if>>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="label control-label">Wrong Answer</label>
                                        <div class="form-group">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-user"></span>
                                            </span>
                                            <input type="hidden" name="wrongAnswer3Id" value="${wrongAnswer.get(2).getAnswerId()}">
                                        <input class="form-control" type="textarea" name="wrongAnswer3" <c:if test="${wrongAnswer.size() == 3}"> value="${wrongAnswer.get(2).getAnswerContent()}"</c:if>>
                                        </div>
                                    </div>
                                </div>

                            <%-- Display messages, if any --%>
                            <div>
                                <c:if test="${!empty message}">
                                    <h6 style="color: green"><c:out value="${message}"/></h6>
                                </c:if>
                            </div>
                            <br>
                            <%-- Submit form --%>
                            <div class="input-group" >
                                <button style="margin-left: auto; margin-right: auto; " type="submit" id="submit" class="btn btn-success">Submit</button>
                                <input type="hidden" name="updateQuestionId" value="${updateQuestion.getQuestionId()}">
                                <input type="hidden" name="service" value="updateQuestionInformation">
                            </div> 
                        </div>
                    </form>
                </c:if>
            </div>
        </div>


        <%-- Include footer page --%>
        <jsp:include page="footer.jsp"/>
    </body>
</html>

