<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VERIFY CERTIFICATE</title>
</head>
<body>
	<body>
	<h3 align="center">VERIFY CERTIFICATE</h3>
	Browse certificate file to upload: <br />
	<form action="Upload" method="post"
		                        enctype="multipart/form-data">
		<input type="file" name="file" size="50" />
		<br />
		<input type="submit" value="Verify Cert" /> <br/>
		${requestScope.result }
	</form>
</body>
</html>