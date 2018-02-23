<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>SYSU Client: user workqueue</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Based on yawl resourceService">
    <meta name="author" content="sysu">

    <!-- Bootstrap -->
    <link href="../public/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link href="../public/css/bootstrap-responsive.min.css" rel="stylesheet">
  </head>

  <body>
  <%@ include file="../common/header.jsp" %>

    <div class="container-fluid">
    	<div class="row-fluid">
	        <div class="span3">
	        	<div class="well sidebar-nav">
	            <ul class="nav nav-list">
	            	<li class="nav-header">应用列表</li>
	              	<li class="active"><a href="available.action">可用的应用</a></li>
	              	<li><a href="all.action">所有的应用</a></li>
	            </ul>
	          </div><!--/.well -->
	        </div>
        
	        <div class="span9">
	        <form name="appForm" action="" method="post"><fieldset><legend>Available Applications</legend>
	        	<div style="height:350px;"><table class="table table-hover">
	        		<thead>
						<tr>
						<th>#</th>
						<th>App Name</th>
						<th>Description</th>
						<th>App Type</th>
						<th>App URL</th>
						</tr></thead>
					<tbody>
						<s:iterator id="app" value="%{apps}">
						<tr>
						<td><s:property value="#app.getId()"/></td>
						<td><s:property value="#app.getAppName()"/></td>
						<td><s:property value="#app.getAppDesc()"/></td>
						<td><s:property value="#app.getAppType()"/></td>
						<td><s:property value="#app.getAppUrl()"/></td>						
						<!-- <td><a class="btn" href="../app/view.action?appid=<s:property value="#app.getId()"/>">view</a></td> -->
						</tr>
						</s:iterator>
					</tbody></table></div>

			 </fieldset></form></div>
			 
			</div>
			 
		<%@ include file="../common/footer.jsp" %>
    </div> <!-- /container -->
</body>
<!-- Placed at the end of the document so the pages load faster -->
<script src="../public/js/jquery.min.js"></script>
<script src="../public/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#nav-app").addClass("active");
	});
</script>
</html>