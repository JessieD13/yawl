<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>SYSU Client: case management</title>
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
	            	<li class="nav-header">流程案例列表</li>
	              	<li><a href="mgt.action">已上载的流程/LoadedSpecs</a></li>
	              	<li class="active"><a href="#">运行中的案例/RunningCases</a></li>

	            </ul>
	            </div><!--/.well -->
	        </div>
        
	        <div class="span9">
	        <form name="runningForm" action="" method="post"><fieldset><legend>Running Cases</legend>
	        	<div style="height:350px;"><table class="table table-hover">
	        		<thead>
						<tr>
						<th>#</th>
						<th>Case ID</th>
						<th>Spec ID</th>
						<th>Spec Name</th>
						<th>Version</th>
						</tr></thead>
					<tbody>
						<s:iterator id="case" value="%{cases}">
						<tr>
						<td><input type="radio" name="selectedCase" value="<s:property value="#case.get('identifier')"/>"></td>
						<td><s:property value="#case.get('id')"/></td>
						<td><s:property value="#case.get('identifier')"/></td>
						<td><s:property value="#case.get('name')"/></td>
						<td><s:property value="#case.get('ver')"/></td>
						<td><br></td>
						</tr>
						</s:iterator>
					</tbody></table></div>
				
				<input type="hidden" name="isPartialReq" value="false">
				<div class="btn-group" style="float: left;">
					<button class="btn btn-warning"
						onclick="runningForm.action='cancel.action';runningForm.submit();">Cancle Case</button>
					<button class="btn">Get Data</button>
			    </div>
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
		$("#nav-specmgt").addClass("active");
	});
</script>
</html>