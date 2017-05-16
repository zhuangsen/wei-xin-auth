package com.zs.wxauth.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.zs.po.User;

import net.sf.json.JSONObject;

public class AuthUtil {

	public static final String APPID = "wx556e5b2e4d4b827a";
	public static final String APPSECRET = "dd8ee32598397b58b0f34881c3bc7079";

	public static final String TEST_APPID = "wx73834ad5aa2dfaa7";
	public static final String TEST_APPSECRET = "1f6301ee7a66d882e6afeac6fe07237e";

	public static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public static final String USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static final String CALLBACK_URL = "http://zhuangsen.tunnel.qydev.com/weixinauth/callback";
	public static final String ACCESS_TOKEN = "vP8uOvaMMmA6OPV-sp_iJtYBxXXZCqfJcSt_pggK4gMtgkDW-6khX1qT6jUCZPP_D0WWxOs-dVXeuL2Z8IY4qD064n-wev_BqBlZN54pC4o";

	public static final String SCOPE_SNSAPI_BASE = "snsapi_base";
	public static final String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";

	public static JSONObject doGetJson(String url) throws ParseException, IOException {
		JSONObject jsonObject = null;
		// CloseableHttpClient client = new CloseableHttpClient();
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = closeableHttpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		httpGet.releaseConnection();
		return jsonObject;
	}

	public static JSONObject getAccessTokenAndOpenID(String code) throws ParseException, IOException {
		String url = ACCESS_TOKEN_URL.replace("APPID", AuthUtil.TEST_APPID).
				replace("SECRET", AuthUtil.TEST_APPSECRET).replace("CODE",code);
		JSONObject jsonObject = doGetJson(url);
		return jsonObject;
	}

	public static User getUserInfo(String code) throws ParseException, IOException {
		JSONObject jsonObject = getAccessTokenAndOpenID(code);
		String openid = jsonObject.getString("openid");
		String token = jsonObject.getString("access_token");
		System.out.println("openid:" + openid);
		System.out.println("token:");
		System.out.println(token);
		String infoUrl = USER_INFO.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
		JSONObject userInfo = doGetJson(infoUrl.trim());
//		User user = (User) JSONObject.toBean(userInfo,User.class);
		User user = new Gson().fromJson(userInfo.toString(), User.class);
		return user;
	}
}
