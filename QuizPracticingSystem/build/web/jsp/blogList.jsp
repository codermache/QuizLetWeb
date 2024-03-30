

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blog List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <%--Start page style--%>
        <style>
            .body{
                font-family: Source Serif Pro;
            }
            .card-body{
                height: 300px;
            }
            .card-body .btn{
                position:absolute;
                bottom:20px;
                left: 85px;
            }
        </style>
        <%--End page style--%>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <%-- search box --%>
                <div class="left col-3" style="margin-top:42px; border-right: 1px solid black;">
                    <div>
                        <form action="${contextPath}/blog" method="GET">
                            <div class="filter" style=""> 
                                <h6 style="color:red;">${errorMess}</h6>
                                <input type="text" name="search" class="form-control" style="border-radius: 5px" placeholder="Search in BlogList" value="${search}">
                                <div>
                                    <h5>Category</h5>
                                </div>
                                <div>
                                    <c:forEach items="${requestScope.postCateList}" var="category">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="category" value="${category.getPostCateId()}" 
                                                   <c:forEach items="${cateList}" var="cate">
                                                       <c:if test="${category.getPostCateId()==cate}">
                                                           checked
                                                       </c:if>
                                                   </c:forEach>
                                                   >
                                            <label class="form-check-label" for="flexCheckDefault">
                                                ${category.getPostCateName()}
                                            </label>
                                        </div>
                                    </c:forEach>    
                                </div>
                                <input type="submit" class="btn btn-primary" value="Search" style="margin-top: 10px;"/>
                                <input hidden name="service" value="blogList"/>
                            </div>
                        </form>
                    </div>
                    <div style="margin-top: 20px;">
                        <a href="${contextPath}/marketingController?service=blogList"><h5>Lastest Posts</h5></a>
                        <c:forEach items="${lastBlogs}" var="blog">
                            <div class="lastposts" style=" display: flex;margin-top: 20px; border: 1px #bccafd solid;" >
                                <div style="width: 104px;border-right:#bccafd 1px solid;">
                                    <img src="${blog.getThumbnail()}" style="height: 70px;width: auto; ">
                                </div>
                                <div>
                                    <a style="text-decoration: none;color: black;" href="${contextPath}marketingController?service=blogDetail&blogId=${blog.getBlogId()}"><h6>${blog.getBlogTitle()}</h6></a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <%-- main content --%>
                <div class="right col-9">
                    <div style="margin-left:9%;">
                        <div class ="row">
                            <div>
                                <a href="${contextPath}/marketingController?service=blogList" style="text-decoration:none;color:black;"><h3 class="head col-3">ALL BLOGS</h3></a>
                            </div>             
                        </div>

                        <div class="blogList row">
                            <c:forEach items="${blogList}" var="blog">
                                <div class="card" style="width: 18rem;margin-bottom: 20px;">
                                    <img class="card-img-top" src="${blog.getThumbnail()}" style="width:100%;height:270px;" alt="Card image cap">
                                    <div class="card-body">
                                        <h8>Author ${blog.getAuthor().getUserName()}</h8>
                                        <h5 class="card-title">${blog.getBlogTitle()}</h5>

                                        <c:if test="${blog.getDetail().length() <= 101}">
                                            <p class="card-text"><c:out value="${blog.getDetail()}"/>. . .</p>
                                        </c:if>
                                        <c:if test="${blog.getDetail().length() > 101}">
                                        <p class="card-text"><c:out value="${blog.getDetail().substring(0, 100)}"/>. . .</p>
                                        </c:if>
                                        <a href="${contextPath}/blog?service=blogDetail&blogId=${blog.getBlogId()}" class="btn btn-primary">Read More</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <%-- pagination --%>
                        <c:set var="page" value="${requestScope.page}"/>  
                        <ul class="pagination" style="margin:auto;">
                            <c:forEach begin="${1}" end="${requestScope.pagenum}" var="i">
                                <li class="${i==page?"active":""}"><a  href="?service=blogList&page=${i}${requestScope.pagingUrl}">${i} </a></li>
                                </c:forEach>
                        </ul>
                    </div>

                </div>
            </div>
        </div>
        <style>


            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
            }
            .pagination .active{

                background-color: #4CAF50;
                color: white;

            }
            .pagination{
                justify-content: center;
                align-items: center;
            }
            .pagination a:hover:not(.active) {background-color: #ddd;}
        </style>

    </body>

    <jsp:include page="footer.jsp"/>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <!-- Popper.JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/9650a62e47.js" crossorigin="anonymous"></script>
</html>
