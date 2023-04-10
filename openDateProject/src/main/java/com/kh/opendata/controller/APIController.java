package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {

	// 발급 받은 인증키 정보 변수에 담아두기
	public static final String servicekey = "lUETVDrhZp%2Fr7PfWvlc1uLVS1%2F8Fm2NqdYOAhP5r2IXn8hp1GYuDXVjI2M0jZ03DYU9kfxmfXagdMyqPYi10CA%3D%3D";

	@ResponseBody
	@RequestMapping(value = "air.do", produces = "application/json; charset=UTF-8")
	public String airPollution(String location) throws IOException {
		
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + servicekey;
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
		url += "&returnType=json";
		url += "&numOfRows=50";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		urlConnection.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String line;
		String responseText = "";
		
		while ((line = br.readLine()) != null) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
		
		//System.out.println(responseText);
		return responseText;
	}
	
	@ResponseBody
	@RequestMapping(value = "bus.do", produces = "application/json; charset=UTF-8")
	public String busRouteList(String route) throws IOException {

		String url = "http://ws.bus.go.kr/api/rest/busRouteInfo/getRoutePath";
		url += "?serviceKey=" + servicekey;
		url += "&strSrch=" + URLEncoder.encode(route, "UTF-8");
		url += "&returnType=json";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		urlConnection.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String line;
		String responseText = "";
		
		while ((line = br.readLine()) != null) {
			responseText += line;
		}

		br.close();
		urlConnection.disconnect();
		
		return responseText;
	}
}
