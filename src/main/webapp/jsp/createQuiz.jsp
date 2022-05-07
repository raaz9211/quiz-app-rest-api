<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Quiz</title>

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

<h4>${message}</h4>

<body>
	<form action="insertQuiz" method="POST">
		<table>
			<tr>
				<th>Quiz</th>
				<td><input type="text" name="name" required></td>
			</tr>
			<tr>
				<td><input type="submit" value = "Add Quiz"  class = "but"></td>
			</tr>
		</table>
	</form>
	
	
	 <a href="quiz" class = "but">Back</a>
	 
</body>
</html>