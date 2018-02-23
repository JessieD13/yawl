function drawFlow(canvasID, x1, y1, x2, y2) {
	$("#"+canvasID).drawLine({
		  strokeStyle: "#000",
		  strokeWidth: 2,
		  x1: x1, y1: y1,
		  x2: x2, y2: y2,
		});
}

function drawTask(canvasID, x, y, color, taskName, taskMap) {
	$("#"+canvasID).drawRect({
		x			: x,
		y			: y,
		group		: "processLG",
		layer		: true,
		width		: 32,
		height		: 32,
		cursor		: "pointer",
		fillStyle	: color,
		fromCenter	: false,
		strokeStyle	: "black",
		strokeWidth	: 1,
		shadowColor	: "lightgray",
		shadowX		: 3,
		shadowY		: 3,
		strokeWidth	: 1,
		index		: 0,
		mouseover	: function(layer) {
			$(this).animateLayerGroup(taskName, {
				visible	: true
			}, 0);
		},
		mouseout	: function(layer) {
			$(this).animateLayerGroup(taskName, {
				visible	: false
			}, 0);
		}
	});
	drawTaskLabel(canvasID, x, y, taskName);
	drawTipsLayerGroup(canvasID, x + 40, y, taskName, taskMap);
}

function drawTaskLabel(canvasID, x, y, taskName) {
	$("#"+canvasID).drawText({
		x			: x + 16,
		y			: y + 40,
		text		: taskName,
		group		: "processLG",
		layer		: true,
		cursor		: "default",
		fontSize	: "12pt",
		fontStyle	: "bold",
		fillStyle	: "black",
		fromCenter	: true,
		strokeWidth	: 0,
		index		: 0
	});
}

function drawTipsLayerGroup(canvasID, x, y, taskName, taskMap) {
	$("#"+canvasID).drawRect({
		x			: x,
		y			: y,
		width		: 420,
		height		: 20,
		layer		: true,
		cursor		: "default",
		name		: taskName + "tipsBackground",
		group		: taskName,
		fillStyle	: "white",
		fromCenter	: false,
		strokeWidth	: 1,
		strokeStyle	: "gray",
		shadowColor	: "lightgray",
		shadowX		: 3,
		shadowY		: 3
	});
	$("#"+canvasID).drawText({
		x			: x + 10,
		y			: y + 10,
		text		: taskName,
		layer		: true,
		cursor		: "default",
		name		: taskName + "tipsTitle",
		group		: taskName,
		fontSize	: "12pt",
		fontStyle	: "bold",
		fillStyle	: "black",
		fromCenter	: false,
		align		: "left",
		respectAlign: "true",
		maxWidth	: 200,
		strokeWidth	: 0
	});
	var offsetY = $("#"+canvasID).measureText(taskName + "tipsTitle").height;
	var textHeight = offsetY;
	for (var m in taskMap) {
		$("#"+canvasID).drawText({
			x			: x + 10,
			y			: offsetY + y + 10,
			text		: m + ":" + taskMap[m],
			layer		: true,
			cursor		: "default",
			group		: taskName,
			fontStyle	: "normal",
			fillStyle	: "black",
			fromCenter	: false,
			align		: "left",
			respectAlign: "true",
			maxWidth	: 400,
			strokeWidth	: 0
		});
		offsetY += textHeight;
	}
	$("#"+canvasID).animateLayer(taskName+"tipsBackground", {
		height : offsetY + 20
	}, 0);
	$("#"+canvasID).animateLayerGroup(taskName, {
		visible	: false
	});
}

function drawTipsLayer(id) {
	$("#"+id).drawRect({
		x			: -500,
		y			: -500,
		group		: "tipsLG",
		layer		: true,
		width		: 100,
		height		: 100,
		fillStyle	: "white",
		strokeStyle	: "gray",
		strokeWidth	: 1,
		fromCenter	: false,
		shadowColor	: "lightgray",
		shadowX		: 3,
		shadowY		: 3
	});
}
