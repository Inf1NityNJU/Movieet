<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/css" var="css" />
<spring:url value="/js" var="js" />
<spring:url value="/resources/fonts/" var="fonts" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dva Demo</title>
    <link rel="stylesheet" href="${css}/index.css" />
</head>
<body>

<div id="root"></div>

<script src="${js}/index.js"></script>


</body>
</html>
