package com.zs.wxauth.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.po.User;
import com.zs.wxauth.util.AuthUtil;

//@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = -6780423602119071106L;

	private String dbUrl;
	private String driverName;
	private String userName;
	private String password;
	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			this.dbUrl = config.getInitParameter("dbUrl");
			this.driverName = config.getInitParameter("driverName");
			this.userName = config.getInitParameter("userName");
			this.password = config.getInitParameter("password");

			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");

		User user = AuthUtil.getUserInfo(code);
		System.out.println(user);

		// 1.使用微信用户信息直接登录，无需注册和登录
		// req.setAttribute("info", user);
		// req.getRequestDispatcher("userInfo.jsp").forward(req, resp);

		// 2.将微信和当前系统的账号进行绑定
		try {
			String nickName = getNickName(user.getOpenid());
			if (!"".equals(nickName)) {
				// 绑定成功
				req.setAttribute("info", user);
				req.getRequestDispatcher("/userInfo.jsp").forward(req, resp);
			} else {
				// 未绑定
				req.setAttribute("openid", user.getOpenid());
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String openid = req.getParameter("openid");

		try {
			int temp = updUser(openid, account, password);
			if (temp > 0) {
				System.out.println("账号绑定成功");
			} else {
				System.out.println("账号绑定失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String getNickName(String openid) throws SQLException {
		String nickName = "";
		connection = DriverManager.getConnection(dbUrl, userName, password);
		String sql = "select nickname from user where openid = ?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, openid);
		rs = ps.executeQuery();
		while (rs.next()) {
			nickName = rs.getString("nickname");
		}

		rs.close();
		ps.close();
		connection.close();
		return nickName;
	}

	public int updUser(String openid, String account, String passWord) throws SQLException {
		connection = DriverManager.getConnection(dbUrl, userName, password);
		String sql = "update user set openid=? where account=? and password=?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, openid);
		ps.setString(2, account);
		ps.setString(3, passWord);
		int temp = ps.executeUpdate();

		rs.close();
		ps.close();
		connection.close();
		return temp;
	}
}
