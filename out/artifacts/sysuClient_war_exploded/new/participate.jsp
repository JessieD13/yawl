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
            <a href="participate.action" class="list-group-item active">我参与的案例</a>
            <a href="monitor.action" class="list-group-item">我关注的流程</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
        
        	<div class="panel-group" id="accordion">
        		
        		<div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" id="title1">
				       正在参与的
				       </a>
				      </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse in">
				    	<div class="panel-body">
				    		<p>
							<button class="btn btn-sm btn-success" id="testbtn">全部时间范围</button>
							<button class="btn btn-sm btn-default" id="testbtn"  style="background:white" onclick="loadCasesWithinToday()">今天</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadCasesWithinYesterday()">昨天</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadCasesWithinAWeek()">一周以内</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadEarlierCases()">更早</button>

							<button class="btn btn-sm btn-default pull-right" id="completed" style="background:white" onclick="loadCompletedCases()">已经完成的</button>
							</p>

							<hr/>
							
							<table class="table table-striped table-condensed">
						      <thead>
								<tr>
								<th>Spec: Case</th>
								<th>Task</th>
								<th>Item</th>
								<th>Type</th>
								<th>Time</th>
								<th>More Info</th>
								</tr></thead>
							  <tbody id="tbody1">
				
							  </tbody>
							</table><br/><br/><br/>
						</div>
				    </div>
			  	</div>
			  	
        		<div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" id="title2">
				       我发起的/结束的
				       </a>
				      </h4>
				    </div>
				    <div id="collapseTwo" class="panel-collapse collapse">
				    	<div class="panel-body">
				    		<p>
							<button class="btn btn-sm btn-success" id="testbtn">全部时间范围</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadMyCasesWithinToday()">今天</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadMyCasesWithinYesterday()">昨天</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadMyCasesWithinAWeek()">一周以内</button>
							<button class="btn btn-sm btn-default" id="testbtn" style="background:white" onclick="loadMyEarlierCases()">更早</button>
							</p>
							<hr/>
							
							<table class="table table-striped table-condensed">
						      <thead>
								<tr>
								<th>Spec: Case</th>
								<th>Type</th>
								<th>Time</th>
								<th>More Info</th>
								</tr></thead>
							  <tbody id="tbody2">
				
							  </tbody>
							</table><br/><br/><br/>
						</div>
				    </div>
			  	</div>
		  				  
        	</div>

        </div><!--/span-->

      </div><!--/row-->

      <%@ include file="common/footer.jsp" %>
    </div><!--/.container-->
    
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
		
		var completed = "false";
		
		updateTable();
		
		$("#completed").click(function() {
			$(this).toggleClass("btn-default");
			$(this).toggleClass("btn-warning active");
		});
		
	});
	
	function updateTable() {
		$.ajax({
			url		:	"loadCases.action",
			type	:	"post",
			dataType:	"json",
			success	:	updateTable1
		});
			
		$.ajax({
			url		:	"loadMineCases.action",
			type	:	"post",
			dataType:	"json",
			success	:	updateTable2
		});			
	}
	
	function loadCompletedCases() {
	//alert($('#completed').style.background);
		//if($('#completed').)
		if(document.getElementById("completed").style.background == "green"){
			$.ajax({
				url		:	"loadCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	updateTable1
			});
			document.getElementById("completed").style.background = "white";
			completed = "false";
		}
		else if(document.getElementById("completed").style.background == "white"){
			$.ajax({
				url		:	"loadCompletedCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	updateTable1
			});	
			document.getElementById("completed").style.background = "green";
			completed = "true";
		}
	}
	
	function loadCasesWithinToday() {
		var today = formatDate(new Date());
		var date = today.substring(0, today.indexOf(" "));
		if(completed == "true"){
			$.ajax({
				url		:	"loadCompletedCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time.indexOf(date) >= 0){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
		else{
			$.ajax({
				url		:	"loadCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time.indexOf(date) >= 0){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
	}
	
	function loadCasesWithinYesterday() {
		var dt = new Date();
		dt.setDate(dt.getDate() - 1);   //昨天
		var yesterday = formatDate(dt);
		var date = yesterday.substring(0, yesterday.indexOf(" "));
		//alert(date);
		if(completed == "true"){
			$.ajax({
				url		:	"loadCompletedCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time.indexOf(date) >= 0){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
		else{
			$.ajax({
				url		:	"loadCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time.indexOf(date) >= 0){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
	}
	
	function loadCasesWithinAWeek() {
		var dt = new Date();
		dt.setDate(dt.getDate() - 7);   //昨天
		var week = formatDate(dt);
		var date = week.substring(0, week.indexOf(" "));
		//alert(date);
		if(completed == "true"){
			$.ajax({
				url		:	"loadCompletedCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time >= date){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
		else{
			$.ajax({
				url		:	"loadCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time >= date){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
	}
	
	
	function loadEarlierCases(){
		var dt = new Date();
		dt.setDate(dt.getDate() - 7);   //昨天
		var week = formatDate(dt);
		var date = week.substring(0, week.indexOf(" "));
		if(completed == "true"){
			$.ajax({
				url		:	"loadCompletedCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time < date){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
		else{
			$.ajax({
				url		:	"loadCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody1").empty();
								$(cases).each(function() {
									if(this.time < date){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.taskid+"</td>");
										str.append("<td>"+this.itemid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody1").append(str);
									}
								});
							}
			});
		}
	}
	
	function loadMyCasesWithinToday(){
		var today = formatDate(new Date());
		var date = today.substring(0, today.indexOf(" "));
		$.ajax({
			url		:	"loadMineCases.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(casesJson) {
							var cases = JSON.parse(casesJson);
							$("#tbody2").empty();
							$(cases).each(function() {
								if(this.time.indexOf(date) >= 0){
									var str = $('<tr/>');
									str.append("<td>"+this.specname+": "+this.caseid+"</td>");
									str.append("<td>"+this.type+"</td>");
									str.append("<td>"+this.time+"</td>");
									str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
									$("#tbody2").append(str);	
								}
							});
						}
			});
	}
	
	function loadMyCasesWithinYesterday(){
		var dt = new Date();
		dt.setDate(dt.getDate() - 1);   //昨天
		var yesterday = formatDate(dt);
		var date = yesterday.substring(0, yesterday.indexOf(" "));
		$.ajax({
			url		:	"loadMineCases.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(casesJson) {
							var cases = JSON.parse(casesJson);
							$("#tbody2").empty();
							$(cases).each(function() {
								if(this.time.indexOf(date) >= 0){
									var str = $('<tr/>');
									str.append("<td>"+this.specname+": "+this.caseid+"</td>");
									str.append("<td>"+this.type+"</td>");
									str.append("<td>"+this.time+"</td>");
									str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
									$("#tbody2").append(str);	
								}
							});
						}
			});
	}
	
	function loadMyCasesWithinAWeek(){
		var dt = new Date();
		dt.setDate(dt.getDate() - 7); 
		var week = formatDate(dt);
		var date = week.substring(0, week.indexOf(" "));
		$.ajax({
			url		:	"loadMineCases.action",
			type	:	"post",
			dataType:	"json",
			success	:	function(casesJson) {
							var cases = JSON.parse(casesJson);
							$("#tbody2").empty();
							$(cases).each(function() {
								if(this.time >= date){
									var str = $('<tr/>');
									str.append("<td>"+this.specname+": "+this.caseid+"</td>");
									str.append("<td>"+this.type+"</td>");
									str.append("<td>"+this.time+"</td>");
									str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
									$("#tbody2").append(str);	
								}
							});
						}
			});
	}
	
	function loadMyEarlierCases(){
		var dt = new Date();
		dt.setDate(dt.getDate() - 7);   //昨天
		var week = formatDate(dt);
		var date = week.substring(0, week.indexOf(" "));
		$.ajax({
				url		:	"loadMineCases.action",
				type	:	"post",
				dataType:	"json",
				success	:	function(casesJson) {
								var cases = JSON.parse(casesJson);
								//alert(casesJson);
								$("#tbody2").empty();
								$(cases).each(function() {
									if(this.time < date){
										var str = $('<tr/>');
										str.append("<td>"+this.specname+": "+this.caseid+"</td>");
										str.append("<td>"+this.type+"</td>");
										str.append("<td>"+this.time+"</td>");
										str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
										$("#tbody2").append(str);
									}
								});
							}
			});
	}
	
	function updateTable1(casesJson) {
		var cases = JSON.parse(casesJson);
		//alert(casesJson);
		$("#tbody1").empty();
		$(cases).each(function() {
			var str = $('<tr/>');
			str.append("<td>"+this.specname+": "+this.caseid+"</td>");
			str.append("<td>"+this.taskid+"</td>");
			str.append("<td>"+this.itemid+"</td>");
			str.append("<td>"+this.type+"</td>");
			str.append("<td>"+this.time+"</td>");
			str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
			$("#tbody1").append(str);
		})
	}
	
	function updateTable2(mineCasesJson) {
		var cases = JSON.parse(mineCasesJson);
		//alert(mineCasesJson);
		$(cases).each(function() {
			var str = $('<tr/>');
			str.append("<td>"+this.specname+": "+this.caseid+"</td>");
			str.append("<td>"+this.type+"</td>");
			str.append("<td>"+this.time+"</td>");
			str.append("<td><button class='btn btn-default btn-xs' onclick='history(\""+this.caseid+"\")'>view</button></td>");
			$("#tbody2").append(str);
		})
	}
	
	function history(caseid) {
		$.ajax({
			url		:	"loadCaseHistory.action",
			data	:	"caseid="+caseid,
			type	:	"post",
			dataType:	"json",
			success	:	function(caseHistoryJson) {
				var history = JSON.parse(caseHistoryJson);
				
				var fade = $('<div class="modal fade" id="historyModal" tabindex="-1"/>');
				var dialog = $('<div class="modal-dialog"/>');		
				var content = $('<div class="modal-content"/>');
				
				var header = $('<div class="modal-header"/>');
				header.append("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				header.append("<h4 class='modal-title'>History of case <em><strong>"+caseid+"</strong></em></h4>");
				
				var body = $('<div class="modal-body"/>');
				var table = $('<table class="table table-striped table-condensed"/>');
				table.append("<thead><tr><th>Time</th><th>Resource</th><th>Task</th><th>Itemid</th><th>Event</th></tr></thead>");
				var tbody = $('<tbody/>');
				
				$(history).each(function(){
					var tr = $('<tr/>');
					tr.append("<td>"+this.time+"</td>");
					tr.append("<td>"+this.resource+"</td>");
					tr.append("<td>"+this.taskid+"</td>");
					tr.append("<td>"+this.itemid+"</td>");
					tr.append("<td>"+this.type+"</td>");
					tbody.append(tr);
				});
				
				table.append(tbody);
				body.append(table);
				
				var footer = $('<div class="modal-footer"/>');
				footer.append("<button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button>");
				
				content.append(header);
				content.append(body);
				content.append(footer);
				dialog.append(content);
				fade.append(dialog);
				
				$("#modal").empty();
				$("#modal").append(fade);
				
				$('#historyModal').modal('show');
				}
			});

	}
	
	function formatDate(date) {  
	    var y = date.getFullYear();  
	    var m = date.getMonth() + 1;  
	    m = m < 10 ? ('0' + m) : m;  
	    var d = date.getDate();  
	    d = d < 10 ? ('0' + d) : d;  
	    var h = date.getHours(); 
	    h = h < 10 ? ('0' + h) : h;  
	    var minute = date.getMinutes();  
	    minute = minute < 10 ? ('0' + minute) : minute;
	    var second = date.getSeconds();  
	    second = second < 10 ? ('0' + second) : second;
	    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;  
	}
		
    </script>
    
  </body>
</html>