package com.c2m.rest.commons;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Utils {

	public static MultivaluedMap<String, String> getMutliValuedMap(
			Map<String, String> mapDetails) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		if (mapDetails.size() > 0 && !mapDetails.isEmpty()) {
			for (String key : mapDetails.keySet()) {
				formData.add(key, mapDetails.get(key));
			}
		}
		return formData;
	}

	public static Map<String, Object> getMapObjFromXMLString(String xml)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject = XML.toJSONObject(xml);
		// making case insensitive for the key added this feature
		Map<String, Object> mapObject = mapper
				.readValue(
						jsonObject.toString(),
						new org.codehaus.jackson.type.TypeReference<Map<String, Object>>() {
						});
		return mapObject;
	}

}
