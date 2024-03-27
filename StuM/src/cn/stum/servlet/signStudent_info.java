package cn.stum.servlet;

import cn.stum.service.Service;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signStudentInfo")
public class signStudent_info extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("signStudentInfo");
        resp.setContentType("text/html;charset=utf-8");
        String signId = req.getParameter("signId");
        String classId = req.getParameter("classId");
        System.out.println(signId+" "+classId);
        resp.getWriter().append(JSON.toJSONString(Service.checkStudentSign(signId,classId)));
    }
}
