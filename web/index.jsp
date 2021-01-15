<%-- 
    Document   : index
    Created on : Jan 5, 2021, 8:04:24 AM
    Author     : KHAM
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hana Shop</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="css/shop-homepage.css" rel="stylesheet">
        <script src="js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <link href="css/css.css" rel="stylesheet">

    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.jsp">Hana Shop</a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="index.jsp">Home</a>
                        </li>
                        <li>
                            <a href="login.jsp">Login</a>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>

        <!-- Page Content -->
        <form action="MainController" method="POST">
            <div class="container">

                <div class="row">

                    <div class="col-md-3">
                        <p class="lead">Search</p>
                        <div class="list-group">
                            <input type="text" name="txtSearchByName" placeholder="Product's name"><br/>
                            <input type="text" name="txtFromPrice" placeholder="Min Price"/> <input type="text" name="txtToPrice" placeholder="Max Price"/><br/>
                            <input type="text" name="txtSearchCategory" placeholder="Product's Category"/><br/>
                            <input type="submit" name="action" value="Search"/>
                        </div>
                    </div>

                    <div class="col-md-9">
                        <div class="row">
                            <c:forEach items="${sessionScope.listSearched}" var="list" varStatus="counter">
                                <div class="col-sm-4 col-lg-4 col-md-4">
                                    <div class="thumbnail">
                                        <img src="${list.image}" alt="">
                                        <div class="caption">
                                            <p class="productName">"${list.productName}</p>
                                            <h4>${list.price}</h4>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="container">
                            <ul class="pagination">
                                <c:forEach begin="1" end="${sessionScope.pageCount}" varStatus="counter">
                                    <c:if test="${counter.count == sessionScope.page}" var="isActive">
                                        <li class="page-item active">
                                            <a class="page-link" href="MainController?action=Search&page=${counter.count}&txtSearchByName=&txtFromPrice=&txtToPrice=&txtSearchCategory=">${counter.count}</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${!isActive}">
                                        <li class="page-item">
                                            <a class="page-link" href="MainController?action=Search&page=${counter.count}&txtSearchByName=&txtFromPrice=&txtToPrice=&txtSearchCategory=">${counter.count}</a>
                                        </li>
                                    </c:if>

                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                </div>

            </div>

            <!-- /.container -->
            <div class="container">
                <hr>
            </div>
            <!-- /.container -->
        </form>
    </body>
</html>
