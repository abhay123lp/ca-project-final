<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register Certificate</title>
</head>
<body>
	<form action="ReciveCSR" method="post">
	<h1 align="center">REGISTER CERTIFICATE</h1>
		<table align="center">
			<tr align="left">
				<th>Country Name (2 letter code) [VN]</th>
				<td><input type="text" name="c"/></td>
			</tr>
			<tr align="left">
				<th>State or Province Name (full name) [Some-State]</th>
				<td><input type="text" name="st" /></td>
			</tr>
			<tr align="left">
				<th>Locality Name (eg, city) []</th>
				<td><input type="text" name="l" /></td>
			</tr>
			<tr align="left">
				<th>Organizatin Name (eg, company) [Internet Widgits Pty Ltd]</th>
				<td><input type="text" name="o" /></td>
			</tr>
			<tr align="left">
				<th>Organization Unit Name (eg, section) []</th>
				<td><input type="text" name="ou" /></td>
			</tr>
			<tr align="left">
				<th>Common Name (eg, server FQDN or YOUR name) []</th>
				<td><input type="text" name="cn" /></td>
			</tr>
			<tr align="left">
				<th>Email Adress []</th>
				<td><input type="text" name="emailAddress" /></td>
			</tr>
			<tr align="left">
				<th>Password [Your Private Key Pass Phrase]</th>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right">
					<input type="submit" value="Register" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>