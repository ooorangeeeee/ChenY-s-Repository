package cn.stum.servlet;

import cn.stum.pojo.Result;
import cn.stum.service.Service;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/classInfo")
public class class_info extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("class_info");
        resp.setContentType("text/html;charset=utf-8");
        String teacherId = req.getParameter("teacherTid");
        Result classByTeacherId = Service.findClassByTeacherId(teacherId);
        resp.getWriter().append(JSON.toJSONString(classByTeacherId));
    }


}
