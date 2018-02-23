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

  <body>&nbsp; 
		<%@ include file="../common/header.jsp" %>

    <div class="container-fluid">
    	<div class="row-fluid">
	        <div class="span3">
	        	<%@ include file="../common/nav-sidebar.jsp" %>
	        </div>
        
	        <div class="span9">
	        	<iframe id="content-panel" src="../workqueue/offered.action?isPartialReq=true" frameborder="0" style="height:550px; width:100%;"></iframe>
	        </div>
			
      </div>
		
		<%@ include file="../common/footer.jsp" %>
    </div> <!-- /container -->
</body>
<!-- Placed at the end of the document so the pages load faster -->
<script src="../public/js/jquery.min.js"></script>
<script src="../public/js/bootstrap.min.js"></script>
<script src="../public/js/workdesk.js"></script>
<script type="text/javascript">
	$(function(){
		$("#nav-workdesk").addClass("active");
		$(".nav>li>a").click(function(){
			$(this).parent().siblings().removeClass("active");
			$(this).parent().addClass("active");
		});
		refreshPage();
	});
</script>
</html>