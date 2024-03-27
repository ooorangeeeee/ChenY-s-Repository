package cn.stum.servlet;

import cn.stum.pojo.Result;
import cn.stum.service.Service;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/studentSignIn")
public class student_signin extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("studentSignIN");
        resp.setContentType("text/html;charset=utf-8");
        String sign_id = req.getParameter("sign_id");
        String studentId = req.getParameter("studentId");
        String signNumber = req.getParameter("signNumber");
        Result mySignByStudentId = Service.studentSign(studentId,signNumber,sign_id);
        resp.getWriter().append(JSON.toJSONString(mySignByStudentId));
    }


}
