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

@WebServlet("/addSign")
public class addSign extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("addSign");
        resp.setContentType("text/html;charset=utf-8");
        String classId = req.getParameter("classId");
        String teacherId = req.getParameter("teacherId");
        String sign_number = req.getParameter("sign_number");
        String signName = req.getParameter("signName");
        Result result = Service.addSign(teacherId,classId,sign_number,signName);
        resp.getWriter().append(JSON.toJSONString(result));
    }
}
