<%--
  Created by IntelliJ IDEA.
  User: cy
  Date: 2024/3/13
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆界面</title>
    <style>
        #outLoginFrame{
            width: 500px;
            height: 400px;
            margin-left: calc(50% - 250px);
            margin-top: 200px;
        }

        #innerLoginFrame{
            /*border: grey 2px solid;*/
            width: 360px;
            margin-left: 100px;
            margin-top: 45px;
            color: grey;
            font-size: 25px;
        }

        input{
            width: 220px;
            height: 30px;
        }

        #rad input{
            width: 17px;
            height: 17px;
            margin-left: 52px;
            margin-bottom: 20px;
        }
        #rad{
            font-size: 20px;
        }
    </style>
</head>
<body>

<div id="outLoginFrame" style="border: grey 2px solid">
    <h1 style="margin-left: 130px; margin-top: 40px;">学&nbsp;生&nbsp;管&nbsp;理&nbsp;系&nbsp;统</h1>
    <div id="innerLoginFrame">
        <form <%--action="Login"--%> method="post" >
            账户：<input type="text" style="font-size: 28px" id="userAccount" name="userAccount" class="myText"><br><br>
            密码：<input type="password" style="font-size: 28px" id="userPassword" name="userPassword" class="myText"><br><br>
            <%--submit对应form里的post--%>

            <div id="rad">
                <input type="radio" name="user" value="s" >学生
                <input type="radio" name="user" value="t">老师
            </div>
            <input style="width: 140px; margin-left: 20px;" type="submit" value="登陆" onclick="return checkLogin()">
            <a href="register.jsp" style="width: 140px;margin-left: 20px;">| &nbsp;注册</a><br>
            <span style="color: red;font-size: 15px;"id="tip">${tip}</span>
        </form>
    </div>
</div>




</body>
</html>

<script>

    function getRadioValue(args) {
        let element = document.getElementsByName(args);
        for (var i = 0; i < element.length;i++){
            if (element[i].checked){
                return element[i].value;
            }
        }
    }

    function checkLogin() {
        let userAccount = document.getElementById("userAccount").value;
        let userPassword = document.getElementById("userPassword").value;
        let rad = getRadioValue("user");
        console.log("user"+session.getAttribute("user"));

        if (userAccount == null || userAccount.trim()===""){
            document.getElementById("tip").innerHTML = "账户不能为空";
            return false;
        }
        if (userPassword == null || userPassword.trim()===""){
            document.getElementById("tip").innerHTML = "密码不能为空";
            return false;
        }
        return true;
    }
</script>
