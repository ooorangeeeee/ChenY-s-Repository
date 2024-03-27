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

@WebServlet("/Login")
public class login extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("进来了");
//        Result user = Service.login("123", "123");
//        req.setAttribute("tip", JSON.toJSON(user));
//        req.getRequestDispatcher("Login.jsp").forward(req,resp);
//        System.out.println(JSON.toJSON(user));

        resp.setContentType("text/html;charset=utf-8");
        String userAccount = req.getParameter("userAccount");
        String userPassword = req.getParameter("userPassword");
        boolean rad = req.getParameter("rad").equals("s");

        resp.getWriter().append(JSON.toJSONString(Service.login(userAccount,userPassword,rad)));


    }
}
