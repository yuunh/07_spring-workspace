package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiExplorer {

	public static void main(String[] args) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/pathinfo/getLocationInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=서비스키");
		urlBuilder.append("&" + URLEncoder.encode("stSrch", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
	}
}
