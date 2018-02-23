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
    <link href="css/bootstrap.min.css" rel="stylesheet">

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
            <a href="workqueue.action" class="list-group-item active"><span class="badge">8</span>我的任务列表</a>
            <a href="participate.action" class="list-group-item">我参与的案例</a>
            <a href="monitor.action" class="list-group-item">我关注的流程</a>
          </div>
          <div class="panel panel-default">
			  <div class="panel-heading">工具组</div>
			  <div class="panel-body" id="appbody">
			    <!-- <p>一共xx个应用，点击<a href="#">这里</a>添加应用</p> -->
			  </div>
	          <div class="list-group" id="applist">
	          	<!-- <a href="javascript:void(0)" onclick="call('')" class="list-group-item">app</a> -->
	          </div>
	      </div>
          
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">

          <div class="panel-group" id="accordion">
          	
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" id="title1">
			       
			       </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in">
			    	<div class="panel-body">

						<p>
						<button class="btn btn-sm btn-success" name="priority" id="allbtn" onclick="updateAll('priority')">全部紧急程度</button>
						<button class="btn btn-sm btn-default" name="priority" id="highbtn" onclick="updateTablePriority('High', 'highbtn', 'started')">严重</button>
						<button class="btn btn-sm btn-default" name="priority" id="middlebtn" onclick="updateTablePriority('Middle', 'middlebtn', 'started')">较重</button>
						<button class="btn btn-sm btn-default" name="priority" id="lowbtn" onclick="updateTablePriority('Low', 'lowbtn', 'started')">一般</button>
						</p>
						
						<p>
						<button class="btn btn-sm btn-success" name="difficulty" id="allbtn2" onclick="updateAll('difficulty')">全部任务难度</button>
						<button class="btn btn-sm btn-default" name="difficulty" id="highbtn2" onclick="updateTableDifficulty('High', 'highbtn2', 'started')">复杂</button>
						<button class="btn btn-sm btn-default" name="difficulty" id="middlebtn2" onclick="updateTableDifficulty('Middle', 'middlebtn2', 'started')">正常</button>
						<button class="btn btn-sm btn-default" name="difficulty" id="lowbtn2" onclick="updateTableDifficulty('Low', 'lowbtn2', 'started')">简单</button>
						</p>
						
						<p>
						<button class="btn btn-sm btn-success" name="clientLevel" id="allbtn3" onclick="updateAll('clientLevel')">全部客户等级</button>
						<button class="btn btn-sm btn-default" name="clientLevel" id="highbtn3" onclick="updateTableClientLevel('High', 'highbtn3', 'started')">高级</button>
						<button class="btn btn-sm btn-default" name="clientLevel" id="middlebtn3" onclick="updateTableClientLevel('Middle', 'middlebtn3', 'started')">中级</button>
						<button class="btn btn-sm btn-default" name="clientLevel" id="lowbtn3" onclick="updateTableClientLevel('Low', 'lowbtn3', 'started')">低级</button>
						</p>
						
						<!-- <p>
						<button class="btn btn-sm btn-success" id="testbtn">全部时间范围</button>
						<button class="btn btn-sm btn-default" id="testbtn">今天</button>
						<button class="btn btn-sm btn-default" id="testbtn">昨天</button>
						<button class="btn btn-sm btn-default" id="testbtn">一周以内</button>
						<button class="btn btn-sm btn-default" id="testbtn">更早</button>
						</p>-->
						
						<hr/>
						<button class="btn btn-sm btn-default" id="default">默认处理</button>
						<button class="btn btn-sm btn-success" id="pilebtn" onclick="pile('started')">按Task批量处理</button>
						<!-- <button class="btn btn-sm btn-success" id="chainbtn">按Case链式处理</button> -->
						<hr/>
						
						<table class="table table-striped table-condensed">
					      <thead id="thead1">
							<tr>
							<th>Case: Task</th>
							<th>Specification</th>
							<th>紧急程度</th>
							<th>任务难度</th>
							<th>客户等级</th>
							<th>Status</th>
							<th>Actions</th>
							</tr></thead>
						  <tbody id="tbody1">

						  </tbody>
						</table><br/>
						
						<table><tr>
						<td><form class="form-inline" id="tspForm">	
							<div class="form-group"><input type='text' name='originLatitude' class='form-control input-sm' placeholder='Latitude' value="23.131482"/></div>
							<div class="form-group"><input type='text' name='originLongitude' class='form-control input-sm' placeholder='Longitude' value="113.268771"/></div>
							<div class="form-group"><input type='text' name='originLocation' class='form-control input-sm' value='公园前'/></div>
							<div class="form-group"><input type='text' name='originRegion' class='form-control input-sm' value='广州'/></div>
						</form></td>
						<td><button class="btn btn-sm btn-success" onclick="tsp('started')">路径优化</button></td>
						</tr></table>
						<span class="help-block">出发位置纬度 经度 暂定公园前</span>
											
						<hr/>
						
					</div>
			    </div>
			  </div>

			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" id="title2">

			        </a>
			      </h4>
			    </div>
			    <div id="collapseTwo" class="panel-collapse collapse">
			      <div class="panel-body">
			      	<p>
					<button class="btn btn-sm btn-success" id="alltime">全部时间范围</button>
					<button class="btn btn-sm btn-default" id="today">今天</button>
					<button class="btn btn-sm btn-default" id="yesterday">昨天</button>
					<button class="btn btn-sm btn-default" id="week">一周以内</button>
					<button class="btn btn-sm btn-default" id="before">更早</button>
					</p>
					<hr/>
			      	<table class="table table-striped table-condensed">
				      <thead>
						<tr>
						<th>Case: Task</th>
						<th>Specification</th>
						<th>Status</th>
						<th>Actions</th>
						</tr></thead>
					  <tbody id="tbody2">

					  </tbody>
					</table>
			      </div>
			    </div>
			  </div>
			  			  
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" id="title3">

			        </a>
			      </h4>
			    </div>
			    <div id="collapseThree" class="panel-collapse collapse">
			      <div class="panel-body">
			      	<p>
					<button class="btn btn-sm btn-success" id="alltime">全部时间范围</button>
					<button class="btn btn-sm btn-default" id="today">今天</button>
					<button class="btn btn-sm btn-default" id="yesterday">昨天</button>
					<button class="btn btn-sm btn-default" id="week">一周以内</button>
					<button class="btn btn-sm btn-default" id="before">更早</button>
					</p>
					<hr/>
			      	<table class="table table-striped table-condensed">
				      <thead>
						<tr>
						<th>Case: TasK</th>
						<th>Specification</th>
						<th>Status</th>
						<th>Actions</th>
						</tr></thead>
					  <tbody id="tbody3">

					  </tbody>
					</table>
			      </div>
			    </div>
			  </div>
			  			  	  
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" id="title4">

			        </a>
			      </h4>
			    </div>
			    <div id="collapseFour" class="panel-collapse collapse">
			      <div class="panel-body">
			      	<p>
					<button class="btn btn-sm btn-success" id="alltime">全部时间范围</button>
					<button class="btn btn-sm btn-default" id="today">今天</button>
					<button class="btn btn-sm btn-default" id="yesterday">昨天</button>
					<button class="btn btn-sm btn-default" id="week">一周以内</button>
					<button class="btn btn-sm btn-default" id="before">更早</button>
					</p>
					<hr/>
			      	<table class="table table-striped table-condensed">
				      <thead>
						<tr>
						<th>Case: Task</th>
						<th>Specification</th>
						<th>Status</th>
						<th>Actions</th>
						</tr></thead>
					  <tbody id="tbody4">

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
    
    <!-- Modal -->
	<div id="modal"></div>

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
		updateTableApp();
		
		$("#default").click(function() {
			$.ajax({
				url		:	"loadStartedQueue.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(itemsJson) {
					updateTableStarted(itemsJson);
				}
			});
		});
		
	});
	
	function updateTable() {
	
		$.ajax({
			url		:	"loadStartedQueue.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(itemsJson) {
				updateTableStarted(itemsJson);
			}
		});
		
		$.ajax({
			url		:	"loadAllocatedQueue.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(itemsJson) {
				updateTableAllocated(itemsJson);
			}
		});
		
		$.ajax({
			url		:	"loadOfferedQueue.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(itemsJson) {
				updateTableOffered(itemsJson);
			}
		});
		
		$.ajax({
			url		:	"loadSuspendedQueue.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(itemsJson) {
				updateTableSuspended(itemsJson);
			}
		});	
	}
	
	function updateTableStarted(itemsJson) {

		var items = JSON.parse(itemsJson);

		$("#tbody1").empty();
		$("#title1").empty();
		$("#title1").append("Started: 有<strong>"+items.length+"</strong>个未完成的任务");

		$(items).each(function() {
			var str = $('<tr/>');
			/* str.append("<td>"+this.id+"</td>"); */
			str.append("<td>"+this.case+": "+this.task +"&nbsp;<button class='btn btn-default btn-xs' onclick='info(\""+this.id+"\")'>more</button></td>");			
			str.append("<td>"+this.spec+"</td>");
						
			var priority = $('<td/>');
			var btngroup = $('<div class="btn-group"/>');
			btngroup.append("<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown'> "+ this.priority +" <span class='caret'></span></button>");				
			var ul = $('<ul class="dropdown-menu"/>');
			ul.append("<li><a onclick='addPriority(\""+this.case+"\", \"High\", \"started\")'>High</a></li>");
			ul.append("<li><a onclick='addPriority(\""+this.case+"\", \"Middle\", \"started\")'>Middle</a></li>");
			ul.append("<li><a onclick='addPriority(\""+this.case+"\", \"Low\", \"started\")'>Low</a></li>");
			btngroup.append(ul);
			priority.append(btngroup);
			str.append(priority);
			
			var difficulty = $('<td/>');
			var btngroup2 = $('<div class="btn-group"/>');
			btngroup2.append("<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown'> "+ this.difficulty +" <span class='caret'></span></button>");
			var ul2 = $('<ul class="dropdown-menu"/>');
			ul2.append("<li><a onclick='addDifficulty(\""+this.case+"\", \"High\", \"started\")'>High</a></li>");
			ul2.append("<li><a onclick='addDifficulty(\""+this.case+"\", \"Middle\", \"started\")'>Middle</a></li>");
			ul2.append("<li><a onclick='addDifficulty(\""+this.case+"\", \"Low\", \"started\")'>Low</a></li>");
			btngroup2.append(ul2);
			difficulty.append(btngroup2);
			str.append(difficulty);
			
			var clientLevel = $('<td/>');
			var btngroup3 = $('<div class="btn-group"/>');
			btngroup3.append("<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown'> "+ this.clientLevel +" <span class='caret'></span></button>");
			var ul3 = $('<ul class="dropdown-menu"/>');
			ul3.append("<li><a onclick='addClientLevel(\""+this.case+"\", \"High\", \"started\")'>High</a></li>");
			ul3.append("<li><a onclick='addClientLevel(\""+this.case+"\", \"Middle\", \"started\")'>Middle</a></li>");
			ul3.append("<li><a onclick='addClientLevel(\""+this.case+"\", \"Low\", \"started\")'>Low</a></li>");
			btngroup3.append(ul3);
			clientLevel.append(btngroup3);
			str.append(clientLevel);
			
			str.append("<td>"+this.status+"</td>");
			
			var btns = $('<td/>');
			btns.append("<button class='btn btn-default btn-xs' onclick='view(\""+this.id+"\")'>编辑查看</button>&nbsp;");
			btns.append("<button class='btn btn-default btn-xs' onclick='reallocate(\""+this.id+"\")'>委派</button>&nbsp;");	
			btns.append("<button class='btn btn-default btn-xs' onclick='suspend(\""+this.id+"\")'>挂起</button>&nbsp;");
			
			if(this.isEdited == 'true') {
				btns.append("<button class='btn btn-default btn-xs'>完成</button>");
			}
			
			if(this.isEdited == 'false') {
				btns.append("<button class='btn btn-default btn-xs' disabled='disabled'>完成</button>");
			}
			str.append(btns);		
			
			$("#tbody1").append(str);
		});
		
	}
	
	function updateTableAllocated(itemsJson) {
		var items = JSON.parse(itemsJson);
		
		$("#tbody2").empty();
		$("#title2").empty();
		$("#title2").append("Allocated: 有<strong>"+items.length+"</strong>个待办任务");

		$(items).each(function() {
			var str = $('<tr/>');
			/* str.append("<td>"+this.id+"</td>"); */
			str.append("<td>"+this.case+": "+this.task+"</td>");
			str.append("<td>"+this.spec+"</td>");
			
			str.append("<td>"+this.status+"</td>");

			var btns = $('<td/>');
			btns.append("<button class='btn btn-default btn-xs' onclick='start(\""+this.id+"\")'>开始</button>&nbsp;");
			btns.append("<button class='btn btn-default btn-xs' onclick='deallocate(\""+this.id+"\")'>拒绝</button>&nbsp;");
			btns.append("<button class='btn btn-default btn-xs' onclick='delegate(\""+this.id+"\")'>委派</button>&nbsp;");
			/* btns.append("<button class='btn btn-default btn-xs' onclick='skip(\""+this.id+"\")'>跳过</button>&nbsp;"); */
			/* btns.append("<button class='btn btn-default btn-xs' onclick='pile(\""+this.id+"\")'>打包</button>"); */			
			str.append(btns);

			$("#tbody2").append(str);
		});
		
	}
		
	function updateTableOffered(itemsJson) {
		var items = JSON.parse(itemsJson);
	
		$("#tbody3").empty();
		$("#title3").empty();

		$("#title3").append("Offered: 有<strong>"+items.length+"</strong>个待接收任务");
		
		$(items).each(function() {
			var str = $('<tr/>');
			/* str.append("<td>"+this.id+"</td>"); */
			str.append("<td>"+this.case+": "+this.task+"</td>");
			str.append("<td>"+this.spec+"</td>");
			str.append("<td>"+this.status+"</td>");
			
			var btns = $('<td/>');
			btns.append("<button class='btn btn-default btn-xs' onclick='accept(\""+this.id+"\")'>接收</button>&nbsp;");
			btns.append("<button class='btn btn-default btn-xs' onclick='acceptstart(\""+this.id+"\")'>直接执行</button>&nbsp;");
			btns.append("<button class='btn btn-default btn-xs' onclick='chain(\""+this.id+"\")'>Chain</button>");

			str.append(btns);

			$("#tbody3").append(str);
		});
		
	}
	
	function updateTableSuspended(itemsJson) {
		var items = JSON.parse(itemsJson);
	
		$("#tbody4").empty();
		$("#title4").empty();
		$("#title4").append("Suspended: 有<strong>"+items.length+"</strong>个挂起的任务");
		
		$(items).each(function() {
			var str = $('<tr/>');
			/* str.append("<td>"+this.id+"</td>"); */
			str.append("<td>"+this.case+":"+this.task+"</td>");	
			str.append("<td>"+this.spec+"</td>");
			str.append("<td> Suspended </td>");
			
			var btns = $('<td/>');
			btns.append("<button class='btn btn-default btn-xs' onclick='unsuspend(\""+this.id+"\")'>解除挂起</button>&nbsp;");

			str.append(btns);
			
			$("#tbody4").append(str);
		});
		
	}
	
	function info(id) {
		$.ajax({
			url		:	"readInfo.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	function(infosJson) {
				var infos = JSON.parse(infosJson);
						
				var fade = $('<div class="modal fade" id="info" tabindex="-1"/>');
				var dialog = $('<div class="modal-dialog"/>');		
				var content = $('<div class="modal-content"/>');
				
				var header = $('<div class="modal-header"/>');
				header.append("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				header.append("<h4 class='modal-title'>Edit Workitem Info <em><strong>"+id+"</strong></em></h4>");
				
				var body = $('<div class="modal-body"/>');
				var form = $('<form class="form-horizontal" id="infoForm"/>');
				
				var location = $('<div class="form-group"/>');
				location.append("<label class='col-sm-2 control-label'>Location</label>");
				location.append("<div class='col-sm-10'><input type='text' name='location' class='form-control' value='"+ infos.location +"'/></div>");
				form.append(location);
								
				var region = $('<div class="form-group"/>');
				region.append("<label class='col-sm-2 control-label'>Region</label>");
				region.append("<div class='col-sm-10'><input type='text' name='region' class='form-control' value='"+ infos.region +"'/></div>");
				form.append(region);
				
				var latitude = $('<div class="form-group"/>');
				latitude.append("<label class='col-sm-2 control-label'>latitude</label>");
				latitude.append("<div class='col-sm-10'><input type='text' name='latitude' class='form-control' value='"+ infos.latitude +"'/></div>");
				form.append(latitude);
								
				var longitude = $('<div class="form-group"/>');
				longitude.append("<label class='col-sm-2 control-label'>longitude</label>");
				longitude.append("<div class='col-sm-10'><input type='text' name='longitude' class='form-control' value='"+ infos.longitude +"'/></div>");
				form.append(longitude);

				var appointment = $('<div class="form-group"/>');
				appointment.append("<label class='col-sm-2 control-label'>Appointment</label>");
				appointment.append("<div class='col-sm-10'><input type='text' name='appointment' class='form-control' value='"+ infos.appointment +"'/></div>");
				form.append(appointment);
				
				var consuming = $('<div class="form-group"/>');
				consuming.append("<label class='col-sm-2 control-label'>Consuming</label>");
				consuming.append("<div class='col-sm-10'><input type='text' name='consuming' class='form-control' value='"+ infos.consuming +"'/></div>");
				form.append(consuming);
						
				var delayFactor = $('<div class="form-group"/>');
				delayFactor.append("<label class='col-sm-2 control-label'>DelayFactor</label>");
				delayFactor.append("<div class='col-sm-10'><input type='text' name='delayFactor' class='form-control' value='"+ infos.delayFactor +"'/></div>");
				form.append(delayFactor);	
								
				body.append(form);
				
				var footer = $('<div class="modal-footer"/>');
				footer.append("<button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button>");
				footer.append("<button type='button' class='btn btn-primary' onclick='saveinfo(\""+id+"\")'>Save</button>");
				
				content.append(header);
				content.append(body);
				content.append(footer);
				dialog.append(content);
				fade.append(dialog);
				
				$("#modal").empty();
				$("#modal").append(fade);
				
				$("#info").modal("show");
			}
		});
	}
	
	function saveinfo(id) {
		$.ajax({
			url		:	"saveInfo.action",
			data	:	$("#infoForm").serialize() + "&selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				$("#info").modal("hide");
				updateTableStarted();
			}
		});
	}
	
	function tsp(queueType) {
		var fade = $('<div class="modal fade" id="tsp" tabindex="-1"/>');
		var dialog = $('<div class="modal-dialog"/>');		
		var content = $('<div class="modal-content"/>');
		
		var header = $('<div class="modal-header"/>');
		header.append("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
		header.append("<h4 class='modal-title'>Route</h4>");
		
		var body = $('<div class="modal-body" id="tspBody"/>');
								
		body.append("访问百度LBS，计算中……");
		
		var footer = $('<div class="modal-footer" id="tspFooter"/>');
		
		content.append(header);
		content.append(body);
		content.append(footer);
		dialog.append(content);
		fade.append(dialog);
		
		$("#modal").empty();
		$("#modal").append(fade);
		
		$("#tsp").modal("show");
		
		$.ajax({
			url		:	"tsp.action",
			data	:	$("#tspForm").serialize() + "&queueType="+queueType,
			type	:	"post",
			dataType:	"json",
			success	:	function(optimalJson) {
				$("#tspBody").empty();
				$("#tspBody").append(optimalJson);
				$("#tspFooter").append("<button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button>");
			}
		});
	}
	
	function accept(id) {
	
		$.ajax({
			url		:	"accept.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
		
	}
	
	function start(id) {
	
		$.ajax({
			url		:	"start.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
		
	}
	
	function acceptstart(id) {
			
		$.ajax({
			url		:	"acceptstart.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
	}
	
	function deallocate(id) {
		$.ajax({
			url		:	"deallocate.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
	}
	

	function skip(id) {
		$.ajax({
			url		:	"skip.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
	}

	function suspend(id) {
		$.ajax({
			url		:	"suspend.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});
	}
	
	function unsuspend(id) {
		$.ajax({
			url		:	"unsuspend.action",
			data	:	"selectedItem="+id,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});	
	}

	function view(itemid) {
		$.ajax({
			url		:	"view.action",
			data	:	"selectedItem="+itemid,
			type	:	"post",
			dataType:	"json",
			success	:	function(paramsJson) {
				var params = JSON.parse(paramsJson);
				
				var fade = $('<div class="modal fade" id="view" tabindex="-1"/>');
				var dialog = $('<div class="modal-dialog"/>');		
				var content = $('<div class="modal-content"/>');
				
				var header = $('<div class="modal-header"/>');
				header.append("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				header.append("<h4 class='modal-title'>Edit workitem <em><strong>"+itemid+"</strong></em></h4>");
				
				var body = $('<div class="modal-body"/>');
				var form = $('<form class="form-horizontal" id="viewForm"/>');
				
				for (var key in params) {
				
					var formgroup = $('<div class="form-group"/>');
					
					if(key == 'customForm') {
						formgroup.append("<label class='col-sm-2 control-label'>"+ key +"</label>");
						formgroup.append("<div class='col-sm-10'><button type='button' class='btn btn-primary' onclick='window.open(\"http://www.g.cn\")'>跳转到</button></div>");
					
					}	else {
						
						formgroup.append("<label class='col-sm-2 control-label'>"+ key +"</label>");
						
						var ss = params[key].split(":");
						var input = $('<div class="col-sm-10"/>');
						
						if(ss[1] == null){
							input.append("<input type='text' name='wirMap."+ key +"' class='form-control'>");
						} else input.append("<input type='text' name='wirMap."+ key +"' class='form-control' value='"+ss[1]+"'>");
						
						input.append("<span class='help-block'>DataType: "+ss[0]+"</span>");
						
						formgroup.append(input);
					}
					
					form.append(formgroup);

				}
								
				body.append(form);
				
				var footer = $('<div class="modal-footer"/>');
				footer.append("<button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button>");
				footer.append("<button type='button' class='btn btn-primary' onclick='save(\""+itemid+"\")'>Save</button>");
				footer.append("<button type='button' class='btn btn-primary' onclick='saveandcomplete(\""+itemid+"\")'>Complete</button>");
				
				content.append(header);
				content.append(body);
				content.append(footer);
				dialog.append(content);
				fade.append(dialog);
				
				$("#modal").empty();
				$("#modal").append(fade);
				
				$("#view").modal("show");
			}
		});
	}
	
	function save(itemid) {

		$.ajax({
			url		:	"save.action",
			data	:	$("#viewForm").serialize() + "&selectedItem="+itemid,
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				$("#view").modal("hide");
				updateTable();
			}
		});	

	}
	
	function complete(itemid) {
		$.ajax({
			url		:	"complete.action",
			data	:	"selectedItem="+itemid,
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				$("#view").modal("hide");
				updateTable();
			}
		});	
	}
	
	function saveandcomplete(itemid) {
		save(itemid);
		complete(itemid);
	}
	
/* 	function pile(itemid) {
		$.ajax({
			url		:	"pile.action",
			data	:	"selectedItem="+itemid,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable
		});	
	}
	
	function chain(id) {
		alert(id);
	} */
	
	function pile(queueType) {
		$.ajax({
			url		:	"pile.action",
			data	:	"queueType="+queueType,
			type	:	"post",
			dataType:	"json",
			success	:	function(itemsJson) {
				var items = JSON.parse(itemsJson);
				
				$("#thead1").empty();
				$("#tbody1").empty();
				
				var tr = $('<tr/>');
				tr.append("<th>Specification: Task</th>");
				tr.append("<th>Items: Status</th>");
				tr.append("<th>Actions</th>");
				$("#thead1").append(tr);
				
				var str = $('<tr/>');
				var ExecutingCount = 0;
				var EnabledCount = 0;
				var EditedCount = 0;
				for (var key in items) {
					str.append("<td>" + key + "</td>");
					
					var td = $('<td/>');
					for(var val in items[key]) {
						td.append("<p>"+items[key][val] + "</p>");
					}
					str.append(td);
					
					var ss = items[key][0].split(";");
					if(ss[1] == 'Executing'){ ExecutingCount++; }
					if(ss[1] == 'Enabled'){ EnabledCount++; }
					if(ss[2] == 'Edited'){ EditedCount++; }

				}
				
				
				var btns = $('<td/>');
				btns.append("<button class='btn btn-default btn-xs'>编辑查看</button>&nbsp;");
				btns.append("<button class='btn btn-default btn-xs'>委派</button>&nbsp;");	
				btns.append("<button class='btn btn-default btn-xs'>挂起</button>&nbsp;");
				
				if(ExecutingCount == items.length) {
					btns.append("<button class='btn btn-default btn-xs'>完成</button>");
				} else {
					btns.append("<button class='btn btn-default btn-xs' disabled='disabled'>完成</button>");
				}
				
				str.append(btns);
				
				$("#tbody1").append(str);
			}
		});	
	}
	
	
	function delegate(itemid) {
		reallocate(itemid);
	}
	
	function reallocate(itemid) {
	
		var fade = $('<div class="modal fade" id="reallocateTo" tabindex="-1"/>');
		var dialog = $('<div class="modal-dialog"/>');		
		var content = $('<div class="modal-content"/>');
		
		var header = $('<div class="modal-header"/>');
		header.append("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
		header.append("<h4 class='modal-title'>Reallocate workitem <em><strong>"+itemid+"</strong></em> to:</h4>");
		
		var body = $('<div class="modal-body"/>');
		var form = $('<form class="form-horizontal" id="reallocateForm"/>');
		var formgroup = $('<div class="form-group"/>');
		
		formgroup.append("<label class='col-sm-2 control-label'>User</label>");
		formgroup.append("<div class='col-sm-10'><input type='text' name='useridTo' class='form-control'></div>");

		form.append(formgroup);			
		body.append(form);
		
		var footer = $('<div class="modal-footer"/>');
		footer.append("<button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button>");
		footer.append("<button type='button' class='btn btn-primary' onclick='reallocateItem(\""+itemid+"\")'>OK</button>");
		
		content.append(header);
		content.append(body);
		content.append(footer);
		dialog.append(content);
		fade.append(dialog);
		
		$("#modal").empty();
		$("#modal").append(fade);
		
		$('#reallocateTo').modal('show');
	}
	
	function reallocateItem(itemid) {
		$.ajax({
			url		:	"reallocate.action",
			data	:	$("#reallocateForm").serialize() + "&selectedItem="+itemid,
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				$("#reallocateTo").modal("hide");
				updateTable();
			}
		});	
	
	}
	
	function addPriority(caseid, priority, queueType) {
		$.ajax({
			url		:	"addPriority.action",
			data	:	"selectedCase="+caseid+"&priority="+priority,
			type	:	"post",
			dataType:	"json",
			success	:function(){
				if( queueType == 'started'){
					$.ajax({
						url		:	"loadStartedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableStarted(itemsJson); }
					});
				}
				
				if( queueType == 'allocated'){
					$.ajax({
						url		:	"loadAllocatedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableAllocated(itemsJson); }
					});
				}
				
				if( queueType == 'offered'){
					$.ajax({
						url		:	"loadOfferedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableOffered(itemsJson); }
					});
				}
				
				if( queueType == 'suspended'){
					$.ajax({
						url		:	"loadSuspendedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableSuspended(itemsJson);}
					});
				}
			}
		});
	}
	
	function addDifficulty(caseid, difficulty, queueType) {
		$.ajax({
			url		:	"addDifficulty.action",
			data	:	"selectedCase="+caseid+"&difficulty="+difficulty,
			type	:	"post",
			dataType:	"json",
			success	:function(){
				if( queueType == 'started'){
					$.ajax({
						url		:	"loadStartedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableStarted(itemsJson); }
					});
				}
				
				if( queueType == 'allocated'){
					$.ajax({
						url		:	"loadAllocatedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableAllocated(itemsJson); }
					});
				}
				
				if( queueType == 'offered'){
					$.ajax({
						url		:	"loadOfferedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableOffered(itemsJson); }
					});
				}
				
				if( queueType == 'suspended'){
					$.ajax({
						url		:	"loadSuspendedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableSuspended(itemsJson);}
					});
				}
			}
		});
	}
	
	function addClientLevel(caseid, clientLevel, queueType) {
		$.ajax({
			url		:	"addClientLevel.action",
			data	:	"selectedCase="+caseid+"&clientLevel="+clientLevel,
			type	:	"post",
			dataType:	"json",
			success	:function(){
				if( queueType == 'started'){
					$.ajax({
						url		:	"loadStartedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableStarted(itemsJson); }
					});
				}
				
				if( queueType == 'allocated'){
					$.ajax({
						url		:	"loadAllocatedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableAllocated(itemsJson); }
					});
				}
				
				if( queueType == 'offered'){
					$.ajax({
						url		:	"loadOfferedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableOffered(itemsJson); }
					});
				}
				
				if( queueType == 'suspended'){
					$.ajax({
						url		:	"loadSuspendedQueue.action",
						type	:	"post",
						dataType:	"json",
						success	:	function(itemsJson) { updateTableSuspended(itemsJson);}
					});
				}
			}
		});
	}
	
	function updateAll(type){
		if(type=='priority'){
			$('button[name="priority"]').removeClass("btn-warning");
			$('button[name="priority"]').addClass("btn-default");					
			$("#allbtn").removeClass("btn-default");
			$("#allbtn").addClass("btn-success");
		}
		if(type=='difficulty'){
			$('button[name="difficulty"]').removeClass("btn-warning");
			$('button[name="difficulty"]').addClass("btn-default");
			$("#allbtn2").removeClass("btn-default");
			$("#allbtn2").addClass("btn-success");
		}
		if(type=='clientLevel'){
			$('button[name="clientLevel"]').removeClass("btn-warning");
			$('button[name="clientLevel"]').addClass("btn-default");
			$("#allbtn3").removeClass("btn-default");
			$("#allbtn3").addClass("btn-success");
		}
		
		$.ajax({
			url		:	"loadStartedQueue.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(itemsJson) {
				updateTableStarted(itemsJson);
			}
		});
	}
		
	function updateTablePriority(value, btnid, queueType) {
		$('button[name="priority"]').removeClass("btn-success");
		$('button[name="priority"]').removeClass("btn-warning");
		$('button[name="priority"]').addClass("btn-default");
		$("#"+btnid).addClass("btn-warning");
		$("#"+btnid).removeClass("btn-default");
		
		if(queueType=='started'){
			$.ajax({
				url		:	"loadPriorityQueue.action",
				type	:	"post",
				data	:	"priority=" + value + "&queueType=" + queueType,
				dataType:	"json",
				success	:	updateTableStarted
			});	
		}
	
	}

	function updateTableDifficulty(value, btnid, queueType) {
		$('button[name="difficulty"]').removeClass("btn-success");
		$('button[name="difficulty"]').removeClass("btn-warning");
		$('button[name="difficulty"]').addClass("btn-default");
		$("#"+btnid).addClass("btn-warning");
		$("#"+btnid).removeClass("btn-default");
		
		if(queueType=='started'){
			$.ajax({
				url		:	"loadDifficultyQueue.action",
				type	:	"post",
				data	:	"difficulty=" + value + "&queueType=" + queueType,
				dataType:	"json",
				success	:	updateTableStarted
			});	
		}
	
	}
	
	function updateTableClientLevel(value, btnid, queueType) {
		$('button[name="clientLevel"]').removeClass("btn-success");
		$('button[name="clientLevel"]').removeClass("btn-warning");
		$('button[name="clientLevel"]').addClass("btn-default");
		$("#"+btnid).addClass("btn-warning");
		$("#"+btnid).removeClass("btn-default");
		
		if(queueType=='started'){
			$.ajax({
				url		:	"loadClientLevelQueue.action",
				type	:	"post",
				data	:	"clientLevel=" + value + "&queueType=" + queueType,
				dataType:	"json",
				success	:	updateTableStarted
			});	
		}
	
	}
		
	function updateTableApp() {
		$.ajax({
			url		:	"loadAvailableApps.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(appsJson) {
				$("#appbody").empty();
				$("#applist").empty();
				
				var apps = JSON.parse(appsJson);
				$("#appbody").append("<p>一共"+ apps.length +"个应用，点击<a href='app.action'>这里</a>添加应用</p>");
				$(apps).each(function() {
					$("#applist").append("<a href='javascript:void(0)' onclick='call(\""+ this.appid+"\")' class='list-group-item'>"+ this.appName +"</a>");
				});
			}
		});
	}
	
	function call(appid) {
		$.ajax({
			url		:	"callApp.action",
			data	:	"appid=" + appid,
			type	:	"post",
			dataType:	"json",
			success	:	function(appJson) {
				var app = JSON.parse(appJson);
				//alert(app.userid);
				var fade = $('<div class="modal fade" id="app" tabindex="-1"/>');
				var dialog = $('<div class="modal-dialog"/>');		
				var content = $('<div class="modal-content"/>');
				
				var header = $('<div class="modal-header"/>');
				header.append("<button class='close' data-dismiss='modal'>&times;</button>");
				header.append("<h4 class='modal-title'>"+ app.name +"</h4>");
				var body = $('<div class="modal-body" id="callBody"/>');
				body.append("<label class='col-sm-2 control-label'>应用描述</label>")
				body.append("<div class='col-sm-10'><p>"+app.desc+"</p></div>");
				var footer = $('<div class="modal-footer"/>');
				footer.append("<button class='btn btn-default' data-dismiss='modal'>Close</button>");
				
				var serviceaddress = "";
				if(app.query != "null"){
					if(app.url.lastIndexOf("/") == app.url.length - 1){
						serviceaddress = app.url + app.query;
					}else{
						serviceaddress = app.url + "/" + app.query;
					}
				}else{
					serviceaddress = app.url;
				}
				
				//if(app.type == 'URL'){
				if(!app.hasOwnProperty("params") && !app.hasOwnProperty("vars") && !app.hasOwnProperty("subparams")){
					var serviceurl = "http://localhost:8020/SSH_Prototype_J2EE_5.0/accessService.jsp?userid="+app.userid+"&serviceid="+appid+"&serviceaddress="+serviceaddress;
				
					//alert(serviceurl);
					footer.append("<button class='btn btn-primary' onClick='window.open(\""+ serviceurl +"\")'>点击调用</button>");			
				
					//footer.append("<button class='btn btn-primary' onClick='window.open(\""+ app.url +"\")'>点击查看</button>");			
				
				} 
				else {
					var form = $('<form class="form-horizontal" id="callForm"/>');
					var formgroup = $('<div class="form-group"/>');
					if(app.hasOwnProperty("params")){
						var params = JSON.parse(app.params);
						for (var key in params) {
							formgroup.append("<label class='col-sm-2 control-label'>"+ key +"</label>");
							formgroup.append("<div class='col-sm-10'><input type='text' name='"+ key +"' class='form-control'><span class='help-block'>ParamType: "+ params[key] +"</span></div>");
						}
					}
					if(app.hasOwnProperty("vars")){
						var vars = JSON.parse(app.vars);
						for (var key in vars) {
							formgroup.append("<label class='col-sm-2 control-label'>"+ key +"</label>");
							formgroup.append("<div class='col-sm-10'><input type='text' name='"+ key +"' class='form-control'><span class='help-block'>VarDesc: "+ vars[key] +"</span></div>");
	
						}
					}
					if(app.hasOwnProperty("subparams")){
						var subparams = JSON.parse(app.subparams);
						for (var key in subparams) {
							formgroup.append("<label class='col-sm-2 control-label'>"+ key +"</label>");
							formgroup.append("<div class='col-sm-10'><input type='text' name='"+ key +"' class='form-control'><span class='help-block'>ParamType: "+ subparams[key] +"</span></div>");
						}
					}
					form.append(formgroup);
					
					body.append(form);
					
					footer.append("<button class='btn btn-primary' onclick='callWebServ(\""+ appid +"\", \""+serviceaddress+"\")'>点击调用</button>");

					
				}
								
				content.append(header);
				content.append(body);
				content.append(footer);
				dialog.append(content);
				fade.append(dialog);
				
				$("#modal").empty();
				$("#modal").append(fade);
				
				$("#app").modal("show");
			
			}
		});

	}
	
	function callWebServ(appid, appurl){
		//alert($("#callForm").serialize());
		$.ajax({
			url		:	"callWebServ.action",
			//data	:	$("#callForm").serialize(),
			data	:	{"params": $("#callForm").serialize()},
			type	:	"post",
			//dataType:	"json",
			success	:	function(resultJson) {
				var result = JSON.parse(resultJson);
				//alert(result.userid+"  "+appid+"   "+appurl+"   "+result.params);
				var accessurl = "http://localhost:8020/SSH_Prototype_J2EE_5.0/accessService.jsp?userid="+result.userid+"&serviceid="+appid+"&serviceaddress="+appurl+"?"+result.params;
				//alert(accessurl);
				window.open(accessurl);
				
				//$("#callBody").empty();
				//$("#callBody").append("<p>"+ result.result +"</p>");
				
			}
		});
	}

    </script>
  </body>
</html>