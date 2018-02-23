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

  <body>
  <%@ include file="../common/header.jsp" %>

    <div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
        	<div class="well sidebar-nav">
            <ul class="nav nav-list">
            	<li class="nav-header">应用列表</li>
              	<li><a href="available.action">可用的应用</a></li>
              	<li class="active"><a href="all.action">所有的应用</a></li>
            </ul>
          </div><!--/.well -->
        </div>
        <div class="span9">
	        <form name="appForm" action="" method="post"><fieldset><legend>All Applications</legend>
	        	<div style="height:350px;"><table class="table table-hover">
	        		<thead>
						<tr>
						<th>#</th>
						<th>App Name</th>
						<th>Description</th>
						<th>App Type</th>
						</tr></thead>
					<tbody>
						<s:iterator id="app" value="%{apps}">
						<tr>
						<td><s:property value="#app.getId()"/></td>
						<td><s:property value="#app.getAppName()"/></td>
						<td><s:property value="#app.getAppDesc()"/></td>
						<td><s:property value="#app.getAppType()"/></td>
						
						<!-- 获取session中的userid，判断当前登录user是否已经申请了该app -->
						<s:set name="userid" value="#session.userid" />
						<s:if test="#app.isBelongto(#userid)">
						<td><button class="btn" disabled onclick="appForm.action='apply.action';appForm.submit();">已申请</button></td>
						</s:if>
						<s:else>
						<td><a class="btn btn-primary" href="../app/apply.action?appid=<s:property value="#app.getId()"/>">申请添加</a></td>
						</s:else>
						
						</tr>
						</s:iterator>
					</tbody></table></div>

			</fieldset></form>
			
			<div class="accordion" id="newApp">
				<div class="accordion-group">
				  <div class="accordion-heading">
				    <a class="accordion-toggle" data-toggle="collapse" data-parent="#newApp" href="#simpleApp">
				    	新增一个简单网页应用 </a>
				  </div>
				  <div id="simpleApp" class="accordion-body collapse in">
				    <div class="accordion-inner">
					<form class="form-horizontal" name="simpleForm" action="" method="post">
						<div class="control-group">
						  <label class="control-label">Name</label>
						  <div class="controls">
						    <input type="text" name="appName" placeholder="app name">
						  </div>
						</div>
						<div class="control-group">
						  <label class="control-label">Url</label>
						  <div class="controls">
						    <input type="text" name="appUrl" placeholder="http://">
						  </div>
						</div>
						<div class="control-group">
						  <label class="control-label">Description</label>
						  <div class="controls">
						    <textarea rows="3" name="appDesc" placeholder="app desc"></textarea>
						  </div>
						</div>
						<div class="control-group">
						    <div class="controls">
						      <button class="btn">Cancel</button>
						      <button class="btn btn-primary" onclick="simpleForm.action='addSimple.action';simpleForm.submit();">Save</button>
						    </div>
						</div>
					</form>
				    </div>
				  </div>
				</div>
				
				<div class="accordion-group">
				  <div class="accordion-heading">
				    <a class="accordion-toggle" data-toggle="collapse" data-parent="#newApp" href="#dynamicApp">
				    	新增一个WebService应用</a>
				  </div>
				  <div id="dynamicApp" class="accordion-body collapse">
				    <div class="accordion-inner">
					<form class="form-horizontal" name="dynamicForm" action="addDynamic.action" method="post">
						<div class="control-group">
						  <label class="control-label">Name</label>
						  <div class="controls">
						    <input type="text" name="appName" placeholder="app name">
						  </div>
						</div>
						
						<div class="control-group">
						  <label class="control-label">Url</label>
						  <div class="controls">
						    <input type="text" name="appUrl" placeholder="http://">
						  </div>
						</div>
						
						<div class="control-group">
						  <label class="control-label">Description</label>
						  <div class="controls">
						    <textarea rows="3" name="appDesc" placeholder="app desc"></textarea>
						  </div>
						</div>
						
						<hr>
						<div class="control-group">
						  <label class="control-label">EndPoint</label>
						  <div class="controls">
						    <input type="text" name="endPoint" placeholder="http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx">
						  </div>
						</div>
						
						<div class="control-group">
						  <label class="control-label">NameSpace</label>
						  <div class="controls">
						    <input type="text" name="nameSpace" placeholder="http://WebXml.com.cn/">
						  </div>
						</div>
						
						<div class="control-group">
						  <label class="control-label">OperationName</label>
						  <div class="controls">
						    <input type="text" name="opName" placeholder="getMobileCodeInfo">
						  </div>
						</div>
						
						<div class="control-group">
						  <label class="control-label">SOAPActionURI</label>
						  <div class="controls">
						    <input type="text" name="soapAction" placeholder="http://WebXml.com.cn/getMobileCodeInfo">
						  </div>
						</div>
						
						<div class="control-group">
						  <label class="control-label">Parameter</label>
						  <div class="controls">
						    <input type="text" class="span2" name="paramName" placeholder="name">
						    <input type="text" class="span1" name="paramType" placeholder="ex:String">
						  </div>
						</div>
						
						<div class="control-group">
						    <div class="controls">
						      <button class="btn">Cancel</button>
						      <button class="btn btn-primary" onclick="dynamicForm.action='addDynamic.action';dynamicForm.submit();">Save</button>
						    </div>
						</div>
					</form>
				    </div>
				  </div>
				</div>
			
			</div>
	 
		</div><!-- end of span9 -->
			 
	</div><!-- end of row-fluid -->
				
	<%@ include file="../common/footer.jsp" %>
	
    </div> <!-- /container -->
</body>
<!-- Placed at the end of the document so the pages load faster -->
<script src="../public/js/jquery.min.js"></script>
<script src="../public/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(function(){
		$("#nav-app").addClass("active");
	});
</script>

</html>