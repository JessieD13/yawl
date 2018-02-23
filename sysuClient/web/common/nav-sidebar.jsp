<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<a style="float: right; cursor: pointer; text-decoration: none; padding-right: 10px; padding-top: 10px;" onclick="refreshWorkQueue();">&Oslash;</a>
<div class="well sidebar-nav">
  <ul class="nav nav-list">
  	<li class="nav-header">我的工作列表</li>
    	<li class="active"><a
    		href="javascript:$('#content-panel').attr('src', '../workqueue/offered.action?isPartialReq=true');refreshPage();">未接收/Offered<span id="offered-count" class="badge pull-right"></span></a></li>
    	<li><a href="#">已接收/Allocated<span id="allocated-count" class="badge pull-right"></span></a></li>
      <li><a
      	href="javascript:$('#content-panel').attr('src', '../workqueue/started.action?isPartialReq=true');refreshPage();">正在执行/Started<span id="started-count" class="badge pull-right"></span></a></li>
      <li><a href="#">挂起/Suspended<span id="suspended-count" class="badge pull-right"></span></a></li>
  </ul>
</div><!--/.well -->
<a style="float: right; cursor: pointer; text-decoration: none; padding-right: 10px; padding-top: 10px;" onclick="refreshCaseMgt();">&Oslash;</a>
<div class="well sidebar-nav">
  <ul class="nav nav-list">
  	<li class="nav-header">我的流程案例</li>
    	<li class="active"><a
    		href="javascript:$('#content-panel').attr('src', '../case/mgt.action?isPartialReq=true');refreshPage();">可发起/Available<span id="available-count" class="badge pull-right"></span></a></li>
    	<li><a
    		href="javascript:$('#content-panel').attr('src', '../case/running.action?isPartialReq=true');refreshPage();">运行中/Running<span id="running-count" class="badge pull-right"></span></a></li>
  </ul>
</div><!--/.well -->