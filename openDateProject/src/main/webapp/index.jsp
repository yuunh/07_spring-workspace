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
				
				/* json 형식으로 응답데이터 받을 때
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
				*/
				
				$.ajax({
					url : "air.do",
					data : {location : $("#location").val()},
					success : function(data) {
						console.log(data);
						// jQuery 탐색 메소드
						// find 메소드 : 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 때 사용 (html, xml 다 사용 가능)
						// console.log(data.find("item")); // find는 제이쿼리 메소드! 제이쿼리화 시켜야함!
						// console.log($(data).find("item"));
						
						// xml 형식의 응답데이터를 받았을 때
						// 1. 응답데이터 안에 실제 데이터가 담겨있는 요소 선택
						
						let itemArr = $(data).find("item");
						
						// 2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들기
						let value = "";
						itemArr.each(function(i, item) { // i에는 인덱스값, item은 순차적으로 접근한 요소
							// console.log(item);
							// console.log($(item).find("stationName")); // <station>중구</station>
							// console.log($(item).find("stationName").text());
							
							value += "<tr>"
								   + "<td>" + $(item).find("stationName").text() + "</td>"
								   + "<td>" + $(item).find("dataTime").text() + "</td>"
								   + "<td>" + $(item).find("khaiValue").text() + "</td>"
								   + "<td>" + $(item).find("pm10Value").text() + "</td>"
								   + "<td>" + $(item).find("coValue").text() + "</td>"
								   + "<td>" + $(item).find("no2Value").text() + "</td>"
								   + "<td>" + $(item).find("so2Value").text() + "</td>"
								   + "<td>" + $(item).find("o3Value").text() + "</td>"
								   + "</tr>";
						})
						
						// 3. 동적으로 만들어낸 요소를 화면에 출력하기
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
	
	<hr>

	<h2>지진해일대피소 정보</h2>
	<input type="button" value="실행" id="btn3">
	
	<div id="result3"></div>
	
	<script>
		$(function() {
			
			/*
			$("#btn3").click(function() {
				$.ajax({
					url : "disaster.do",
					success : function(data) {
						// console.log(data);
						// console.log($(data).find("row"));
						
						// jquery 방식으로 동적으로 테이블 요소 만들기!
						let $table = $("<table border='1'></table>");
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
								   + "<th>일련번호</th>"
								   + "<th>시도명</th>"
								   + "<th>시군구명</th>"
								   + "<th>시도대피장소명</th>"
								   + "<th>주소</th>"
								   + "<th>수용가능인원(명)</th>"
								   + "<th>해변으로부터거리</th>"
								   + "<th>대피소분류명</th>"
								   + "</tr>";
					
						// 결합작업
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						
						$(data).find("row").each(function(i, row) { // i는 인덱스값, row는 순차적으로 접근한 요소
							// console.log(row);
							console.log($(row).find("shel_nm").text()); // <shel_nm>어쩌구</shel_nm)
							
							bodyTr += "<tr>"
									+ "<td>" + $(row).find("id").text() + "</td>"
									+ "<td>" + $(row).find("sido_name").text() + "</td>"
									+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
									+ "<td>" + $(row).find("shel_nm").text() + "</td>"
									+ "<td>" + $(row).find("address").text() + "</td>"
									+ "<td>" + $(row).find("shel_av").text() + "</td>"
									+ "<td>" + $(row).find("lenth").text() + "</td>"
									+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
									+ "</tr>";
						})
						
						// 결합
						$tbody.html(bodyTr);
				
						// jquery 방식으로 만들어진 요소를 자식으로 추가할 때는 이렇게 해야함
						// html() 메소드는 안에 스트링을 넣어야함
						
						// $table.append($thead, $tbody); // a.append(b) => a 안에 b를 추가하겠다
						// $table.appendTo("#result3");   // a.appendTo(b) => b 안에 a를 추가하겠다
						
						$table.append($thead, $tbody)
							  . appendTo("#result3");
					},
					error : function() {
						console.log("ajax 통신 실패!");
					}
				})
			})
			*/
			
			$("#btn3").click(() => {
				$.ajax({
					url : "disaster.do",
					success : data => {
						// console.log(data);
						// console.log($(data).find("row"));
						
						// jquery 방식으로 동적으로 테이블 요소 만들기!
						let $table = $("<table border='1'></table>");
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
								   + "<th>일련번호</th>"
								   + "<th>시도명</th>"
								   + "<th>시군구명</th>"
								   + "<th>시도대피장소명</th>"
								   + "<th>주소</th>"
								   + "<th>수용가능인원(명)</th>"
								   + "<th>해변으로부터거리</th>"
								   + "<th>대피소분류명</th>"
								   + "</tr>";
					
						// 결합작업
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						
						$(data).find("row").each((i, row) => { // i는 인덱스값, row는 순차적으로 접근한 요소
							// console.log(row);
							console.log($(row).find("shel_nm").text()); // <shel_nm>어쩌구</shel_nm)
							
							bodyTr += "<tr>"
									+ "<td>" + $(row).find("id").text() + "</td>"
									+ "<td>" + $(row).find("sido_name").text() + "</td>"
									+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
									+ "<td>" + $(row).find("shel_nm").text() + "</td>"
									+ "<td>" + $(row).find("address").text() + "</td>"
									+ "<td>" + $(row).find("shel_av").text() + "</td>"
									+ "<td>" + $(row).find("lenth").text() + "</td>"
									+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
									+ "</tr>";
						})
						
						// 결합
						$tbody.html(bodyTr);
				
						// jquery 방식으로 만들어진 요소를 자식으로 추가할 때는 이렇게 해야함
						// html() 메소드는 안에 스트링을 넣어야함
						
						// $table.append($thead, $tbody); // a.append(b) => a 안에 b를 추가하겠다
						// $table.appendTo("#result3");   // a.appendTo(b) => b 안에 a를 추가하겠다
						
						$table.append($thead, $tbody)
							  . appendTo("#result3");
					},
					error : () => {
						console.log("ajax 통신 실패!");
					}
				})
			})
		})
		
		/*
			** 화살표 함수 **
			익명함수들을 화살표 함수로 작성할 수 있음
			
			"function() {}"를		"() => {}" 이런 식으로 작성 가능
			
			"function(data) {}"를	"data => {}" 이런 식으로 작성 가능 (매개변수 한개)
			
			"function(a,b) {}"를		"(a, b) => {}" 이런 식으로 작성 가능 (매개변수 여러개)
			
			"function() {return 10;}"를 "() => 10" 이런 식으로 작성 가능
		*/
	</script>	
	
	<hr>
	
	<h1>재진/해일 대피소 안내</h1>
	<input type="button" value="실행" id="btn" />
	<div id="result"></div>
	<script>
	$("#btn").click(() => {
		$.ajax({
			success : data => {
				var $table = $("<table></table>"); //동적으로 생성할 table 태그 선언
				var $thead = $("<thead></thead>");
				var header = "<tr>"
							+ "<td>일렬번호</td>"
							+ "<td>시도명</td>"
							+ "<td>시군구명</td>"
							+ "<td>대피지구명</td>"
							+ "<td>대피장소명</td>"
							+ "<td>주소</td>"
							+ "<td>경도</td>"
							+ "<td>위도</td>"
							+ "<td>수용가능인원(명)</td>"
							+ "<td>해변으로부터거리</td>"
							+ "<td>대피소분류명</td>"
							+ "<td>내진적용여부</td>"
							+ "<td>해발높이</td>"
							+ "</tr>";
							
				$thead.html(header);
				
				let $tbody = $("<tbody></tbody>");
				let bodyTr = "";
				
				$(data).find("row").each((i, row) => {
					bodyTr += "<tr>"
							+ "<td>" + $(row).find("id").text() + "</td>"
							+ "<td>" + $(row).find("sido_name").text() + "</td>"
							+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
							+ "<td>" + $(row).find("remarks").text() + "</td>" 
							+ "<td>" + $(row).find("remarks").text() + "</td>"​
							+ "<td>" + $(row).find("shel_nm").text() + "</td>"
							+ "<td>" + $(row).find("address").text() + "</td>"
							+ "<td>" + $(row).find("lon").text() + "</td>"​
							+ "<td>" + $(row).find("lat").text() + "</td>"​
							+ "<td>" + $(row).find("shel_av").text() + "</td>"
							+ "<td>" + $(row).find("lenth").text() + "</td>"
							+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
							+ "<td>" + $(row).find("seismic").text() + "</td>"​​
							+ "<td>" + $(row).find("height").text() + "</td>"
							+ "</tr>";
							
					$tbody.html(bodyTr);
					$table.append($thead, $tbody)
					 	  . appendTo("#result");
				}
			},
			error : (xhr, textStatus, err) => {
				console.log(xhr, testStatus, err);
			}
		});
	});
	</script>

	<script>
	$(function(){
		$("#btn").click(function(){
			$.ajax({
				url:"controller 매핑값 어쩌구저쩌구",
				success:(data) =>{
				let $table = $("<table></table>");
				let $thead = $("<thead></thead>");
				let headTr = "<tr>"
							+ "<td>일렬번호</td>"
							+ "<td>시도명</td>"
							+ "<td>시군구 명</td>"
							+ "<td>대피 지구 명</td>"​
							+ "<td>대피 장소 명</td>"
							+ "<td>주소</td>"
							+ "<td>경도</td>"
							+ "<td>위도</td>"​
							+ "<td>수용 가능인원</td>"
							+ "<td>해변으로 부터 거리</td>"
							+ "<td>대피소 분류명</td>"
							+ "<td>내진적용여부</td>"
							+ "<td>해발높이</td>"​​
							+ "</tr>";
				$thead.html(headTr);
				let $tbody = $("<tbody></tbody>");
				let bodyTr = "";
				$(data).find("row").each(function(i, row){ 
				bodyTr += "<tr>"
						+ "<td>" + $(row).find("id").text() + "</td>"
						+ "<td>" + $(row).find("sido_name").text() + "</td>"
						+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
						+ "<td>" + $(row).find("remarks").text() + "</td>" 
						+ "<td>" + $(row).find("remarks").text() + "</td>"​
						+ "<td>" + $(row).find("shel_nm").text() + "</td>"
						+ "<td>" + $(row).find("address").text() + "</td>"
						 + "<td>" + $(row).find("lon").text() + "</td>"​
						 + "<td>" + $(row).find("lat").text() + "</td>"​
						+ "<td>" + $(row).find("shel_av").text() + "</td>"
						+ "<td>" + $(row).find("lenth").text() + "</td>"
						+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
						+ "<td>" + $(row).find("seismic").text() + "</td>"​​
						+ "<td>" + $(row).find("height").text() + "</td>"​​
						+ "</tr>";
		                  })
				$tbody.html(bodyTr); 
				$table.append($thead, $tbody).appendTo("#result");
				},
				error:function(xhr, textStatus, err){
					console.log(xhr, textStatus, err​)
				}
			});
		})
		})

</script>
	<input type="button" value="실행" id="btn" />
	<div id="result-container"></div>
	<script>
	$("#btn").click(() => {
			$.ajax({
				success : data => {
					var $table = $("<table></table>"); //동적으로 생성할 table 태그 선언
					var $thead = $("<thead></thead>");
					var header = "<tr>"
						+ "<td>일렬번호</td>"
						+ "<td>시도명</td>"
						+ "<td>시군구명</td>"
						+ "<td>대피지구명</td>"
						+ "<td>대피장소명</td>"
						+ "<td>주소</td>"
						+ "<td>경도</td>"
						+ "<td>위도</td>"
						+ "<td>수용가능인원(명)</td>"
						+ "<td>해변으로부터거리</td>"
						+ "<td>대피소분류명</td>"
						+ "<td>내진적용여부</td>"
						+ "<td>해발높이</td>"
						+ "</tr>";
						
			$thead.html(header);
			
			let $tbody = $("<tbody></tbody>");
			let bodyTr = "";
			
			$(data).find("row").each((i, row) => {
				bodyTr += "<tr>"
						+ "<td>" + $(row).find("id").text() + "</td>"
						+ "<td>" + $(row).find("sido_name").text() + "</td>"
						+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
						+ "<td>" + $(row).find("remarks").text() + "</td>" 
						+ "<td>" + $(row).find("remarks").text() + "</td>"​
						+ "<td>" + $(row).find("shel_nm").text() + "</td>"
						+ "<td>" + $(row).find("address").text() + "</td>"
						+ "<td>" + $(row).find("lon").text() + "</td>"​
						+ "<td>" + $(row).find("lat").text() + "</td>"​
						+ "<td>" + $(row).find("shel_av").text() + "</td>"
						+ "<td>" + $(row).find("lenth").text() + "</td>"
						+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
						+ "<td>" + $(row).find("seismic").text() + "</td>"​​
						+ "<td>" + $(row).find("height").text() + "</td>"
						+ "</tr>";
						
				$tbody.html(bodyTr);
					});
					
					table.append(thead, tbody);
					$("#result-container").html(table);
				},
				error:function(){
		               console.log("ajax 통신 실패!!"); 
		        }
			});
		});
	</script>



	
</body>
</html>