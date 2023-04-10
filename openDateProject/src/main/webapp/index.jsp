<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery 라이브러리 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

	<h2>실시간 대기 오염 정보</h2>
	
	지역 : 
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
	</select>
	<button id="btn1">해당 지역 대기오염정보 확인하기</button>
	
	<table id="result1" border="1" align="center">
		<thead>
			<tr>
				<th>측정소명</th>
				<th>측정일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지농도</th>
				<th>아황산가스농도</th>
				<th>일산화탄소농도</th>
				<th>이산화질소농도</th>
				<th>오존농도</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	
	<script>
		$(function() {
			$("#btn1").click(function() {
				$.ajax({
					url : "air.do",
					data : {location : $("#location").val()},
					success : function(data) {
						// console.log(data);
						// console.log(data.response.body.items);
						
						const itemArr = data.response.body.items;
						
						let value = "";
						for ( let i in itemArr) { // item이 계속 바뀌기 때문에 let
							// console.log(itemArr[i]);
							let item = itemArr[i];
							
							value += "<tr>"
								   + "<td>" + item.stationName + "</td>"
								   + "<td>" + item.dataTime + "</td>"
								   + "<td>" + item.khaiValue + "</td>"
								   + "<td>" + item.pm10Value + "</td>"
								   + "<td>" + item.so2Value + "</td>"
								   + "<td>" + item.coValue + "</td>"
								   + "<td>" + item.no2Value + "</td>"
								   + "<td>" + item.o3Value + "</td>"
								   + "</tr>";
						}
						
						$("#result1 tbody").html(value);
					},
					error : function() {
						console.log("ajax 통신 실패!");
					}
				})
			})
		})
	</script>
	
	<h2>노선번호에 해당하는 노선 목록 조회</h2>
	
	노선 번호 : <input id="route">
	<button id="btn2">확인</button>
	
	<table id="result2" border="1" align="center">
		<thead>
			<tr>
				<td>노선</td>
				<td>정류소</td>
				<td>방향</td>
				<td>노선유형</td>
				<td>첫차시간</td>
				<td>막차시간</td>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	
	<script>
		$("#btn2").click(function() {
			$.ajax({
				url : "bus.do",
				data : {route : $("#route").val()},
				success : function(data) {
					const itemArr = data.response.body.items;
					
					let value = "";
					for ( let i in itemArr) { 
						
						let item = itemArr[i];
						
						value += "<tr>"
							   + "<td>" + item.busRoute + "</td>"
							   + "<td>" + item.station + "</td>"
							   + "<td>" + item.direction + "</td>"
							   + "<td>" + item.routeType + "</td>"
							   + "<td>" + item.beginTm + "</td>"
							   + "<td>" + item.lastTm + "</td>"
							   + "</tr>";
					}
			
					$("#result2 tbody").html(value);
				},
				error : function(data) {
					console.log("ajax 통신 실패!");
				}
			})
		})
	</script>
	
</body>
</html>