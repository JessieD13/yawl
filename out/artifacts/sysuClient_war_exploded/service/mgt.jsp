<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>SYSU Client: service management</title>
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
	            	<li class="nav-header">服务列表</li>
	              	<li class="active"><a href="#">YAWL引擎已注册的组件/RegisteredServices</a></li>
	            </ul>
	            </div><!--/.well -->
	        </div>
        
	        <div class="span9">
	        <form name="registeredForm" action="" method="post"><fieldset><legend>Registered Services</legend>
	        	<div style="height:350px;"><table class="table table-hover">
	        		<thead>
						<tr>
						<th>#</th>
						<th>ServiceName</th>
						<th>ServiceURI</th>
						<th>Documentation</th>
						</tr></thead>
					<tbody>
						<s:iterator id="service" value="%{serviceReferences}">
						<tr>
						<td><input type="radio" name="selectedService" value="<s:property value="#service.getServiceID()"/>"></td>
						<td><s:property value="#service.getServiceName()"/></td>
						<td><s:property value="#service.getURI()"/></td>
						<td><s:property value="#service.getDocumentation()"/></td>
						<td><br></td>
						</tr>
						</s:iterator>
					</tbody></table></div>
				<input type="hidden" name="isPartialReq" value="false">
				<div class="btn-group" style="float: left;">
					<button class="btn"
						onclick="registeredForm.action='remove.action';registeredForm.submit();">Remove Service</button>
			    </div>
			</fieldset></form>
	         <form name="registerForm" action="register.action" method="post">
			  	<fieldset><legend>Add Service</legend>
			  	<div class="span9">
			  		<div class="span6">
		  				<label class="span4">ServiceName</label><input class="span8" type="text" name="serviceName">
		  			</div>
		  			<div class="span6">
		  				<label class="span4">Password</label><input class="span8" type="password" name="servicePwd">
		  			</div>
				  		<label>ServiceURI</label><input type="text" class="input-block-level" name="serviceURI">
				  		<label>Documentation</label><textarea rows="3" class="input-block-level" name="serviceDoc" ></textarea>
				  </div>
				  <div class="span9">
				  	<input type="submit" class="btn btn-warning" value="Add New Service">
				  </div>
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
		$("#nav-servmgt").addClass("active");
	});
</script>
</html>