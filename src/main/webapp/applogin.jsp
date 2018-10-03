<%-- 
    Document   : applogin
    Created on : Apr 15, 2016, 11:04:35 AM
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
        <p>
            <% 
                String param = (String) application.getAttribute("par");
                param = (param == null) ? "" : param;
                String name = (String) application.getAttribute(param);
                out.println(name == null ? "Anonimo" : name);
            %>
        </p>
    </body>
</html>
