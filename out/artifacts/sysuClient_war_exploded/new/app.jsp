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
    
    <!-- Custom styles for this template -->
   
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
<link rel="stylesheet" href="css/bootstrap.min.css">

  <link rel="stylesheet" href="css/offcanvas.css"  type="text/css">
<!-- jQuery -->

 

<body>
	<%@ include file="common/header.jsp" %>
	
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-right">
        
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="case.action" class="list-group-item">过程管理</a>
            <a href="#" class="list-group-item">资源管理</a>
            <a href="app.action" class="list-group-item active">应用管理</a>
            <a href="service.action" class="list-group-item">YAWL组件管理</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
        	<h4>所有应用</h4><hr/>
        	<table id="table_id" class="display">
    <thead>
        <tr>
            <th>appid</th>
            <th>appName</th>
             <th>appType</th>
              <th>appDesc</th>
              <th>available</th>
             
        </tr>
    </thead>
    <tbody>
      
    </tbody>
</table>
			
			
		 
			
        </div><!--/span-->

      </div><!--/row-->

      <%@ include file="common/footer.jsp" %>
    </div><!--/.container-->
     <script src="js/jquery-1.10.2.js"></script>
      <script src="js/jquery.dataTables.js"></script>
     <script src="js/bootstrap.min.js"></script>
    <script src="js/offcanvas.js"></script>
    <script src="js/form2json.js"></script>
<script>
var apps;
  $(function(){
		$("#nav-mgt").addClass("active");
			
		updateAppQueuee();
		
		var paramNum = 1;	
		
		$("#addBtn").click(function() {
			paramNum++;
			var str = '<div class="form-group"><div class="col-sm-offset-2 col-sm-4"><input type="text" class="form-control" ';
			str += 'name="paramName_';
			str += paramNum;
			str += '" placeholder="name"></div><div class="col-sm-4"><input type="text" class="form-control" ';
			str += 'name="paramType_';
			str += paramNum;
			str += '" placeholder="ex:String"></div><div class="col-sm-1"><button class="btn btn-danger" onclick="deltr(this)">Delete</button></div></div>';
			$("#btns").before(str);
		});
			
		$("#saveBtn").click(function() {
			var params = 'dynamicJson=' + $("#dynamicForm").form2json();
			$.ajax({
				url:"addDynamic.action",
				type	:	"post",
				data	:	params,
				dataType:	"json",
				success	:	updateAppQueuee
	        });
	    });
    });
     
	function updateAppQueuee() {
	
		$.ajax({
			type	:	"post",
			url		:	"loadApps.action",
			dataType:	"json",
			success	:	function(appsJson) {
				 apps = JSON.parse(appsJson);
				 //alert(apps);
					$("#table_id").DataTable( {
				       data: apps,
				     "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 3 ] }],
				    columns: [
				        { data: 'appid' },
				        { data: 'appName' },
				        { data: 'appType' },
				        { data: 'appDesc' },
				        { data: 'available'}
				    ],
				    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
							/* Append the grade to the default row class name */
							var s=aData.available;
							if ( s=="false")
							{
							
								$('td:eq(4)', nRow).html( "<button   class='btn btn-success btn-xs' onclick='apply(\""+ aData.appid +"\")'>Add</button>");
								
							}
							else if(s=="true"){
							     $('td:eq(4)', nRow).html("<td><button class='btn btn-warning btn-xs' onclick='unapply(\""+ aData.appid +"\")'>Remove</button></td>");
							
							}else{
								  $('td:eq(4)', nRow).html("<td><br>Auditing</br></td>");
							  		    	
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
				updateAppQueuee();
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
				updateAppQueuee();
				location.reload(true);
			}
		});		
	}
	
   
	function deltr(delbtn){
		$(delbtn).parent().parent().remove();
	};
	
	
	
 
	
</script>
  </body>
  
</html>
