<%-- 
    Document   : index
    Created on : Jan 5, 2021, 8:04:24 AM
    Author     : KHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>LOGIN</h1>
        <form action="MainController" method="POST">
            UserID: <input type="text" name="txtUserID" />
            <font color="red">
                ${INVALID.userIDError}
            </font><br/>
            Password: <input type="text" name="txtPassword" />
            <font color="red">
                ${INVALID.passwordError}
            </font>
            <br/>
            <input type="submit" name="action" value="Login" /><br/>
            Search: <input type="text" name="txtSearch" />
        </form>
        
    </body>
</html>
