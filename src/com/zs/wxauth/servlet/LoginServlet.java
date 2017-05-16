package com.zs.wxauth.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.wxauth.util.AuthUtil;

@WebServlet("/wxLogin")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 9035417553015985898L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = AuthUtil.AUTHORIZE_URL.replace("APPID", AuthUtil.TEST_APPID)
				.replace("REDIRECT_URI", URLEncoder.encode(AuthUtil.CALLBACK_URL, "UTF-8"))
				.replace("SCOPE", AuthUtil.SCOPE_SNSAPI_USERINFO);
		resp.sendRedirect(url.trim());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
