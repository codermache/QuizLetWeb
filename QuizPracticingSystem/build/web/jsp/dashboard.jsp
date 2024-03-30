

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>

    <head>   
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">     
        <link rel="stylesheet" href="${contextPath}/css/dashboard.css">
        <title>Dashboard</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <%--Start chart javascript--%>
        <c:if test="${nameList.size()>0}">
            <script>
                window.onload = function () {
                    var data = new Array(${statistics.size()});
                    var i = 0;
                <c:forEach items="${statistics}" var="string">
                    data[i] =${string};
                    i++;
                </c:forEach>
                    var dataPoint = new Array(${statistics.size()});
                    for (var k = 0; k < dataPoint.length; k++) {
                        dataPoint[k] = [];
                    }
                    var chartRevenue = new CanvasJS.Chart("chart", {
                        animationEnabled: true,
                        theme: "light2",
                        title: {
                            text: "${option}"
                        },
                        axisY: {
                            title: "${target}",
                            titleFontSize: 24,
                            includeZero: true
                        },
                        data: [
                <c:forEach var="k" begin="0" end="${nameList.size()-1}">
                            {
                                type: "line",
                                yValueFormatString: "#,###",
                                name: "${nameList.get(k)}",
                                showInLegend: true,
                                dataPoints: dataPoint[${k}]
                            },
                </c:forEach>
                        ]
                        
                    }
                    );
                    function addData(data) {
                        for (var k = 0; k < data.length; k++) {
                            for (var j = 0; j < data[k].length; j++) {
                                dataPoint[k].push({
                                    x: new Date(data[k][j].date),
                                    y: data[k][j].value
                                });
                            }
                        }
                        chartRevenue.render();
                    }
                    addData(data);
                }
            </script>    
        </c:if>
        <%--End chart javascript--%>
                <style>
            .choose .active,.tab .active{
                border: #4472c4 3px solid;
                background-color: white;
                color:#4472c4;
                font-weight: bold;
                opacity:1;
                font-size:120%;
            }
            .choose a, .tab a{
                border: 1px solid black;
                margin-bottom: 10px;
                opacity: 0.7;
                font-size:110%;
            }
            .choose a:hover, .tab a:hover{
                color: #4472c4;
                font-weight: bold;
                opacity:1;
                font-size:120%;
            }
            .date input{
                border:2px solid #4472c4;
                font-weight: 600;
            }
            #info{
                border: 1px solid #4472c4;
                margin:2px;
            }

        </style>
        <%--End page style--%>
    </head>

    <body>
        <jsp:include page="header.jsp"/>
        <div class="container" style="">

            <div class="row" style="text-align: center; margin-top: 30px;">
                <h4>Dashboard</h4>
            </div>
            <div class="row">
                <div class="col-md-2"></div>
                <%--Statistics view option--%>
                <div class="tab col-md-6" style="margin-left:110px;">
                    <div >
                        <a class="btn ${option=="subject"?"active":""}" role="button" href="${contextPath}/dashboard?&option=subject&target=new&attribute=revenue">Subjects</a>
                        <a class="btn ${option=="registration"?"active":""}" role="button" href="${contextPath}/dashboard?&option=registration">Registrations</a>
                        <a class="btn ${option=="revenue"?"active":""}" role="button" href="${contextPath}/dashboard?&option=revenue&target=total">Revenues</a>
                        <a class="btn ${option=="customer"?"active":""}" role="button" href="${contextPath}/dashboard?&option=customer&target=newlyRegistered">Customers</a>
                        <a class="btn ${option=="view"?"active":""}" role="button" href="${contextPath}/dashboard?&option=view">Web</a>
                    </div>
                </div>
                <%--End Statistics view option--%>
                <div class="col-md-3">
                </div>
            </div>
            <div class="" class="tabcontent" style="border-top:1px solid black;">
                <%--Subject statistics view option--%>
                <c:if test="${option=='subject'}">
                    <div class="row" style="padding-bottom: 100px; padding-top: 20px;">
                        <div class="choose col-3" style="display: grid;">
                            <a class="btn ${attribute=="revenue"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=subject&target=${target}&attribute=revenue">Revenue</a>
                            <a class="btn ${attribute=="registrationCount"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=subject&target=${target}&attribute=registrationCount">Registration</a>                 
                        </div>
                        <div class="choose col-3" style="display: grid;">
                            <a class="btn ${target=="new"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=${option}&target=new&attribute=${attribute}">New Subject</a>
                            <a class="btn ${target=="all"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=${option}&target=all&attribute=${attribute}">All Subject</a>
                        </div>
                        <div class="date col-6">
                            <form action="${contextPath}/dashboard" method="GET" style="float:right;">
                                <input hidden name="service" value="dashboard">

                                <input onchange="this.form.submit()" type="date" name="from" value="${from}" max="${to}">
                                <input onchange="this.form.submit()" type="date" name="to" value="${to}" min="${from}" max="${currentDate}">
                                <input hidden name="option" value=${option}>
                                <input hidden name="target" value="${target}">
                                <input hidden name="attribute" value="${attribute}">
                            </form>
                        </div>
                    </div>
                </c:if>
                <%--Registration statistics view option--%>
                <c:if test="${option=='registration'}">
                    <div class="row" style="padding-bottom: 100px; padding-top: 20px;">
                        <div class="choose col-6" style="display: grid;">
                        </div>

                        <div class="date col-6">
                            <form action="${contextPath}/dashboard" method="GET" style="float:right;">
                                <input hidden name="service" value="dashboard">
                                <input onchange="this.form.submit()" type="date" name="from" value="${from}" max="${to}">
                                <input onchange="this.form.submit()" type="date" name="to" value="${to}" min="${from}" max="${currentDate}">
                                <input hidden name="option" value=${option}>                              
                            </form>
                        </div>
                    </div>
                </c:if>
                <%--Revenue statistics view option--%>
                <c:if test="${option=='revenue'}">
                    <div class="row" style="padding-bottom: 100px; padding-top: 20px;">
                        <div class="choose col-3" style="display: grid;">
                            <a class="btn ${target=="total"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=${option}&target=total">Total</a>
                            <a class="btn ${target=="bySubjectCate"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=${option}&target=bySubjectCate">By Subject Category</a>
                        </div>
                        <div class="col-3"></div>
                        <div class="date col-6">
                            <form action="${contextPath}/dashboard" method="GET" style="float:right;">
                                <input hidden name="service" value="dashboard">
                                <input onchange="this.form.submit()" type="date" name="from" value="${from}" max="${to}">
                                <input onchange="this.form.submit()" type="date" name="to" value="${to}" min="${from}" max="${currentDate}">
                                <input hidden name="option" value=${option}>
                                <input hidden name="target" value="${target}">
                            </form>
                        </div>
                    </div>
                </c:if>
                <%--Customer statistics view option--%>
                <c:if test="${option=='customer'}">
                    <div class="row" style="padding-bottom: 100px; padding-top: 20px;">
                        <div class="choose col-3" style="display: grid;">
                            <a class="btn ${target=="newlyRegistered"?"active":""}" role="button" href="${contextPath}/dashboard?&from=${from}&to=${to}&option=${option}&target=newlyRegistered">Newly Registered</a>                           
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <div class="row">
                        <table>
                            <c:if test="${target=='newlyRegistered'}">
                                <tr class="table-primary">
                                    <th>User ID</th>
                                    <th>Name</th>
                                    <th>Role</th>
                                    <th>Email</th>
                                    <th>Mobile</th> 
                                </tr>
                                <c:forEach items="${userList}" var="user">
                                    <tr>
                                        <td>${user.getUserId()}</td>
                                        <td>${user.getUserName()}</td>
                                        <td>
                                            <c:if test="${user.getRoleId()==1}">Customer</c:if>
                                            <c:if test="${user.getRoleId()==2}">Sale</c:if>
                                            <c:if test="${user.getRoleId()==3}">Marketing</c:if>
                                            <c:if test="${user.getRoleId()==4}">Expert</c:if>
                                            <c:if test="${user.getRoleId()==5}">Admin</c:if>
                                            </td>
                                            <td>${user.getUserMail()}</td>
                                        <td>${user.getUserMobile()}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                    </div>
                </c:if>
                <%--View statistics view option--%>
                <c:if test="${option=='view'}">
                    <div class="row" style="padding-bottom: 100px; padding-top: 20px;">
                        <div class="choose col-6" style="display: grid;">
                            <div>
                                <div>
                                    <button id="info" type="button" class="btn" data-toggle="modal" data-target=".subjectInfo">Total subjects: ${totalSubjectCount} subjects</button>
                                </div>
                                <div class="modal fade bd-example-modal-sm subjectInfo" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <h5 class="modal-title" id="exampleModalLabel" style="margin-left:12px;">Subject Cate</h5>                           
                                            <div class="modal-header">
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach items="${subjectCateList}" var="subjectCate">
                                                    <c:set value="0" var="count"/>
                                                    <c:if test="${subjectCateCountMap.get(subjectCate.getSubjectCateName())!=null}">
                                                        <c:set value="${subjectCateCountMap.get(subjectCate.getSubjectCateName())}" var="count"/>
                                                    </c:if>

                                                    <h6>${subjectCate.getSubjectCateName()}: ${count}</h6>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button id="info" type="button" class="btn" data-toggle="modal" data-target=".registrationInfo">Total registrations count: ${totalRegistrationCount}</button>
                                <div class="modal fade bd-example-modal-sm registrationInfo" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <h5 class="modal-title" id="exampleModalLabel" style="margin-left:12px;">Registration</h5>                           
                                            <div class="modal-header">
                                            </div>
                                            <div class="modal-body">
                                                <h6 style="color: green;">Total paid: ${paidRegistrationCount}</h6>
                                                <h6 style="color: red;">Total unpaid: ${unpaidRegistrationCount}</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button id="info" type="button" class="btn" data-toggle="modal" data-target=".userInfo">Total user: ${totalUserCount}</button>

                                <div class="modal fade bd-example-modal-sm userInfo" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <h5 class="modal-title" id="exampleModalLabel" style="margin-left:12px;">Users</h5>                           
                                            <div class="modal-header">
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach items="${userRoleList}" var="userRole">
                                                    <c:set value="0" var="count"/>
                                                    <c:if test="${userRoleCountMap.get(userRole.getUserRoleName())!=null}">
                                                        <c:set value="${userRoleCountMap.get(userRole.getUserRoleName())}" var="count"/>
                                                    </c:if>

                                                    <h6>${userRole.getUserRoleName()}: ${count}</h6>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div>
                                <button id="info" type="button" class="btn">Total view: ${totalView}</button>
                            </div>
                        </div>
                        <div class="date col-6">
                            <form action="${contextPath}/dashboard" method="GET" style="float:right;">
                                <input hidden name="service" value="dashboard">
                                <input onchange="this.form.submit()" type="date" name="from" value="${from}" max="${to}">
                                <input onchange="this.form.submit()" type="date" name="to" value="${to}" min="${from}" max="${currentDate}">
                                <input hidden name="option" value=${option}>                              
                            </form>
                        </div>
                    </div>
                </c:if>
                <c:if test="${option!='customer'}">
                    <c:choose>
                        <c:when test="${nameList.size()!=0}">
                            <div class='row' style="height: 300px; width: 100%; padding-bottom: 100px;">
                                <div>
                                    <div class="subtabcontent" style='display:block;'>
                                        <div id="chart" style="height: 300px; width: 100%;">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class='row' style="height: 300px; width: 100%;">
                                <div>
                                    <div class="subtabcontent" style='display:block;'>
                                        <h4>There're no statistics</h4>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
        <script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    </body>
    <jsp:include page="footer.jsp"/>

</html>