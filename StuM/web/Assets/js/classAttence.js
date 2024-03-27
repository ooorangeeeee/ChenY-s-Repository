$(document).ready(function() {

	//设置全局变量
	var whichId;

	//显示学生表
	getStudent();

	//显示学生表
	//	$("#classList").append(localStorage.getItem("classList"));

	//全体考勤事件
	$("#allAttence").click(function() {
		$("#userUI").html("<p>请同学们拿出手机扫描二维码，开始考勤！！！</p><img src='Assets/images/code.jpg'/>");
	})

	//随机考勤事件
	$("#randAttence").click(function() {

		$("#userUI").html("<div style='position: relative;margin-left: -90px;'><div id='attenceImg' style='margin-left: 1em;'><img src='Assets/images/1.png' style='width: 1em;height: 1em;display: none;' /></div><div><table id='randTxt' style='position: absolute;top: -5px;left: 55px;'></table></div><div id='clickAttence' style='display: none;'><a>有效点击区域</a></div></div>")

		$("#clickAttence").fadeIn(2000);
		//禁用右键菜单
		$(document).bind("contextmenu", function(e) {
			return false;
		});

		//待考勤图片
		$("#attenceImg").html("<img src='Assets/images/1.png' style='width: 1em;height: 1em;' />").fadeIn();

		var rows = document.getElementById("classListInner").rows.length - 1;
//		console.log(rows);

		var random = Math.ceil(rows * Math.random());
//		console.log(random);
		$("#randTxt").html(random);

		var filter, table, tr, td, i;

		table = $("#classListInner");
		tr = $("tr");

		var id, classId, className, studentName, stuId;
		// 循环表格每一行，查找匹配项
		for(i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[0];
			if(td) {
				if(td.innerHTML == random) {
//					console.log(tr[i].innerHTML);
					id = tr[i].getElementsByTagName("td")[0].innerHTML;
					classId = tr[i].getElementsByTagName("td")[1].innerHTML;
					className = tr[i].getElementsByTagName("td")[2].innerHTML;
					studentName = tr[i].getElementsByTagName("td")[3].innerHTML;
					stuId = tr[i].getElementsByTagName("td")[4].innerHTML;
					$("#randTxt").html("<tr><td>" + id + "</td><td>" + classId + "</td><td>" + className + "</td><td>" + studentName + "</td><td>" + stuId + "</td></tr>");
				}
			}
		}

		//语音播报姓名
		Strtt(studentName);

		//考勤左右键
		$('#clickAttence').mousedown(function(e) {
			if(3 == e.which) {
				$("#attenceImg").html("<img src='Assets/images/fail.png' id='attenceImg1' style='width: 1em;height: 1em;' />").fadeIn();
				Strtt("签到失败");

				$.ajax({
					type: "GET",
					url: "http://localhost:8080/zibiao/insertRecord",
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					data: {
						"stuid": stuId,
						"teachid": 10001,
						"week" : 1,
						"coursesection" : 3,
						"istrue": 0,
						"classid": whichId
					},
					success: function(data) {
//						console.log(data);
					},
					error: function(err) {
						console.log(err.status);
					}
				});

			} else if(1 == e.which) {
				$("#attenceImg").html("<img src='Assets/images/success.png' id='attenceImg1' style='width: 1em;height: 1em;' />").fadeIn();
				Strtt("签到成功");

				$.ajax({
					type: "GET",
					url: "http://localhost:8080/zibiao/insertRecord",
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					data: {
						"stuid": stuId,
						"teachid": 10001,
						"week" : 1,
						"coursesection" : 3,
						"istrue": 1,
						"classid": whichId
					},
					success: function(data) {
//						console.log(data);
					},
					error: function(err) {
						console.log(err.status);
					}
				});
			}
		})
	})

	function Strtt(str) {
		//语音播报

		var ttsDiv = document.getElementById('bdtts_div_id');
		var ttsAudio = document.getElementById('tts_autio_id');
		var ttsText = str;

		// 这样就可实现播放内容的替换了
		ttsDiv.removeChild(ttsAudio);
		var au1 = '<audio id="tts_autio_id" autoplay="autoplay">';
		var sss = '<source id="tts_source_id" src="http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=4&text=' + ttsText + '" type="audio/mpeg">';
		var eee = '<embed id="tts_embed_id" height="0" width="0" src="">';
		var au2 = '</audio>';
		ttsDiv.innerHTML = au1 + sss + eee + au2;

		ttsAudio = document.getElementById('tts_autio_id');

		ttsAudio.play();
	}

	function getStudent() {

		whichId = localStorage.getItem("whichId");
		//		alert(whichId);

		$.ajax({
			type: "GET",
			url: "http://localhost:8080/student/getAllStudent",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			data: {
				"classid": whichId
			},
			success: function(data) {
				//返回的数据用data.d获取内容    

				$("#classListInner").append("<tr><td>编号</td><td>班级Id</td><td>班级名称</td><td>姓名</td><td>学号</td></tr>");
				for(var i = 0; i < data.length; i++) {
					var index = i + 1;
//					console.log(data[i].classid + data[i].classname + data[i].name + data[i].stuid);
					$("#classListInner").append("<tr><td>" + index + "</td><td>" + data[i].classid + "</td><td>" + data[i].classname + "</td><td>" + data[i].name + "</td><td>" + data[i].stuid + "</td></tr>");
				}

			},
			error: function(err) {
				console.log(err.status);
			}
		});
	}

})