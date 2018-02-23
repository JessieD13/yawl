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
    <div class="container-fluid">
    	<div class="row-fluid">
	        <div class="span12">
	        <form name="offeredForm" action="" method="post"><fieldset><legend>Offered Work Items</legend>
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
						</tr>
						</s:iterator>
					</tbody></table></div>
				
				<input type="hidden" name="isPartialReq" value="true">
				<div class="btn-group" style="float: left;">
					<button class="btn"	onclick="offeredForm.action='accept.action';offeredForm.submit();">
						接收/AcceptOffer</button>
					<button class="btn" onclick="offeredForm.action='acceptstart.action';offeredForm.submit();">
						直接开始/Accept and Start</button>
					<!-- <button class="btn" onclick="">
						链式/Chain</button> -->
			    </div>		
			</fieldset></form></div>
			
      </div>
    </div> <!-- /container -->
</body>
<!-- Placed at the end of the document so the pages load faster -->
<script src="../public/js/jquery.min.js"></script>
<script src="../public/js/bootstrap.min.js"></script>
</html>