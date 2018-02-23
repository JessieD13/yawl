<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Dynamic Form</title>
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
	        	<form id="dynamicform" name="dynamicform" action="" method="POST">
	        		<fieldset>
	        			<legend>Dynamic Form for <s:property value="selectedWir.getTaskName()"/></legend>
	        			<div style="height: 100px;">
	        				<table class="table table-hover">
	        					<thead><tr>
											<th>CaseID</th>
											<th>TaskID</th>
											<th>TaskStatus</th>
											<th>Documentation</th>
										</tr></thead>
										<tbody><tr>
											<td><s:property value="selectedWir.getCaseID()"/></td>
											<td><s:property value="selectedWir.getTaskID()"/></td>
											<td><s:property value="selectedWir.getStatus()"/></td>
											<td>&quot;<s:property value="selectedWir.getDocumentation()"/></td>
										</tr></tbody>
	        				</table>
	        			</div>
	        			<div style="height: 250px;">
	        				<s:iterator id="parameter" value="params">
	        					<label class="span2"><s:property value="#parameter.getName()"/></label>
	        					<input class="span4" type="text"
	        								 name="wirMap.<s:property value="#parameter.getName()"/>"
	        								 value="<s:property value="#parameter.getDefaultValue()"/>"
	        								 <s:if test='#parameter.getParamType() == "inputParam"'>disabled="disabled"</s:if>/>
	        					<br>
	        				</s:iterator>
	        				<s:if test="isPartialReq">
	        					<input type="hidden" name="isPartialReq" value="true">
	        				</s:if>
	        				<input type="hidden" name="source" value="<s:property value="source" />">
	        				<input type="hidden" name="selectedItem" value="<s:property value="selectedItem" />">
	        			</div><div class="clear"></div>
	        			<div class="btn-group" style="float: left;">
									<a class="btn"
										href="<s:property value='source'/><s:if test='isPartialReq'>?isPartialReq=true</s:if>">取消/Cancel</a>
									<button class="btn"
										onclick="dynamicform.action='update.action';dynamicform.submit();">保存/Save</button>
									<button class="btn"
										onclick="dynamicform.action='update.action?complete=true';dynamicform.submit();">完成/Complete</button>
			    			</div>
	        		</fieldset>
	        	</form>
	        	
	        </div>
      </div>
    </div> <!-- /container -->
</body>
<!-- Placed at the end of the document so the pages load faster -->
<script src="../public/js/jquery.min.js"></script>
<script src="../public/js/bootstrap.min.js"></script>
<script>
function updateWorkItem(complete) {
	$.ajax({
		url : "../workitemjson/update.action",
		type : "POST",
		data : $("#dynamicform").serialize(),
		dataType : "json",
		success : function(response) {
			if (complete) completeWorkItem();
		},
		error : function(xhr, error, printstack){}
	});
	$("#dynamicform").submit(function(){
		
	});
}
</script>
</html>