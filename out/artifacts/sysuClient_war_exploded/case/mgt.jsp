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
	              	<li class="active"><a href="#">已上载的流程/LoadedSpecs</a></li>
	              	<li><a href="running.action">运行中的案例/RunningCases</a></li>

	            </ul>
	            </div><!--/.well -->
	        </div>
        
	        <div class="span9">
	        <form name="loadedForm" action="" method="post"><fieldset><legend>Loaded Specifications</legend>
	        	<div style="height:350px;"><table class="table table-hover">
	        		<thead>
						<tr>
						<th>#</th>
						<th>Name</th>
						<th>Version</th>
						<th>Title</th>
						<th>Status</th>
						<th>Documentation</th>
						</tr></thead>
					<tbody>
						<s:iterator id="spec" value="%{specs}">
						<tr>
						<td><input type="radio" name="selectedSpec" value="<s:property value="#spec.getID().toKeyString()"/>"></td>
						<td><s:property value="#spec.getName()"/></td>
						<td><s:property value="#spec.getSpecVersion()"/></td>
						<td><s:property value="#spec.getMetaTitle()"/></td>
						<td><s:property value="#spec.getStatus()"/></td>
						<td><s:property value="#spec.getDocumentation()"/></td>
						</tr>
						</s:iterator>
					</tbody></table></div>
				<input type="hidden" name="isPartialReq" value="false">
				<div class="btn-group" style="float: left;">
					<button class="btn"
						onclick="loadedForm.action='launch.action';loadedForm.submit();">Launch Case</button>
					<button class="btn">Launch Later</button>
					<button class="btn">Get Info</button>
					<button class="btn">Download Log</button>
			    </div>
			    <div class="btn-group"><button class="btn btn-warning" >Unload Cases</button></div>
			</fieldset></form>
	        <form name="uploadForm" action="upload.action" method="post" enctype="multipart/form-data">
			  	<fieldset>
				  	<input type="button" name="ywl" placeholder="selected a specification file"><!-- title默认为Browse -->
				  	<input type="submit" class="btn btn-warning" value="Upload New Specification">
			  	</fieldset>
			</form></div>
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

<script src="../public/js/bootstrap.file-input.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('input[type=file]').bootstrapFileInput();
	});
</script>

</html>