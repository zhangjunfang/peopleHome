package com.github.abel533.login.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -7019430002489853832L;
	private String successJsp = "WEB-INF/jsp/main.jsp";
    private String loginJsp = "WEB-INF/jsp/login.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	System.err.println("-------------------------------------------");
        req.getRequestDispatcher(loginJsp).forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username:"+username+",password:"+password);
        resp.setCharacterEncoding("UTF-8");
        if(username.equals("admin") && password.equals("123456")){
            req.setAttribute("message", "欢迎您:" + username);
            req.getRequestDispatcher(successJsp).forward(req, resp);
        } else {
            req.setAttribute("message", "用户名或密码错误!");
            req.getRequestDispatcher(loginJsp).forward(req, resp);
        }
    }

}
