<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Quizzes</title>
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
<h4>${message}</h4>

<h2>Showing All Quiz</h2>

<table border=1>
<tr>
 <th>Title</th>

 <th>Action</th>
</tr>

<c:forEach items="${quizzes}" var="quiz"> 

<tr>
 <td> ${quiz.name} </td>
 <td> <a href="deleteQuiz?id=${quiz.id}"  class = "but">Delete</a>  | 
 <a href="viewQuizQuestions?id=${quiz.id}"  class = "but">View Question</a> | 
 <a href="getQuestions?id=${quiz.id}"  class = "but">Add Question</a> | 
 
 </td>
 
</tr>

</c:forEach>
</table>
 <a href="quiz" class = "but">Back</a>
</body>
</html>