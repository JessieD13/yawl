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

        <!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
    
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="css/offcanvas.css" rel="stylesheet">

<!-- jQuery -->

  </head>

  <body>
	<%@ include file="common/header.jsp" %>
	
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-right">
        
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="case.action" class="list-group-item active">过程管理</a>
            <a href="#" class="list-group-item">资源管理</a>
            <a href="app.action" class="list-group-item">应用管理</a>
            <a href="service.action" class="list-group-item">YAWL组件管理</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
        
        	<h4>All Specifications</h4><hr>
        	<table id="allSpec" class="table table-striped table-condensed">
        		<thead>
					<tr>
					<th>Id</th>
					<th>Identifier</th>
					<th>Name</th>
					<th>Uri</th>
					<th>Version</th>
					<th>Documentation</th>
					<th>Available</th>
					</tr></thead>
			</table><br /><br /><br />
        
        
        
        	<h4>Loaded Specifications</h4><hr>
        	<table class="table table-striped table-condensed">
        		<thead>
					<tr>
					<th>Identifier</th>
					<th>Name</th>
					<th>Uri</th>
					<th>Version</th>
					<th>Documentation</th>
					<th>Actions</th>
					</tr></thead>
				<tbody id="tbody1">

				</tbody>
			</table><br /><br /><br />
									
			<form name="uploadForm" action="upload.action" method="post" enctype="multipart/form-data">
				<div class="row">
					<div class="col-xs-6"><input type="file" class="btn btn-default" name="ywl"></div>
				  	<div class="col-xs-6"><input type="submit" class="btn btn-default" value="Upload"></div>
				</div>
			</form><br /><br /><br />
			
			<div class="panel-group" id="accordion">
			
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
			       		Running Cases
			       </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in">
			    	<div class="panel-body">
					<table class="table table-striped table-condensed" id="applist">
		        		<thead>
							<tr>
							<th>case</th>
							<th>spec name</th>
							<th>spec version</th>
							<th>Action</th>
							</tr></thead>
						<tbody id="tbody2">
		
						</tbody>
					</table><br /><br /><br />
						
					</div>
			    </div>
			  </div>
			  		  
		  </div><!-- /panel-group -->
			
        </div><!--/span-->

      </div><!--/row-->

		<div id="modal"></div>

      <%@ include file="common/footer.jsp" %>
    </div><!--/.container-->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.dataTables.js"></script>
    <script src="js/offcanvas.js"></script>
	<script type="text/javascript"> -->
	
	<script src="js/jquery.min.js"></script>
      <script src="js/jquery.dataTables.js"></script>
     <script src="js/bootstrap.min.js"></script>
    <script src="js/offcanvas.js"></script>
    <script src="js/form2json.js"></script>
    <script type="text/javascript">
	$(function(){
		$("#nav-mgt").addClass("active");
		
		updateSpecQueuee();
		
		$.ajax({
			url		:	"getMySpec.action",
			type	:	"post",
			dataType:	"json",
			success	:	updateTable1
		});
		
		$.ajax({
			url		:	"loadRunningCases.action",
			type	:	"post",
			dataType:	"json",
			success	:	updateTable2
		});
	});
	
	
	function updateSpecQueuee() {
	
		$.ajax({
			type	:	"post",
			url		:	"getAllSpec.action",
			dataType:	"json",
			async   :	false, 
			success	:	function(appsJson) {
				 specs = JSON.parse(appsJson);
				 //alert(apps);
					$("#allSpec").DataTable( {
				       data: specs,
				     "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 'unsortable' ] }],
				    columns: [
				        { data: 'appid' },
				        { data: 'identifier' },
				        { data: 'name' },
				        { data: 'uri' },
				        { data: 'version' },
				        { data: 'documentation' },
				        { data: 'available'}
				    ],
				    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
							/* Append the grade to the default row class name */
							var s=aData.available;
							if ( s=="false")
							{
							
								$('td:eq(6)', nRow).html( "<button   class='btn btn-success btn-xs' onclick='apply(\""+ aData.appid +"\")'>Add</button>");
								
							}
							else if(s=="true"){
							     $('td:eq(6)', nRow).html("<td><button class='btn btn-warning btn-xs' onclick='unapply(\""+ aData.appid +"\")'>Remove</button></td>");
							
							}else{
								  $('td:eq(6)', nRow).html("<td><br>Auditing</br></td>");
							  		    	
							  }
						  
							return nRow;
						}
			});
				
			}
		});
		
	}
	
	function apply(appid){
    	$.ajax({
			url		:	"apply.action",
			data	:	"appid="+appid,
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				//updateSpecQueuee();
				$.ajax({
					url		:	"getMySpec.action",
					type	:	"post",
					dataType:	"json",
					success	:	updateTable1
				});
				location.reload(true);
			}
		});		
	}
	
	function unapply(appid){
    	$.ajax({
			url		:	"remove.action",
			data	:	"appid="+appid,
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				//updateSpecQueuee();
				$.ajax({
					url		:	"getMySpec.action",
					type	:	"post",
					dataType:	"json",
					success	:	updateTable1
				});
				location.reload(true);
			}
		});		
	}
	
	function updateTable1(specsJson) {
		var specs = JSON.parse(specsJson);
		$("#tbody1").empty();
	
		$(specs).each(function() {
			var str = $('<tr/>');
			str.append("<td>"+this.identifier+"</td>");
			str.append("<td>"+this.name+"</td>");
			str.append("<td>"+this.uri+"</td>");
			str.append("<td>"+this.version+"</td>");
			str.append("<td>"+this.documentation+"</td>");
			//alert(this.documentation);
			var btns = $('<td/>');
			btns.append("<button class='btn btn-default btn-xs' onclick='launch(\""+this.identifier+"\",\""+this.version+"\",\""+this.uri+"\")'>Launch</button>&nbsp;");
			if(this.hasOwnProperty("appProvider")){
				btns.append("<button class='btn btn-default btn-xs' onclick='unload(\""+this.identifier+"\",\""+this.version+"\",\""+this.uri+"\")'>Unload</button>&nbsp;");
			}
			btns.append("<button class='btn btn-default btn-xs' onclick='getInfo(\""+this.identifier+"\",\""+this.version+"\",\""+this.uri+"\")'>Get Info</button>&nbsp;");
			btns.append("<button class='btn btn-default btn-xs' onclick='downloadLog(\""+this.identifier+"\",\""+this.version+"\",\""+this.uri+"\")'>Download Log</button>");
			//btns.append("<td/>");
			str.append(btns);
			
			$("#tbody1").append(str);
		})		
	}
	
	function updateTable2(caseRunningJson) {
		var cases = JSON.parse(caseRunningJson);
		$("#tbody2").empty();
	
		$(cases).each(function() {
			var str = $('<tr/>');
			str.append("<td>"+this.id+"</td>");
			str.append("<td>"+this.specname+"</td>");
			str.append("<td>"+this.specversion+"</td>");
			
			var btns = $('<td/>');
			btns.append("<button class='btn btn-default btn-xs' onclick='cancel(\""+this.id+"\",\""+this.specidentifier+"\",\""+this.specversion+"\",\""+this.specuri+"\")'>Cancel Case</button>&nbsp;");
			//btns.append("<button class='btn btn-default btn-xs' onclick='info(\""+this.id+"\")'>Get Info</button>&nbsp;");
			//btns.append("<button class='btn btn-default btn-xs' onclick='logs(\""+this.id+"\")'>Get EventLogs</button>");
			str.append(btns);
			
			$("#tbody2").append(str);
		});
	}
	
	function launch(identifier, version, uri) {
		$.ajax({
			url		:	"launch.action",
			data	:	"identifier="+identifier+"&version="+version+"&uri="+uri,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable2
		});	
	}
	
	function unload(identifier, version, uri) {
		$.ajax({
			url		:	"unload.action",
			data	:	"identifier="+identifier+"&version="+version+"&uri="+uri,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable1
		});
	}
	
	function getInfo(identifier, version, uri) {
		//alert(identifier);
		$.ajax({
			url		:	"getSpecInfo.action",
			data	:	"identifier="+identifier+"&version="+version+"&uri="+uri,
			type	:	"post",
			dataType:	"json",
			success	:	function(SpecInfoJson){
				//alert(SpecInfoJson);
				var info = JSON.parse(SpecInfoJson);
				var fade = $('<div class="modal fade" id="infoModal" tabindex="-1" aria-hidden="true"/>');
				var dialog = $('<div class="modal-dialog"/>');		
				var content = $('<div class="modal-content"/>');
						
				var header = $('<div class="modal-header"/>');
				header.append("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				header.append("<h4 class='modal-title'>Information of <em><strong>"+identifier+"</strong></em></h4>");
						
				var body = $('<div class="modal-body"/>');
				var table = $('<table class="table table-striped table-condensed"/>');
				table.append("<thead><th>Name</th><th>Value</th></tr></thead>");
				var tbody = $('<tbody/>');
				var tr1 = $('<tr/>');
				tr1.append("<td>URI:	</td>");
				tr1.append("<td>"+info[0].URI+"</td>");
				tbody.append(tr1);
				var tr2 = $('<tr/>');
				tr2.append("<td>VERSION:	</td>");
				tr2.append("<td>"+info[0].VERSION+"</td>");
				tbody.append(tr2);
				var tr3 = $('<tr/>');
				tr3.append("<td>DESCRIPTION:	</td>");
				tr3.append("<td>"+info[0].DESCRIPTION+"</td>");
				tbody.append(tr3);
				var tr4 = $('<tr/>');
				tr4.append("<td>IDENTIFIER:	</td>");
				tr4.append("<td>"+info[0].IDENTIFIER+"</td>");
				tbody.append(tr4);
				var tr5 = $('<tr/>');
				tr5.append("<td>META-TITLE:	</td>");
				tr5.append("<td>"+info[0].METATITLE+"</td>");
				tbody.append(tr5);
				var tr6 = $('<tr/>');
				tr6.append("<td>SCHEMA VERSION:	</td>");
				tr6.append("<td>"+info[0].SCHEMAVERSION+"</td>");
				tbody.append(tr6);
				var tr7 = $('<tr/>');
				tr7.append("<td>AUTHOR(S):	</td>");
				tr7.append("<td>"+info[0].AUTHOR+"</td>");
				tbody.append(tr7);
				var tr8 = $('<tr/>');
				tr8.append("<td>ROOT NET:	</td>");
				tr8.append("<td>"+info[0].ROOTNET+"</td>");
				tbody.append(tr8);
				var tr9 = $('<tr/>');
				tr9.append("<td>ENGINE STATUS:	</td>");
				tr9.append("<td>"+info[0].ENGINESTATUS+"</td>");
				tbody.append(tr9);
				var tr10 = $('<tr/>');
				tr10.append("<td>EXTERNAL DATA GATEWAY:	</td>");
				tr10.append("<td>"+info[0].EXTERNALDATAGATEWAY+"</td>");
				tbody.append(tr10);
				
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
				$('#infoModal').modal('toggle');
			}
		});
	}
	
	function downloadLog(identifier, version, uri) {
		alert(identifier);
		$.ajax({
			url		:	"downloadLog.action",
			data	:	"identifier="+identifier+"&version="+version+"&uri="+uri,
			type	:	"post",
			dataType:	"json",
			success	:	function(SpecInfoJson){
				alert(SpecInfoJson);
			}
		});
	}
	
	function cancel(id, identifier, version, uri) {
		$.ajax({
			url		:	"cancel.action",
			data	:	"selectedCase="+id+"&identifier="+identifier+"&version="+version+"&uri="+uri,
			type	:	"post",
			dataType:	"json",
			success	:	updateTable2
		});
	
	}
	</script>
  </body>
</html>