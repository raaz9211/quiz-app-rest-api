<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Quistion</title>
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

<h2>Showing All Questions</h2>

<table border=1>
<tr>
 <th>Title</th>
 <th>Difficulty</th>
 <th>Action</th>
 <th>Option 1</th>
 <th>Option 2</th>
 <th>Option 3</th>
 <th>Option 4</th>
 
</tr>

<c:forEach items="${questions}" var="question"> 

<tr>
 <td> ${question.title} </td>
 <td> <c:out value="${question.difficulty}"></c:out>  </td>
 <td> <a href="editQuestion?id=${question.id}"  class = "but">Edit</a> | <a href="deleteQuestion?id=${question.id}"  class = "but">Delete</a> </td>
 <c:forEach items="${question.options}" var="option">
  <td>  <c:out value="${option.getValue()}"></c:out>   </td>
 </c:forEach> 
</tr>

</c:forEach>
</table>
 <a href="question" class = "but">Back</a>
</body>
</html>