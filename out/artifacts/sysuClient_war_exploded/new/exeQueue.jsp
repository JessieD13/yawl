<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 2018/3/2
  Time: 下午12:02
  To change this template use File | Settings | File Templates.
--%>
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
                <a href="exeQueue.action" class="list-group-item active">我的执行队列</a>
                <a href="workqueue.action" class="list-group-item">我的任务列表</a>
                <a href="participate.action" class="list-group-item">我参与的案例</a>
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
                                <button class="btn btn-sm btn-success" name="sortbtn" id="myExeQueuebtn" onclick="updateTable()">我的队列</button>
                                <button class="btn btn-sm btn-default" name="sortbtn" id="ddlbtn"  style="background:white" onclick="updateTableDeadline()">截止时间最近</button>
                                <button class="btn btn-sm btn-default" name="sortbtn" id="exeTimebtn" style="background:white" onclick="updateTableExetime()">执行时间最短</button>
                                <button class="btn btn-sm btn-default" name="sortbtn" id="startbtn" style="background:white" onclick="updateTableEnable()">开始时间最近</button>

                            </p>

                            <hr/>

                            <table class="table table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th>Spec: Case</th>
                                    <th>Task</th>
                                    <th>ExecuteTime</th>
                                    <th>LatestStartTime</th>
                                    <th>TimeSurplus</th>
                                </tr></thead>
                                <tbody id="tbody1">

                                </tbody>
                            </table><br/><br/><br/>
                            <p id ="condition">

                            </p>
                        </div>
                    </div>
                </div>

            </div>

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
        console.log("\n\n\n页面启动\n\n\n");
        updateTable();


        $("#default").click(function() {
            $.ajax({
                url		:	"loadExecuting.action",
                type	:	"post",
                dataType:	"json",
                success	:	function(itemsJson) {
                    updateTable(itemsJson);
                }
            });
        });

    });
    function updateTable() {
        console.log("\nupdateTable\n");
        $.ajax({
            url		:	"loadExecuting.action",
            type	:	"post",
            dataType:	"json",
            success	:	function(itemsJson) {
                updateTable2(itemsJson);
            }
        });
    }


    function updateTable2(itemsJson) {
        console.log("\nupdateTable2\n");
        var items = JSON.parse(itemsJson);
        $("#tbody1").empty();
        $(items).each(function() {
            var str = $('<tr/>');
            str.append("<td>"+this.spec+": "+this.case+"</td>");
            str.append("<td>"+this.task+"</td>");
            str.append("<td>"+this.executeTime+"</td>");
            str.append("<td>"+this.latestStartTime+"</td>");
            str.append("<td>"+this.timeSurplus+"</td>");
            $("#tbody1").append(str);
        })

        updateQueueAssess();
    }

    function getQueueAssess(condition) {
        console.log("\nupdateQueueAssess\n");
        $("#condition").empty();
        var con = JSON.parse(condition);
        console.log("\n"+con);
        var str = "sss";
        if (con == "2") {
            str = "优秀";
        } else if (con == "1") {
            str = "良好";
        } else if (con == "0") {
            str = "超时异常";
        }

        $("#condition").append(str);

    }

    function updateQueueAssess() {
        $.ajax({
            url: "loadOverallAssess.action",
            type: "post",
            dataType: "json",
            success: function (condition) {
                getQueueAssess(condition);
            }
        })
    }

    function updateTableDeadline(btnid) {
        $('button[name="sortbtn"]').removeClass("btn-success");
        $('button[name="sortbtn"]').removeClass("btn-warning");
        $('button[name="sortbtn"]').addClass("btn-default");
        $("#"+btnid).addClass("btn-warning");
        $("#"+btnid).removeClass("btn-default");

            $.ajax({
                url		:	"loadDeadline.action",
                type	:	"post",
                dataType:	"json",
                success	:	function(itemsjson) {
                    updateTable2(itemsjson)
                }
            });
    }

    function updateTableEnable(btnid) {
        $('button[name="sortbtn"]').removeClass("btn-success");
        $('button[name="sortbtn"]').removeClass("btn-warning");
        $('button[name="sortbtn"]').addClass("btn-default");
        $("#"+btnid).addClass("btn-warning");
        $("#"+btnid).removeClass("btn-default");

        $.ajax({
            url		:	"loadEnable.action",
            type	:	"post",
            dataType:	"json",
            success	:	function(itemsjson) {
                updateTable2(itemsjson)
            }
        });
    }

    function updateTableExetime(btnid) {
        $('button[name="sortbtn"]').removeClass("btn-success");
        $('button[name="sortbtn"]').removeClass("btn-warning");
        $('button[name="sortbtn"]').addClass("btn-default");
        $("#"+btnid).addClass("btn-warning");
        $("#"+btnid).removeClass("btn-default");

        $.ajax({
            url		:	"loadExetime.action",
            type	:	"post",
            dataType:	"json",
            success	:	function(itemsjson) {
                updateTable2(itemsjson)
            }
        });
    }


</script>

</body>
</html>