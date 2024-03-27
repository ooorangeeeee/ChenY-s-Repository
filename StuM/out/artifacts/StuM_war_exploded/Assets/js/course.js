$(document).ready(function() {
	getCourse();


	//获取点击的课程编号
	var courseId = $(".courselist li"); 
 
	courseId.click(function() {
		var whichId =  $(this).context.innerText.slice(0,4);
//		console.log(whichId);
		localStorage.setItem("whichId", whichId);
	})
 
	/*点击上传表格*/
	$("#inClassExcel").click(function() {
		$('#inClassExcel').change(function(e) {

			$("#table-1Div").show();

			var files = e.target.files;

			var fileReader = new FileReader();
			fileReader.onload = function(ev) {
				try {
					var data = ev.target.result,
						workbook = XLSX.read(data, {
							type: 'binary'
						}), // 以二进制流方式读取得到整份excel表格对象
						persons = []; // 存储获取到的数据
				} catch(e) {
					console.log('文件类型不正确');
					return;
				}

				// 表格的表格范围，可用于判断表头是否数量是否正确
				var fromTo = '';
				// 遍历每张表读取
				for(var sheet in workbook.Sheets) {
					if(workbook.Sheets.hasOwnProperty(sheet)) {
						fromTo = workbook.Sheets[sheet]['!ref'];
						console.log(fromTo);
						persons = persons.concat(XLSX.utils.sheet_to_json(workbook.Sheets[sheet]));
						// break; // 如果只取第一张表，就取消注释这行
					}
				}

				//				console.log(persons);
				localStorage.setItem("personsdata", persons);

				for(var item in persons) {
					//					console.log(persons[item]);
					var str = persons[item];
					//					console.log(str.学号);
					$("#table-1").append("<tr><td>" + str.编号 + "</td><td>" + str.学号 + "</td><td>" + str.姓名 + "</td><td>" + str.专业年级 + "</td></tr>");

					var inClassId = $("#inClassId").val();
					var inClassName = $("#inclassName").val();
					$.ajax({
						type: "GET",
						url: "http://localhost:8080/schedule/insertStudent",
						contentType: "application/json; charset=utf-8",
						dataType: "json",
						data: {
							"stuid": str.学号,
							"name": str.姓名,
							"classid": inClassId,
							"classname": inClassName
						},
						success: function(data) {
							//alert($("#inClassId").val());
							console.log(data);
						},
						error: function(err) {
							console.log(err.status);
						}
					});
				}

				alert("插入成功!!!\n\n");

			};

			// 以二进制方式打开文件
			fileReader.readAsBinaryString(files[0]);
			localStorage.setItem("classList", $("#table-1Div").html());
		});
	})

	function getCourse() {
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/schedule/getAllClass",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			data: {
				"teacherid": 10001
			},
			async: false,
			success: function(data) {
				//返回的数据用data.d获取内容    
				for(var i = 0; i < data.length; i++) {
					var content = "<li><a href='classAttence.html'><img src='Assets/upload/pic7.jpg' alt='' /><div class='des'>" + data[i].classid + " " + data[i].classname + "<p>" + data[i].classroom + "</p></div></a></li>";
					$(".courselist").prepend(content);
				}

			},
			error: function(err) {
				console.log(err.status);
			}
		});

	}
})