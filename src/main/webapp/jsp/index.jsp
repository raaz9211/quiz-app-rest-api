<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quiz App</title>

<style type="text/css">

.but:link, .but:visited {
  background-color: #000;
  color: white;
  padding: 5px 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}

.but:hover, .but:active {
  background-color: #544e4e;
}
</style>


</head>
<body>

<h2>Quiz Application</h2>

<h4>${message}</h4>
<body>
	<form action="#" method="POST">
		<table>
			<tr>
				<td><input placeholder="username" type="text" name="username" required></td>
			</tr>
			<tr>
				<td><input  placeholder="password" type="password" name="password" required></td>
			</tr>
			<tr>
				<td><input type="submit"  class = "but"></td>
			</tr>
		</table>
	</form>
	
</body>
</html>