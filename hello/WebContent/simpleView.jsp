<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>뷰</title>
</head>
<body>
	<!--  컨트롤러가 전달한 값을 읽어옴. -->
	결과 : <%= request.getAttribute("result") %>
</body>
</html>