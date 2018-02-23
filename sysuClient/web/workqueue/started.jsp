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
	            	<li class="nav-header">个人工作列表</li>
	              	<li><a href="offered.action">未接收/Offered</a></li>
	              	<li><a href="allocated.action">已接收/Allocated</a></li>
	                <li class="active"><a href="started.action">正在执行/Started</a></li>
	                <li><a href="suspended.action">挂起/Suspended</a></li>
	            </ul>
	            </div><!--/.well -->
        	</div>
        	
        	<div class="span9">
        	<%
        	String requestUrl = request.getRequestURL().toString();
        	String completeUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/")+1)+"complete.action";
        	 %>
			<form name="startedForm" action="" method="post"><fieldset><legend>Started Work Items</legend>
       			<div style="height:350px;"><table class="table table-hover">
        			<thead>
						<tr>
						<th>#</th>
						<th>Task ID</th>
						<th>Task Name</th>
						<th>Case ID</th>
						<th>Status</th>
						<th>Created</th>
						</tr></thead>
					<tbody>
						<s:iterator id="workitem" value="%{items}">
						<tr>
						<td><input type="radio" name="selectedItem" value="<s:property value="#workitem.getID()"/>"></td>
						<td><s:property value="#workitem.getTaskID()"/></td>
						<td><s:property value="#workitem.getTaskName()"/></td>
						<td><s:property value="#workitem.getCaseID()"/></td>
						<td><s:property value="#workitem.getStatus()"/></td>
						<td><s:property value="#workitem.getEnablementTime()"/></td>
						<td><a class="btn" href="view.action?source=<%=completeUrl %>&selectedItem=<s:property value="#workitem.getID()"/>">View</a></td>
						</tr>
						</s:iterator>
					</tbody></table></div>
					
				<div class="btn-group" style="float: left;">
					<button class="btn">挂起/Suspend</button>
					<button class="btn">重新分配/Reallocate s/l</button>
					<button class="btn">重新分配/Reallocate s/f</button>
					<button class="btn">新建实例/New Instance</button> <!-- 该操作需要判断选中的wir是否有新建实例所需要的参数 -->
					<button class="btn" onclick="startedForm.action='complete.action';startedForm.submit();">完成/Complete</button>
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
		$("#nav-workqueue").addClass("active");
	});
</script>
</html>