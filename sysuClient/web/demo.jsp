<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
<title>Ajax demo</title>
</head>

<body>
	<div id="result"></div>
	<div>
		<form action="">
			<input type="text" name="userName"/>
   			<input type="text" name="userAge" />
   			<input type="button" value="add" id="btn"/>
   		</form>
	</div>

</body>

<script type="text/javascript" src="public/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#btn").click(function () {
		// 序列化表单的值
	    var params=$("form").serialize();
		$.ajax({
			url:"jsonAction.action",
			type	:	"post",
			data	:	params,//必须先对提交表单数据数据进行序列化，采用jQuery的serialize()方法
			dataType:	"json",
			success	:	show
	        });
	    });
	});
	
	function show(result) {
		var json = JSON.parse(result);
		var str = "result:" + json.key + "<br />";
		$("#result").html(str);
	}
</script>


</html>