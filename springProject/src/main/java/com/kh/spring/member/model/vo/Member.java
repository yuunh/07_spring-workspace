package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복)
 * 
 * 1. 라이브러리 다운로드 후 적용 (Maven pom.xml)
 * 2. 다운로드된 jar 찾아서 설치 (작업할 IDE 선택해서 설치)
 * (안되면 CMD로 해당 경로 진입 후 java -jar lombok-1.18.12.jar)
 * 3. IDE 재실행 (이클립스,STS)
 */

@NoArgsConstructor	// 기본 생성자
@AllArgsConstructor	// 매개변수 생성자
@Setter				// setter 메소드
@Getter				// getter 메소드
@ToString			// toString 메소드

public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	// private int age;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
	// private String uName;
	// 단, 롬복을 쓸 때는 필드명 작성시 소문자 두글자 이상으로 시작할 것 (권장/오류날수있어서)
	
}
