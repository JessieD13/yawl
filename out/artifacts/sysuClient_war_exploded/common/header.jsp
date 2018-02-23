<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String HEADER_BASEPATH = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="brand" href="#">Sysu Wfclient</a>
      <div class="nav-collapse collapse">
		<!--         
		<p class="navbar-text pull-right">
          Logged in as <a href="#">User</a>
        </p> 
        -->
        <ul class="nav">
          <li id="nav-workqueue"><a href="<%=HEADER_BASEPATH %>workqueue/offered.action">任务队列</a></li>
          <li id="nav-specmgt"><a href="<%=HEADER_BASEPATH %>case/mgt.action">过程管理</a></li>
          <li id="nav-servmgt"><a href="<%=HEADER_BASEPATH %>service/registered.action">YAWL组件管理</a></li>
          <li id="nav-workdesk"><a href="<%=HEADER_BASEPATH %>workdesk/workdesk.jsp">工作台</a></li>
          <li id="nav-app"><a href="<%=HEADER_BASEPATH %>app/all.action">应用</a></li>
        </ul>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>