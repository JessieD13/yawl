<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dataTable.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" href="css/DT_bootstrap.css" />
  </head>
  
  <body>
   <table>
   	<tr>
   		<td>#1</td>
   		<td>#2</td>
   	</tr>
   	<tr>
   		<td>#1</td>
   		<td>#2</td>
   	</tr>
   	<tr>
   		<td>#1</td>
   		<td>#2</td>
   	</tr>
   </table>
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script src="js/jquery.min.js"></script>
  </body>
  
</html>
