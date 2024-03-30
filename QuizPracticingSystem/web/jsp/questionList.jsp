
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" ></script>
        <link rel="stylesheet" href="${contextPath}/css/questionList.css">
        <script src="${contextPath}/js/questionList.js"></script>
    </head>
    <body>
        <div class="wrap">
            <%-- Include header page --%>
            <jsp:include page="header.jsp"/>

            <%-- Check If user is logged in or not, if not redirect to error page --%>
            <c:if test="${(sessionScope.currUser == null)||(!sessionScope.role.getUserRoleName().equalsIgnoreCase('Admin')
                          &&!sessionScope.role.getUserRoleName().equalsIgnoreCase('Expert'))}">
                <c:set var = "errorMess" scope="session" value = "User not logged in"/>
                <c:redirect url="/error.jsp"/>
            </c:if>

            <%-- Check If listFilterSubject,listFilterDimension,listFilterLesson is avaiable not, if not redirect to load information --%>
            <c:if test="${listFilterSubject==null || listFilterDimension==null || listFilterLesson==null}">
                <c:redirect url="/questionController?service=getFilterInformation"/>
            </c:if>
            <div class="row" style="margin-top: 3rem">
                <div class="col-md-1"></div>
                <div class="col-md-2" id="form" style="height: 480px">
                    <h2 class="text-center">Filter</h2>
                    <div style="margin-bottom: 20px;">
                        <%-- Start search form --%>
                        <form action = "${contextPath}/questionController" class="navbar-form">
                            <div class="input-group">
                                <input  class="form-control" type="text" id="content" placeholder="Content... " name="content"  style="display: inline-block">
                                <span class="input-group-btn">
                                    <button type="submit" class="btn btn-primary"><span class="fas fa-search"></span></button>  
                                </span>
                                <input type="hidden" name="service" value="searchQuestionByContent">
                            </div>
                        </form>                     
                    </div>
                    <%-- Start filter form --%>
                    <form action="${contextPath}/questionController" method="POST">
                        <div class="form-group">
                            <h5>Filter by Subject</h5>
                            <%-- Choose Subject Filter --%>
                            <select class="form-control" name="subjectId">                                
                                <option value="0" selected="">Choose...</option>
                                <c:forEach items="${listFilterSubject}" var="subject">
                                    <option value="${subject.getSubjectId()}" ><c:out value="${subject.getSubjectName()}" /></option>                          
                                </c:forEach>                          
                            </select>
                            <h5>Filter by Dimension</h5>
                            <%-- Choose Dimension Filter --%>
                            <select class="form-control" name="dimensionId">
                                <option value="0" selected="">Choose...</option>
                                <c:forEach items="${listFilterDimension}" var="dimension">
                                    <option value="${dimension.getDimensionId()}" onclick=""><c:out value="${dimension.getDimensionName()}" /></option>                          
                                </c:forEach>                          
                            </select>
                            <h5>Filter by Lesson</h5>
                            <%-- Choose Lesson Filter --%>
                            <select class="form-control" name="lessonId">
                                <option value="0" selected="">Choose...</option>
                                <c:forEach items="${listFilterLesson}" var="lesson">
                                    <option value="${lesson.getLessonId()}" onclick=""><c:out value="${lesson.getLessonName()}" /></option>                          
                                </c:forEach>                          
                            </select>
                        </div>
                        <div class="input-group">
                            <button type="submit" id="submit" class="btn btn-success" style="width: 100%">Filter</button>
                            <input type="hidden" name="service" value="filterQuestion">
                        </div>
                    </form>

                </div>

                <div class="col-md-8" id="form" style="min-height: 480px; min-width: 1000px">
                    <div class="container" >
                        <%-- Table Container --%>
                        <div class="form-group">                           
                            <%-- Select number of Rows show on table --%>
                            <select class="form-control" name="state" id="maxRows" style="width: 150px;float:left;">                               
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="20">20</option>
                            </select>
                            <a href="${contextPath}/jsp/questionDetail.jsp"><button class="btn btn-success" style="float:right;margin: 5px">Add new Question</button></a>
                        </div>  

                        <%-- Table of QuestionList--%>
                        <table id="table-id" class="table table-bordered table-striped">
                            <thead>
                                <%-- Headers of Table--%>
                                <tr style="background-color: #F0D8D5;">
                                    <th>QuesId</th>
                                    <th>Content</th>
                                    <th>Subject</th>
                                    <th>Lesson</th>
                                    <th>Dimension</th>
                                    <th>Status</th>
                                    <th>Action</th></tr>
                            </thead> 
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty listQuestionManage}">
                                        <tr style="color: red"><td colspan="8">No Question Available</td></tr>
                                    </c:when>                               
                                    <%-- Check if listQuestionManage not null then display listQuestionManage --%>
                                    <c:otherwise>
                                        <c:forEach items="${listQuestionManage}" var="questionList">
                                            <tr>
                                                <td><c:out value="${questionList.getQuestionId()}"/></td>
                                                <td><c:out value="${questionList.getContent()}"/></td>
                                                <td><c:out value="${questionList.getSubjectName()}"/></td>
                                                <td><c:out value="${questionList.getLessonName()}"/></td>
                                                <td><c:out value="${questionList.getDimensionName()}"/></td>
                                                <%-- Check if questionList status is available or not--%>
                                                <td><c:if test="${questionList.isStatus()}">
                                                        Available
                                                    </c:if>
                                                    <c:if test="${!questionList.isStatus()}">
                                                        Not Available
                                                    </c:if>
                                                </td>
                                                <td><a href="questionController?service=editQuestion&type=update&questionId=${questionList.getQuestionId()}"><div class="btn btn-success">Edit</div></a></td>
                                            </tr>
                                        </c:forEach> 
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${message != null}">
                                    <tr style="color: red"><td colspan="8"><c:out value="${message}" /></td></tr>
                                    </c:if>
                            </tbody>
                        </table>
                        <%--Start Pagination --%>
                        <div class='pagination-container row'>
                            
                            <nav>
                                <ul class="pagination" style="justify-content: center">
                                    <li data-page="prev" >
                                        <span> <button class="btn btn-light" style="border: solid 1px">Prev</button></span>
                                    </li>
                                    <%--Here the JS Function Will Add the Rows --%>
                                    <li data-page="next" id="prev">
                                        <span> <button class="btn btn-light" style="border: solid 1px">Next</button><span class=""></span></span>
                                    </li>
                                </ul>
                            </nav>
                            
                        </div>

                    </div>
                </div>
                <div class="col-md-1"></div>
            </div>
            <div class="space" style="min-height: 50vh;"></div>
            <%-- Include footer page --%>
            <jsp:include page="footer.jsp"/>
        </div>
        <script>
            getPagination('#table-id');
            function getPagination(table) {
                var lastPage = 1;

                $('#maxRows')
                        .on('change', function (evt) {
                            lastPage = 1;
                            $('.pagination')
                                    .find('li')
                                    .slice(1, -1)
                                    .remove();
                            var trnum = 0; // reset tr counter
                            var maxRows = parseInt($(this).val()); // get Max Rows from select option

                            if (maxRows == 5000) {
                                $('.pagination').hide();
                            } else {
                                $('.pagination').show();
                            }

                            var totalRows = $(table + ' tbody tr').length; // numbers of rows
                            $(table + ' tr:gt(0)').each(function () {
                                // each TR in  table and not the header
                                trnum++; // Start Counter
                                if (trnum > maxRows) {
                                    // if tr number gt maxRows

                                    $(this).hide(); // fade it out
                                }
                                if (trnum <= maxRows) {
                                    $(this).show();
                                } // else fade in Important in case if it ..
                            }); //  was fade out to fade it in
                            if (totalRows > maxRows) {
                                // if tr total rows gt max rows option
                                var pagenum = Math.ceil(totalRows / maxRows); // ceil total(rows/maxrows) to get ..
                                //	numbers of pages
                                for (var i = 1; i <= pagenum; ) {
                                    // for each page append pagination li
                                    $('.pagination #prev')
                                            .before(
                                                    '<li class="btn btn-light "style="" data-page="' +
                                                    i +
                                                    '">\
                                                                  <div>' +
                                                    i++ +
                                                    '<div></div></div>\
                                                                </li>'
                                                    )
                                            .show();
                                } // end for i
                            } // end if row count > max rows
                            $('.pagination [data-page="1"]').addClass('active'); // add active class to the first li
                            $('.pagination li').on('click', function (evt) {
                                // on click each page
                                evt.stopImmediatePropagation();
                                evt.preventDefault();
                                var pageNum = $(this).attr('data-page'); // get it's number

                                var maxRows = parseInt($('#maxRows').val()); // get Max Rows from select option

                                if (pageNum == 'prev') {
                                    if (lastPage == 1) {
                                        return;
                                    }
                                    pageNum = --lastPage;
                                }
                                if (pageNum == 'next') {
                                    if (lastPage == $('.pagination li').length - 2) {
                                        return;
                                    }
                                    pageNum = ++lastPage;
                                }

                                lastPage = pageNum;
                                var trIndex = 0; // reset tr counter
                                $('.pagination li').removeClass('active'); // remove active class from all li
                                $('.pagination [data-page="' + lastPage + '"]').addClass('active'); // add active class to the clicked
                                // $(this).addClass('active');					// add active class to the clicked
                                limitPagging();
                                $(table + ' tr:gt(0)').each(function () {
                                    // each tr in table not the header
                                    trIndex++; // tr index counter
                                    // if tr index gt maxRows*pageNum or lt maxRows*pageNum-maxRows fade if out
                                    if (
                                            trIndex > maxRows * pageNum ||
                                            trIndex <= maxRows * pageNum - maxRows
                                            ) {
                                        $(this).hide();
                                    } else {
                                        $(this).show();
                                    } //else fade in
                                }); // end of for each tr in table
                            }); // end of on click pagination list
                            limitPagging();
                        })
                        .val(5)
                        .change();
            }

            function limitPagging() {
                // alert($('.pagination li').length)

                if ($('.pagination li').length > 7) {
                    if ($('.pagination li.active').attr('data-page') <= 3) {
                        $('.pagination li:gt(5)').hide();
                        $('.pagination li:lt(5)').show();
                        $('.pagination [data-page="next"]').show();
                    }
                    if ($('.pagination li.active').attr('data-page') > 3) {
                        $('.pagination li:gt(0)').hide();
                        $('.pagination [data-page="next"]').show();
                        for (let i = (parseInt($('.pagination li.active').attr('data-page')) - 2); i <= (parseInt($('.pagination li.active').attr('data-page')) + 2); i++) {
                            $('.pagination [data-page="' + i + '"]').show();

                        }

                    }
                }
            }

        </script>
    </body>
</html>
