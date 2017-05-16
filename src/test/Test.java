package test;

import com.google.gson.Gson;
import com.zs.po.User;

public class Test {

	public static void main(String[] args) {
		String str = "{\"openid\":\" 11111111\","
				+ "\"nickname\":\"haha\",\"sex\":\"男\","
				+ "\"province\":\"shanghai\",\"city\":\"jiading\",\"country\":\"中国\","
				+ "\"headimgurl\":\"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc"
				+ "56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxC"
				+ "fHe/46\",\"privilege\":[\"PRIVILEGE1\",\"PRIVILEGE2\"],"
				+ "\"unionid\":\"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";
		
//		User user = new User();
//		user.setOpenid(\"11111111\");
//		user.setNickname(\"haha\");
//		user.setSex(\"男\");
//		user.setProvince(\"shanghai\");
//		user.setCity(\"jiading\");
//		user.setCountry(\"中国\");
//		user.setHeadimgurl(\"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46\");
//		user.setPrivilege(new String[]{\"PRIVILEGE1\",\"PRIVILEGE1\"});
//		user.setUnionid(\"o6_bmasdasdsad6_2sgVt7hMZOPfL\");
		
//		JSONObject jsonObject = JSONObject.fromObject(str);
//		System.out.println(jsonObject.toString());
//		User user = (User) JSONObject.toBean(jsonObject, User.class);
		User user = new Gson().fromJson(str, User.class);
		System.out.println(user);
	}

}
