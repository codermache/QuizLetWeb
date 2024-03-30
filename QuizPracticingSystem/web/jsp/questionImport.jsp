

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script class="jsbin" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <title>Question Import</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="${contextPath}/css/questionImport.css"/>
    </head>
    <body>
        <%-- Include header page --%>
        <jsp:include page="header.jsp"/>
        <div class="wrap" style="min-height: 55vh; padding-top: 5vh;">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8" id="content">
                    <c:choose>
                        <c:when test="${empty subjectList}">
                            <c:redirect url="importQuestion?service=loadSubjectList"/>
                        </c:when>
                        <c:otherwise>
                            <%--Template Downloader|File uploader--%>
                            <div class="row file-upload" >
                                <div class="col-md-6" id="uploadBanner">
                                    <p style='text-align: center; font-weight: bold'>
                                        <span style='color:red; font-weight: bold'>! Before you start uploading your own file ! </span><br>
                                        If this is your first time then please download our template here 
                                        first!
                                        <br>Don't want any weird errors now do we?
                                        <br>
                                        <br><span style='font-style: italic; font-weight: normal'>Disclaimer! 
                                            This function still has a lot of restrictions.
                                            You still have to select the lesson and dimension
                                            later. Sorry for the inconvenience!</span>
                                    </p>
                                    <div class='col text-center'>
                                        <a class="btn btn-success" style="margin: auto" href ="${contextPath}/questionTemplate/questionTemplate.xml" download>Give me the template!</a>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <form action="${contextPath}/importQuestion" method="POST">
                                        <div class="text-center">
                                            <div class="image-upload-wrap">
                                               <input class="file-upload-input" type='file' onchange="openFile(event);" accept=".xml" />
                                            <div class="drag-text">
                                              <h4>Drag and drop or select a file</h4>
                                            </div>  
                                            </div>
                                            <p class="fileName" style="margin-bottom:0;">&nbsp;
                                            </p>
                                            <label>Subject : </label>
                                            <select name="subjectId" style="width: 20vw; margin-top: 2vh;">
                                                <c:forEach items="${subjectList}" var="subject">
                                                    <c:choose>
                                                        <c:when test="${(!empty subjectImport) && (subjectImport.getSubjectId() == subject.getSubjectId())}">
                                                            <option value="${subject.getSubjectId()}" name="subjectId" selected><c:out value="${subject.getSubjectName()}"/></option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${subject.getSubjectId()}" name="subjectId"><c:out value="${subject.getSubjectName()}"/></option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select><br>
                                            <input id="questionContent" type="hidden" name="questionContent" value=""/>
                                            <input type="hidden" name="service" value="uploadQuestion"/>
                                            <input id="submit" class="btn btn-success" type="submit" value="Import">
                                        </div>
                                    </form>
                                </div>
                            </div><br>
                            <c:if test="${!empty questionImportMessage}">
                                <div class="question-form">
                                    <c:out value="${questionImportMessage}"/>
                                </div>
                            </c:if>
                            <%--If the importedQuestion list is not null, display it--%>
                            <c:if test="${!empty importedQuestions}">
                                <h5>After some digging, here are the question we managed to extract from your beautiful file: </h5>
                                <form action="${contextPath}/importQuestion" method="POST">
                                    <c:forEach items = "${importedQuestions}" var="question">
                                        <%--Question forms--%>
                                        <div class="row question-form">
                                            <div class="col-md-8 question-content">
                                                <label style="margin-top: 2px;"><span style="color: red">*</span><c:out value="${question.getQuestionId()}"/>.</label>
                                                    <textarea class="input-question" name="questionContent" 
                                                              rows="2" style="vertical-align: top" required><c:out value="${question.getContent()}"/></textarea>
                                                <label style="font-style: italic">Explanation: </label>
                                                    <input style="width:85%; border:none; background-color:#FCFFF2" type="text" name="questionExplanation" value="${question.getExplanation()}"><br>
                                                    <label style="font-weight: bold"><span style="color: red">*</span>A.</label>
                                                    <input class="input-question" type="text" name="questionAnswerRight" value="${question.getAnswers().get(0).getAnswerContent()}" required><br>
                                                <label><span style="color: red">*</span>B.</label>
                                                    <input class="input-question" type="text" name="questionAnswerWrong1" value="${question.getAnswers().get(1).getAnswerContent()}" required><br>
                                                <label>C.</label>
                                                    <input class="input-question" type="text" name="questionAnswerWrong2" value="${question.getAnswers().get(2).getAnswerContent()}"><br>
                                                <label>D.</label>
                                                    <input class="input-question" type="text" name="questionAnswerWrong3" value="${question.getAnswers().get(3).getAnswerContent()}"><br>
                                            </div>
                                            <div class="col-md-4 question-select">
                                                <label>Select Lesson</label><br>
                                                <select name="lesson">
                                                    <c:forEach items="${lessonList}" var="lesson">
                                                        <option value="${lesson.getLessonId()}"><c:out value="${lesson.getLessonName()}"/></option>
                                                    </c:forEach>
                                                </select>
                                                <br>
                                                <label>Select Dimension</label><br>
                                                <select name="dimension">
                                                    <c:forEach items="${subjectImport.getDimensions()}" var="dimension">
                                                        <option value="${dimension.getDimensionId()}"><c:out value="${dimension.getDimensionName()}"/></option>
                                                    </c:forEach>
                                                </select>
                                                <br>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="text-center">
                                        <input type="hidden" name="subjectId" value="${subjectImport.getSubjectId()}">
                                        <input type="hidden" name="service" value="importQuestions">
                                        <input type="submit" class="btn btn-success" value="Add it to the subject!">
                                    </div>
                                </form>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-2"></div>
            </div>
        </div>
        <%-- Include footer page --%>
        <jsp:include page="footer.jsp"/>
    </body>
    <script>
        var openFile = function (event) {
            var input = event.target;
            const endPage = document.getElementById("endPage");

            $('.fileName').html('You just uploaded : ' + input.files[0].name);
            var reader = new FileReader();
            reader.onload = function () {
                var text = reader.result;
                //var lines = reader.result.split('\n');
                var content = document.getElementById('questionContent');
                content.value = text;
                document.getElementById("submit").disabled = false;
//                for (var line =0; line<lines.length; line++){
//                    var paragraph = document.createElement("p");
//                    paragraph.appendChild(document.createTextNode(lines[line]));
//                    parent.insertBefore(paragraph, parent);
//                    console.log(lines[line]);
//                }

                console.log(reader.result.substring(0, 200));
            };
            reader.readAsText(input.files[0]);
        };
        
        window.onload = function () {
            document.getElementById("submit").disabled = true;
        };
    </script>
</html>
