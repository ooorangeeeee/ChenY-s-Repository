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

@WebServlet("/addClass")
public class addClass extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("addClass");
        resp.setContentType("text/html;charset=utf-8");
        String classId = req.getParameter("classId");
        String className = req.getParameter("className");
        String teacherId = req.getParameter("teacherTid");
        Result result = Service.addClass(classId, className, teacherId);
        resp.getWriter().append(JSON.toJSONString(result));
    }
}
