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
          	<a href="index.action" class="list-group-item">首页</a>
            <a href="workqueue.action" class="list-group-item">我的任务列表</a>
            <a href="participate.action" class="list-group-item">我参与的案例</a>
            <a href="monitor.action" class="list-group-item active">我关注的流程</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
			<div class="panel-group" id="accordion">
          	
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" id="title1">
			       	关注的流程
			       </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in">
			    	<div class="panel-body">
						<table class="table table-striped table-condensed">
					      <thead>
							<tr>
							<!-- <th>Identifier</th> -->
							<th>SpecName</th>
							<th>Version</th>
							<th>URI</th>
							<th>Documentation</th>
							</tr></thead>
						<tbody id="tbody1">
		
					  	</tbody>
						</table>
					</div>
			    </div>
			  </div>
			  
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" id="title2">
					所有流程
			        </a>
			      </h4>
			    </div>
			    <div id="collapseTwo" class="panel-collapse collapse">
			      <div class="panel-body">
			      	<table class="table table-striped table-condensed">
				      <thead>
						<tr>
						<th>SpecName</th>
						<th>Version</th>
						<!-- <th>Status</th> -->
						<th>URI</th>
						<th>Documentation</th>	
						<th>Actions</th>
						</tr></thead>
					  <tbody id="tbody2">
		
					  </tbody>
					</table>
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
    <script src="js/offcanvas.js"></script>
    <script type="text/javascript">
    $(function(){
		$("#nav-workdesk").addClass("active");
		
		updateTable();
		
	});

	function updateTable() {
	
		$.ajax({
			url		:	"loadSpecsMonitor.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(specsJson) {
				updateTable1(specsJson);
				updateTable2(specsJson);
			}
		});
		
	}
	
	function updateTable1(specsJson) {
		$("#tbody1").empty();
	
		var specs = JSON.parse(specsJson);
		var followed = JSON.parse(specs.followed);
		
		$(followed).each(function() {
			var str = $('<tr/>');
			str.append("<td style='display:none'>"+this.identifier+"</td>");
			str.append("<td>"+this.name+"</td>");
			str.append("<td>"+this.version+"</td>");
			//str.append("<td>"+this.status+"</td>");
			str.append("<td>"+this.uri+"</td>");
			str.append("<td>"+this.documentation+"</td>");

			$("#tbody1").append(str);
		})
	}
	
	function updateTable2(specsJson) {
		$("#tbody2").empty();
		
		var specs = JSON.parse(specsJson);
		var followed = JSON.parse(specs.followed);
		var unfollowed = JSON.parse(specs.unfollowed);
		
		$(followed).each(function() {
			var str = $('<tr/>');
			str.append("<td style='display:none'>"+this.identifier+"</td>");
			str.append("<td>"+this.name+"</td>");
			str.append("<td>"+this.version+"</td>");
			//str.append("<td>"+this.status+"</td>");
			str.append("<td>"+this.uri+"</td>");
			str.append("<td>"+this.documentation+"</td>");
			str.append("<td><button class='btn btn-default btn-xs' onclick='unfollow(\""+this.identifier+"\")'>Unfollow</button></td>");
			
			$("#tbody2").append(str);
		})
		
		$(unfollowed).each(function() {
			var str = $('<tr/>');
			str.append("<td style='display:none'>"+this.identifier+"</td>");
			str.append("<td>"+this.name+"</td>");
			str.append("<td>"+this.version+"</td>");
			//str.append("<td>"+this.status+"</td>");
			str.append("<td>"+this.uri+"</td>");
			str.append("<td>"+this.documentation+"</td>");
			str.append("<td><button class='btn btn-default btn-xs' onclick='follow(\""+this.identifier+"\")'>Follow</button></td>");

			$("#tbody2").append(str);
		})
	}
	
	function follow(identifier) {
		//alert(identifier);
		
		$.ajax({
			url		:	"follow.action",
			data	:	"selectedSpec="+identifier,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});	
	}
	
	function unfollow(identifier) {
		//alert(identifier);
		
		$.ajax({
			url		:	"unfollow.action",
			data	:	"selectedSpec="+identifier,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});	
	}

    </script>
    
  </body>
</html>