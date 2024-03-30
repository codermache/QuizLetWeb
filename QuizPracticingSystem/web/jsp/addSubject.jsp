

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new subject</title>

        <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body >

        <jsp:include page="header.jsp"/>
        <form>
            <div class="form-group row">
                <label  class="col-sm-2 col-form-label">Subject Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"  placeholder="Subject Name">
                </div>
            </div>
            <div class="form-group row">
                <label  class="col-sm-2 col-form-label">Description</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" placeholder="Description">
                </div>
            </div>
            <div class="form-group row">
                <label  class="col-sm-2 col-form-label">Owner</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"  placeholder="Owner">
                </div>
            </div>
            <div class="form-group-row ">
                <label  class="col-sm-2 col-form-label">Category</label>

                <div class =" col-sm-10">
                    <button class="btn btn-default dropdown-toggle" type="button" 
                            id="dropdownMenu1" data-toggle="dropdown" 
                            aria-haspopup="true" aria-expanded="true"
                            style="width: 80%; border: 1px solid #ced4da">
                        <i class="fas fa-bars"></i>

                    </button>
                    
                </div>
            </div>
            <div class="form-group">
                <label for="exampleFormControlFile1">Add thumbnail image</label>
                <input type="file" class="form-control-file" id="exampleFormControlFile1">
            </div>

            <div class="form-group row">
                <div class="col-sm-2">Featured subject</div>
                <div class="col-sm-10">
                    <div class="form-check">

                        <div class="custom-control custom-switch">
                            <input type="checkbox" class="custom-control-input" id="customSwitch1">
                            <label class="custom-control-label" for="customSwitch1">is Featured subject</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-2">Status</div>
                <div class="col-sm-10">
                    <div class="form-check">

                        <div class="custom-control custom-switch">
                            <input type="checkbox" class="custom-control-input" id="customSwitch2">
                            <label class="custom-control-label" for="customSwitch2">On/Off</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-primary">Add subject</button>
                </div>
            </div>
        </form>


        <jsp:include page="footer.jsp"/>
    </body>
</html>
