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
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="css/offcanvas.css" rel="stylesheet">

  </head>

  <body>
	<%@ include file="common/header.jsp" %>
	
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-right">
        
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="case.action" class="list-group-item">过程管理</a>
            <a href="#" class="list-group-item">资源管理</a>
            <a href="app.action" class="list-group-item">应用管理</a>
            <a href="service.action" class="list-group-item active">YAWL组件管理</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
        
        	<h4>YAWL引擎已注册的组件/RegisteredServices</h4><hr>
			<table class="table table-striped table-condensed" id="applist">
        		<thead>
					<tr>
					<th>Name</th>
					<th>URI</th>
					<th>Documentation</th>
					<th>Actions</th>
					</tr></thead>
				<tbody>

				</tbody>
			</table><br /><br /><br />
			
			<div class="panel-group" id="accordion">
			
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
			       		注册新的服务
			       </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in">
			    	<div class="panel-body">
			        <form class="form-horizontal" id="registerForm" onsubmit="return false;">
						<div class="form-group">
						  <label class="col-sm-2 control-label">ServiceName</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="serviceName" placeholder="service name">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">Password</label>
						  <div class="col-sm-10">
						    <input type="password" class="form-control" name="servicePwd" placeholder="service passwd">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">ServiceURI</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="serviceURI" placeholder="service uri">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">Documentation</label>
						  <div class="col-sm-10">
						    <textarea rows="3" class="form-control" name="serviceDoc" placeholder="service desc"></textarea>
						  </div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
							<button class="btn btn-default">Cancel</button>
							<button class="btn btn-warning" onclick="register()">Add New Service</button>
							</div>
						</div>
						
					</form>	
						
					</div>
			    </div>
			  </div>			  
			  		  
		  	</div><!-- /panel-group -->

			 
        </div><!--/span-->

      </div><!--/row-->

      <%@ include file="common/footer.jsp" %>
    </div><!--/.container-->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/form2json.js"></script>
    <script src="js/offcanvas.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#nav-mgt").addClass("active");
		
		$.ajax({
			url		:	"loadServs.action",
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
	});

	function updateTable(servsJson) {
		var servs = JSON.parse(servsJson);
		$("tbody").empty();
	
		$(servs).each(function() {
			var str = $('<tr/>');
			str.append("<td>"+this.name+"</td>");
			str.append("<td>"+this.uri+"</td>");
			str.append("<td>"+this.doc+"</td>");
			
			var btns = $('<td/>');
			/* btns.append("<button class='btn btn-default btn-xs'>Edit</button>&nbsp;"); */
			btns.append("<button class='btn btn-default btn-xs' onclick='removeServ(\""+ this.id +"\")'>Remove</button>");

			str.append(btns);
			$("tbody").append(str);
		});
	}
	
	function register() {
    	$.ajax({
			url		:	"register.action",
			data	:	$("#registerForm").serialize(),
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
    }
    
    function removeServ(id) {
    	alert(id);
        $.ajax({
			url		:	"removeServ.action",
			data	:	"selectedSerivce="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
    
    }
	
	</script>
  </body>
</html>