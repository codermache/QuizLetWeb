


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${blog.getBlogTitle()}</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container-fluid" style="border-top: 1px black solid;">
            <%-- search box --%>
            <div class="row">
                <div class="left col-3" style="margin-top:42px; border-right: 1px black solid;">
                    <div>
                        <form action="marketingController" method="">
                            <div class="filter" style="">      
                                <input type="text" name="search" class="form-control" style="border-radius: 5px" placeholder="Search in BlogList">
                                <div>
                                    <h5>Category</h5></div>
                                <div>
                                    <c:forEach items="${requestScope.postCateList}" var="category">
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
                        <a href="marketingController?service=blogList"><h5>Latest Posts</h5></a>
                        <c:forEach items="${lastBlogs}" var="blog">
                            <div class="lastposts" style=" display: flex;margin-top: 20px; border: 1px #bccafd solid;" >
                                <div style="width: 104px;border-right:#bccafd 1px solid;">
                                    <img src="${blog.getThumbnail()}" style="height: 70px;width: auto; ">
                                </div>
                                <div>
                                    <a style="text-decoration: none;color: black;" href="marketingController?service=blogDetail&blogId=${blog.getBlogId()}"><h6>${blog.getBlogTitle()}</h6></a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <%-- mainContent --%>
                <div class="right col-9">
                    <c:set var="thisBlog" value="${blog}"/>
                    <div class="info row">
                        <div class="cate col-9">${blogCateName}</div>
                        <div class="date col-3">
                            <div style="float:right;">Last Edited: <fmt:formatDate type = "date" value = "${thisBlog.getLastEdited()}" />
                            </div>
                            <c:if test="${sessionScope.role.getUserRoleName().equalsIgnoreCase('admin')}">
                            <div style="float:right;">
                                <a href="${contextPath}/PostDetailController?service=editPost&blogId=${thisBlog.getBlogId()}" class="btn btn-primary">Blog detail</a>
                            </div>
                            </c:if>
                        </div>
                    </div>        
                    <div class="title row">
                        <h4>${thisBlog.getBlogTitle()}</h4>
                    </div>
                    <hr>
                    <div class="content row" style="text-align: justify;">
                        <p>${thisBlog.getDetail()}</p>
                    </div>
                    <div class="author row" style="float:right;">
                        <div>
                            <div style="float:right;">Author</div>                        
                        </div>
                        <div>
                            <div style="float:right;">
                                <h5>${thisBlog.getAuthor().getUserName()}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- end mainContent --%>

    </body>
    <jsp:include page="footer.jsp"/>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</html>
