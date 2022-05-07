<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Question Menu</title>
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

<h2>Question Menu</h2>
<h4>${message}</h4>

<a href="createQuiz"  class = "but">Create Quiz</a> <br><br>
<a href="viewQuizzes"  class = "but">Display Quiz</a><br><br>
<a href="dashBoard"  class = "but">Back</a>
</body>
</html>