<%-- 
    Document   : newjsp
    Created on : Apr 13, 2016, 12:00:28 AM
    Author     : ciao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%
            String s = "ciao";
            out.print(s);
            %>
    </body>
</html>
