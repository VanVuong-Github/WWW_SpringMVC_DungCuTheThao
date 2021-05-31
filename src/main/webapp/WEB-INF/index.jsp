<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style>
.header {
	background-color: lightblue;
	height: 100px;
}

.footer {
	position: fixed;
	left: 0;
	bottom: 0;
	height: 100px;
	width: 100%;
	background-color: lightblue;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="header">
		<p>header</p>
	</div>
	<div class="container">
		<h1>Dung cu the thao</h1>
		<a href="api/sanphams">tat ca san pham</a>
		<a href="api/khachhangs">tat ca khach hang</a>
	</div>
	<div class="footer">
		<p>footer</p>
		<p>Lại Văn Vượng - 18072661 - DHKTPM14</p>
	</div>
</body>
</html>