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
    <div class="container-fluid">
    	<div class="row-fluid">
	        <div class="span12"><br>visual process page<br>
				<div style="height: 350px; overflow: scroll;">
				<canvas id="processcanvas" width="800px" height="400px"></canvas>
				</div>
	        </div>
		</div>
		
		<%@ include file="../common/footer.jsp" %>
    </div> <!-- /container -->
</body>

<!-- Placed at the end of the document so the pages load faster -->
<script src="../public/js/jquery.min.js"></script>
<script src="../public/js/jcanvas.min.js"></script>
<script src="../public/js/bootstrap.min.js"></script>
<script src="../public/js/process.visual.js"></script>
<script type="text/javascript">		
	$(function(){
	
		//drawTipsLayer("processcanvas");
		var io = JSON.parse('${ioJson}');
		
		for (var i in io) {
			var details = io[i];
			drawTask("processcanvas", details["x"]*1, details["y"]*1, "lightgreen", i, details);
		};
			
		var task = JSON.parse('${taskJson}');
		
		for (var t in task) {
			var details = task[t];
			drawTask("processcanvas", details["x"]*1, details["y"]*1, "lightblue", t, details);
		};

	});
</script>

</html>