

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${subject.getSubjectName()}</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <style>


            /* Button used to open the contact form  */
            .open-button {
                background-color: #555;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                opacity: 0.8;
                position: center;

                right: 28px;
                width: 280px;
            }



            /* Add some hover effects to buttons */
            .form-container .btn:hover, .open-button:hover {
                opacity: 1;
            }
        </style>

    </head>
    <body>
        <jsp:include page="header.jsp"/>


        <div class="container-fluid" style="border-top: 1px black solid;">



            <div class="row">
                <div class="left col-3" style="margin-top:42px; border-right: 1px black solid;">
                    <div>
                        <form action="subjectController" method="">
                            <div class="filter" style="">      
                                <input type="text" name="search" class="form-control" style="border-radius: 5px" placeholder="Search Subject">
                                <div>
                                    <h5>Category</h5></div>
                                <div>
                                    <c:forEach items="${requestScope.subjectList}" var="category">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="category" value="${category.getPostCateId()}">
                                            <label class="form-check-label" for="flexCheckDefault">
                                                ${category.getPostCateName()}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                                <input class="btn btn-primary" type="submit" value="Search"/>
                                <input hidden name="service" value="blogList"/>
                            </div>
                        </form>
                    </div>

                    <div style="margin-top: 20px;">
                        <a href="marketingController?service=blogList"><h5>Subject you may like</h5></a>
                        <c:forEach items="${lastBlogs}" var="blog">
                            <div class="lastposts" style=" display: flex;margin-top: 20px; border: 1px #bccafd solid;" >
                                <div style="width: 104px;border-right:#bccafd 1px solid;">

                                </div>
                                <div>
                                    <a style="text-decoration: none;color: black;" href="subjectController?service=subjectDetail&subjectId=${subject.getSubjectId()}"><h6>${subject.getSubjectName()}</h6></a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="right col-9">
                    <c:set var="thisSubject" value="${subject}"/>

                    <div class="title row">
                        <h4>${subject.getSubjectName()}</h4>
                    </div>
                    <hr>
                    <div class="content row" style="text-align: justify;">

                        <p>${subject.getDescription()}</p>

                    </div>

                    <%--<div class="content row" style="text-align: justify;">
                        <c:forEach items = "${pricePackage}" var="pricePackage" begin = "0" end = "2">
                            <h3>${pricePackage.getAllPricePackagesBySubject()}</h3>
                            </c:forEach>


                    </div>--%>

                </div>
                <a onClick="userValidate()">click here</a>
                <input type="hidden" id="checkLogin" value="${sessionScope.currUser}">
                <button class="open-button" onclick="openForm()">Open Form</button>
            </div>

        </div>
    </div>

</body>
<jsp:include page="footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</html>
