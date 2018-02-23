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
            <a href="case.action" class="list-group-item">过程管理</a>
            <a href="#" class="list-group-item">资源管理</a>
            <a href="app.action" class="list-group-item active">应用管理</a>
            <a href="service.action" class="list-group-item">YAWL组件管理</a>
          </div>
        </div><!--/span-->
        
        <div class="col-xs-12 col-sm-9">
        	<h4>所有应用</h4><hr/>
        	<table class="table table-striped table-condensed" id="applist">
        		<thead>
					<tr>
					<th>#</th>
					<th>App Name</th>
					<th>Description</th>
					<th>Actions</th>
					</tr></thead>
				<tbody id="appBody">

				</tbody>
			</table>
			
			<div class="panel-group" id="accordion">
			
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
			       		新增一个简单网页应用 
			       </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in">
			    	<div class="panel-body">
					<form class="form-horizontal" id="simpleForm" onsubmit="return false;">
						<div class="form-group">
						  <label class="col-sm-2 control-label">Name</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="appName" placeholder="app name">
						  </div>
						</div>
						<div class="form-group">
						  <label class="col-sm-2 control-label">Url</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="appUrl" placeholder="http://">
						  </div>
						</div>
						<div class="form-group">
						  <label class="col-sm-2 control-label">Description</label>
						  <div class="col-sm-10">
						    <textarea rows="3" class="form-control" name="appDesc" placeholder="app desc"></textarea>
						  </div>
						</div>
						<div class="form-group">
						    <div class="col-sm-offset-2 col-sm-10">
						      <button class="btn btn-default">Cancel</button>
						      <button class="btn btn-primary" onclick="addSimple()">Save</button>
						    </div>
						</div>
					</form>
					</div>
			    </div>
			  </div>
			  
			  <div class="panel panel-default">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
			         	新增一个WebService应用
			        </a>
			      </h4>
			    </div>
			    <div id="collapseTwo" class="panel-collapse collapse">
			      <div class="panel-body">
			      	<form class="form-horizontal" id="dynamicForm">
						<div class="form-group">
						  <label class="col-sm-2 control-label">Name</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="appName" placeholder="app name">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">Url</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="appUrl" placeholder="http://">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">Description</label>
						  <div class="col-sm-10">
						    <textarea rows="3" class="form-control" name="appDesc" placeholder="app desc"></textarea>
						  </div>
						</div>
						
						<hr>
						<div class="form-group">
						  <label class="col-sm-2 control-label">EndPoint</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="endPoint" placeholder="http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">NameSpace</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="nameSpace" placeholder="http://WebXml.com.cn/">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">OperationName</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="opName" placeholder="getMobileCodeInfo">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">SOAPActionURI</label>
						  <div class="col-sm-10">
						    <input type="text" class="form-control" name="soapAction" placeholder="http://WebXml.com.cn/getMobileCodeInfo">
						  </div>
						</div>
						
						<div class="form-group">
						  <label class="col-sm-2 control-label">Parameter</label>
						  <div class="col-sm-4"><input type="text" class="form-control" name="paramName_1" placeholder="name"></div>
						  <div class="col-sm-4"><input type="text" class="form-control" name="paramType_1" placeholder="ex:String"></div>
						  <div class="col-sm-2"><input type="button" class="btn btn-info" id="addBtn" value="Add"></div>
						</div>
						
						<div class="form-group" id="btns">
						    <div class="col-sm-offset-2 col-sm-10">
						      <input type="button" class="btn btn-default" value="Cancle">
						      <input type="button" class="btn btn-primary" value="Save" id="saveBtn">
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
    <script src="js/offcanvas.js"></script>
    <script src="js/form2json.js"></script>
    <script type="text/javascript">
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
				$("#appBody").empty();
				var apps = JSON.parse(appsJson);
	
				$(apps).each(function() {
					var str = $('<tr/>');
					str.append("<td>"+this.appid+"</td>");
					str.append("<td>"+this.appName+"</td>");
					str.append("<td>"+this.appDesc+"</td>");
					if(this.available == 'true'){
						str.append("<td><button class='btn btn-warning btn-xs' onclick='unapply(\""+ this.appid +"\")'>Remove</button></td>");
					} else {
						str.append("<td><button class='btn btn-success btn-xs' onclick='apply(\""+ this.appid +"\")'>Add</button></td>");
					}
					
					$("tbody").append(str);
				})
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
			}
		});		
	}
	
    function addSimple(){
    	$.ajax({
			url		:	"addSimple.action",
			data	:	$("#simpleForm").serialize(),
			type	:	"post",
			dataType:	"json",
			success	:	function(){
				updateAppQueuee();
			}
		});
    }
	
	function deltr(delbtn){
		$(delbtn).parent().parent().remove();
	};
	
    </script>
  </body>
</html>