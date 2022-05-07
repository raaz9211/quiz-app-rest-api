<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Question</title>
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
	<form action="insertQuestion" method="POST">
		<table>
			<tr>
				<th>Title</th>
				<td><input type="text" name="title" required></td>
			</tr>
			<tr>
				<th>Difficulty</th>
				<td><input type="text" name="difficulty" required></td>
			</tr>
			<tr>
				<th>Tag</th>
				<td><input type="text" name="tag"></td>
			</tr>
			
			<tr>
				<th>Option 1</th>
				<td><input type="text" name="value"></td>
			
			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>
		
			<tr>
				<th>Option 2</th>
				<td><input type="text" name="value"></td>
			
			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>
		
			<tr>
				<th>Option 3</th>
				<td><input type="text" name="value"></td>
			
			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>Option 4</th>
				<td><input type="text" name="value"></td>
			
			<td>
				<select type="boolean" name="isAnswer">
				  <option  value="true">True</option>
				  <option  value="false">False</option>
				</select>
				</td>
			</tr>
		
			<tr>
				<td><input type="submit" value = "Add Question"  class = "but"></td>
			</tr>
		</table>
		
		
	</form>
	
	 <a href="question" class = "but">Back</a>
	 
</body>
</html>