<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="zh-Ch">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SYSU WfClient: Workdesk</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="css/offcanvas.css" rel="stylesheet">

  </head>

  <body>
	<%@ include file="common/header.jsp" %>
	
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-right">
        
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
          	<a href="index.action" class="list-group-item active">首页</a>
            <a href="workqueue.action" class="list-group-item">我的任务列表</a>
            <a href="participate.action" class="list-group-item">我参与的案例</a>
            <a href="monitor.action" class="list-group-item">我关注的流程</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
        
        </div><!--/span-->

      </div><!--/row-->

      <%@ include file="common/footer.jsp" %>
    </div><!--/.container-->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/offcanvas.js"></script>
    <script type="text/javascript">
    $(function(){
		$("#nav-workdesk").addClass("active");});
	
    </script>
    
  </body>
</html>