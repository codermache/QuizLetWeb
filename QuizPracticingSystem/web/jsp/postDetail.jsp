

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Detail</title>
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
        <c:if test="${categoryList == null}">
            <c:redirect url="/PostDetailController?service=getPostCategory"/>
        </c:if>
        <c:if test="${editBlog != null}" >
            <c:set var="service" value="editBlog" />
        </c:if>
        <c:if test="${editBlog == null}" >
            <c:set var="service" value="addBlog" />
        </c:if>
        <%-- Include header page --%>
        <jsp:include page="/jsp/header.jsp"/>
        <c:if test="${ sessionScope.role.getUserRoleName().equalsIgnoreCase('admin')}">
            <%-- Check If user registedSubject is avaiable not, if not redirect to load information --%>     
            <div class="main">
                <%-- Login form --%>
                <div class="container" style="align-self: center; min-height: 50vh">
                    <%-- Start form --%>
                    <form action="${contextPath}/PostDetailController?service=${service}" enctype="multipart/form-data" method="post">
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
                                <h1>Post Detail</h1>
                                <label class="label control-label">Post Title</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <input class="form-control" type="text" name="postTitle" required value="${editBlog.getBlogTitle()}" maxlength="100">
                                </div>

                                <c:if test="${editBlog != null}" >
                                    <input type="text" name="editBlogId" value="${ editBlog.getBlogId() }" hidden>
                                    <label class="label control-label">Author</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="text" name="author" required value="${editBlog.getAuthor().getUserName()}" readonly>
                                    </div>

                                    <label class="label control-label">Create time</label>
                                    <div class="form-group">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input class="form-control" type="date" name="createTime" required value="${editBlog.getCreated()}" readonly>
                                    </div>
                                </c:if>

                                <label class="label control-label">Detail</label>
                                <div class="form-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <textarea class="form-control" name="detail" required rows="6" cols="50" maxlength="1000"><c:out value="${editBlog.getDetail()}"/></textarea>
                                </div>

                                <label class="label control-label">Categories:</label>
                                <div class="form-group">
                                    <c:forEach items="${categoryList}" var="category">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user">
                                            </span>
                                        </span>                                    
                                        <input class="checkbox" type="checkbox" name="categories_<c:out value="${category.getPostCateId()}"/>" value="${category.getPostCateId()}" <c:if test="${ blogCategoryId.contains(category.getPostCateId())}">checked</c:if>>
                                        <c:out value="${category.getPostCateName()}" />
                                        <br>
                                    </c:forEach>
                                </div>
                                
                                <div  class="wrapper">
                                    <label class="label control-label">Thumbnail</label>
                                    <div class="form-group">
                                        <div style="width: 100%;border: dashed green;">
                                            <img id="img" style="width: 100%;" src="${contextPath}/${editBlog.getThumbnail()}" alt="Thumbnail image" >
                                        </div>
                                        <br>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input id="file" class="form-control" type="file" name="thumnail" onchange="readURL(this);">
                                    </div>
                                </div>
                                        
                                <%-- Submit form --%>
                                <div class="input-group" >
                                    <button style="margin-left: auto; margin-right: auto; " type="submit" id="submit" class="btn btn-success">Submit</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </c:if>
            <c:if test="${ !sessionScope.role.getUserRoleName().equalsIgnoreCase('admin')}">
                <h2 style="text-align: center;">You don't have the right to access this page</h2>
            </c:if>
            <%-- Include footer page --%>
            <jsp:include page="/jsp/footer.jsp"/>
            <script>
                function readURL(input) {
                    if (input.files && input.files[0]) {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            $('#img').attr('src', e.target.result);
                        };
                        reader.readAsDataURL(input.files[0]);
                    }
                }
            </script>
    </body>
</html>
