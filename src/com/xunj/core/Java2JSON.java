package com.xunj.core;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Java2JSON {
	public static JSONObject generate(List<?> list, int count) {
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("totalCount", count);
		map.put("topics", list);
		return JSONObject.fromObject(map);
	}

	public static JSONObject javabean2json(Object object) {
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("success", true);
		map.put("data", object);
		return JSONObject.fromObject(map);
	}
	public static JSONObject bean2json(CorePo object) {
		return JSONObject.fromObject(object);
	}
	public static JSONArray list2json(List<?> object) {
		return JSONArray.fromObject(object);
	}
	public static String list2jsonSuggest(List<?> object) {
		StringBuilder sb=new StringBuilder("{[");
		for(int i=0;i<object.size();i++){
			sb.append("{'text':'");
			sb.append(object.get(i));
			sb.append("'},");
		}
		if(object.size()>0)
		sb.deleteCharAt(sb.length()-1);
		sb.append("]}");
		return sb.toString();
	}
	public static void main(String s[]){
//		Books book=new Books();
//		book.setBookDate(new Date());
//		Books book1=new Books();
//		book1.setBookName("2222222222");
//		book1.setBookDate(new Date());
//		
//		List l=new ArrayList();
//		l.add(book);
//		l.add(book1);
//		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}) );
//		JSONArray jsonObject = list2json(l);
//		System.out.println(jsonObject.toString());
	}
}