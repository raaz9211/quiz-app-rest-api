<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Question</title>
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
	<form action="insertEditedQuestion?&id=${question.id}" method="POST">
		<table>
			<tr>
				<th>Title</th>
				<td><input type="text" name="title" value="${question.title}"></td>
			</tr>
			<tr>
				<th>Difficulty</th>
				<td><input type="text" name="difficulty"  value="${question.difficulty}"></td>
			</tr>
			<tr>
				<th>Tag</th>
				<td><input type="text" name="tag" value="${question.tag}"></td>
			</tr>
			
			<tr>
				<th>Option 1</th>
				<td><input type="text" name="value" value="${question.getOptions().get(0).getValue()}"></td>
			
			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>

			<tr>
				<th>Option 2</th>
				<td><input type="text" name="value" value="${question.getOptions().get(1).getValue()}"></td>

			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>

			<tr>
				<th>Option 3</th>
				<td><input type="text" name="value" value="${question.getOptions().get(2).getValue()}"></td>

			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>Option 4</th>
				<td><input type="text" name="value" value="${question.getOptions().get(3).getValue()}"></td>

			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>

			<tr>
				<td><input type="submit" value = "Update Question"  class = "but"></td>
			</tr>
		</table>
		
		
	</form>
	
	 <a href="question" class = "but">Back</a>
	 
</body>
</html>