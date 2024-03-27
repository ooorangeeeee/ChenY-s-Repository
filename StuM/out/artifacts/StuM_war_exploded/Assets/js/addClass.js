$(document).ready(function() {

	/*点击提交按钮*/
	$("#submit").click(function() {

		var inClassId = $("#inClassId").val();
		var inClassName = $("#inclassName").val();
		var inClassRoom = $("#inClassRoom").val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/schedule/insertClass",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			data: {
				"classid": inClassId,
				"classname": inClassName,
				"classroom": inClassRoom,
				"teacherid": 10001
			},
			success: function(data) {
				//				alert($("#inClassId").val());
				if(data.toString()=="true") {
					alert("添加成功！！\n\n点击确认返回课程页");
				}
				else{
					alert("添加失败！！\n\n点击确认返回课程页");
				}

				console.log(data);
				window.location.href = "works.html";
			},
			error: function(err) {
				console.log(err.status);
			}
		});
	})

	/*点击取消按钮*/
	$("#cancel").click(function() {
		window.location.href = "works.html";
	})
})