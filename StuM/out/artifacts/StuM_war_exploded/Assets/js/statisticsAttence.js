$(document).ready(function() {

	getRecord();

	function getRecord() {

		var whichId = localStorage.getItem("whichId");

		$.ajax({
			type: "GET",
			url: "http://localhost:8080/zibiao/getAttenceRecord",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			data: {
				"classid": whichId
			},
			async: false,
			success: function(data) {
				//返回的数据用data.d获取内容    
				$("#attenceRecord").append("<tr><td>编号</td><td>学号</td><td>教师Id</td><td>第几周</td><td>第几节</td><td>考勤情况</td><td>班级Id</td></tr>");
				for(var i = 0; i < data.length; i++) {
					var index = i + 1;
					//					console.log(data[i].classid + data[i].classname + data[i].name + data[i].stuid);
					if(data[i].istrue=="0"){
						data[i].istrue = "未到";
					}else{
						data[i].istrue = "已到";
					}
					
					
					$("#attenceRecord").append("<tr><td>" + index + "</td><td>" + data[i].stuid + "</td><td>" + data[i].teachid + "</td><td>" + data[i].week + "</td><td>" + data[i].coursesection + "</td><td>" + data[i].istrue + "</td><td>" + data[i].classid + "</td></tr>");
				}

			},
			error: function(err) {
				console.log(err.status);
			}
		});
	}

})