function refreshPage() {
	refreshWorkQueue();
	refreshCaseMgt();
}

function refreshWorkQueue() {
	$.ajax({
		url : "refresh-workqueue.action",
		type : "POST",
		dataType : "json",
		success : function(response) {
			$("#offered-count").html(response.data.offered);
			$("#allocated-count").html(response.data.allocated);
			$("#started-count").html(response.data.started);
			$("#suspended-count").html(response.data.suspended);
		},
		error : function(xhr, error, printstack) {}
	});
}

function refreshCaseMgt() {
	$.ajax({
		url : "refresh-casemgt.action",
		type : "POST",
		dataType : "json",
		success : function(response) {
			$("#available-count").html(response.data.available);
			$("#running-count").html(response.data.running);
		},
		error : function(xhr, error, printstack) {}
	});
}