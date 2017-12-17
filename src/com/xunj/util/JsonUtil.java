package com.xunj.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.ezmorph.object.NumberMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.beanutils.PropertyUtils;

public class JsonUtil {

	/**
	 * 将传入的list对象转换为JSON字符串
	 * @param list
	 * @return
	 */
	public static String parseArrayToJSONString(List list)
	{
		String jsonString = null;
		JSONArray ja = JSONArray.fromObject(list);
		jsonString = ja.toString();
		return jsonString;
	}
	
	/**
	 * 将JSON字符串转换为指定对象list
	 * @param list
	 * @return
	 */
	public static List parseJSONStringToArray(String jsonString, Class pojoClass){

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for ( int i = 0 ; i<jsonArray.size(); i++){
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject,pojoClass);
			list.add(pojoValue);
		}
		return list;
	}
	/**
	 * 将JSON字符串转换为指定对象list
	 * @param list
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static List parseJSONStringToArray(String jsonString, String pojoClassName) throws ClassNotFoundException{

		if(jsonString!=null && !"".equals(jsonString)){
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			JSONObject jsonObject;
			Object pojoValue;
	
			List list = new ArrayList();
			for ( int i = 0 ; i<jsonArray.size(); i++){
				jsonObject = jsonArray.getJSONObject(i);
				pojoValue = JSONObject.toBean(jsonObject,Class.forName(pojoClassName));
				list.add(pojoValue);
			}
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 将JSON字符串转换为指定对象list
	 * @param list
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static Object getObjectFromJSONStringArray(String jsonString, String pojoClassName,String keyName,String keyValue,boolean getLast) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(jsonString!=null && !"".equals(jsonString)){
			try{
				JSONArray jsonArray = JSONArray.fromObject(jsonString);
				JSONObject jsonObject;
				Object pojoValue = null;
				for ( int i = 0 ; i<jsonArray.size(); i++){
					jsonObject = jsonArray.getJSONObject(i);
					Object returnPo = JSONObject.toBean(jsonObject,Class.forName(pojoClassName));
					Object value = PropertyUtils.getProperty(returnPo, keyName);
					String objValue = value.toString();
					if(keyValue.equals(objValue))
					{
						pojoValue = returnPo;
						if(!getLast)
							return returnPo;
					}
				}
				return pojoValue;
			}
			catch(Exception e)
			{
				return null;
			}
			
		}else{
			return null;
		}
	}
	public static void main(String args[])
	{
//		JSONUtils.getMorpherRegistry().clear();
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}) );		
		JSONUtils.getMorpherRegistry().registerMorpher(new NumberMorpher(Integer.class));
		JSONUtils.getMorpherRegistry().registerMorpher(new NumberMorpher(Long.class));
		JSONUtils.getMorpherRegistry().registerMorpher(new NumberMorpher(Double.class));
		String jsonStr = "[{\"processId\":\"f64b8046309cb69901309cd5e8770002\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":2,\"time\":1308301322000,\"date\":17,\"timezoneOffset\":0,\"hours\":9,\"minutes\":2},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":1,\"statusNum\":6},{\"processId\":\"f64b8046309cb69901309cd75d350006\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":37,\"time\":1308301417000,\"date\":17,\"timezoneOffset\":0,\"hours\":9,\"minutes\":3},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":-1,\"statusNum\":5},{\"processId\":\"f64b8046309c65b401309c68587e0002\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":22,\"time\":1308294142000,\"date\":17,\"timezoneOffset\":0,\"hours\":7,\"minutes\":2},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":1,\"statusNum\":5},{\"processId\":\"f64b8046309c65b401309c6898750004\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":38,\"time\":1308294158000,\"date\":17,\"timezoneOffset\":0,\"hours\":7,\"minutes\":2},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":0,\"statusNum\":5},{\"processId\":\"f64b8046309c65b401309c69195d0008\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":11,\"time\":1308294191000,\"date\":17,\"timezoneOffset\":0,\"hours\":7,\"minutes\":3},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":1,\"statusNum\":6},{\"processId\":\"f64b8046309c65b401309c6af6a8000c\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":13,\"time\":1308294313000,\"date\":17,\"timezoneOffset\":0,\"hours\":7,\"minutes\":5},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":-1,\"statusNum\":5},{\"processId\":\"f64b8046309c65b401309c6bea7e0010\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":16,\"time\":1308294376000,\"date\":17,\"timezoneOffset\":0,\"hours\":7,\"minutes\":6},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":-1,\"statusNum\":4},{\"processId\":\"f64b8046309c65b401309c735aab0011\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":5,\"year\":111,\"nanos\":0,\"seconds\":23,\"time\":1308294863000,\"date\":17,\"timezoneOffset\":0,\"hours\":7,\"minutes\":14},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":1,\"statusNum\":5},{\"processId\":\"f64b804630b0bd3b0130b0e53730002d\",\"enterUserId\":\"admin\",\"enterUserName\":\"?????\",\"productBoxId\":\"12\",\"PDate\":{\"month\":5,\"day\":2,\"year\":111,\"seconds\":9,\"time\":1308637869000,\"date\":21,\"timezoneOffset\":0,\"hours\":6,\"minutes\":31},\"tableId\":\"\",\"remark\":\"\",\"processDirection\":1,\"statusNum\":6}]";
		try {
			Object obj = getObjectFromJSONStringArray(jsonStr,"com.hitzd.po.ProcessStatus","statusNum","6",true);
			System.out.println(obj);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
