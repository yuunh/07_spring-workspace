package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

// @Component 이렇게만 써도 bean으로 등록은 되나 역할을 제대로 명시해주지 않음
@Service
public class MemberServiceImpl implements MemberService {

	// private MemberDao mDao = new MemberDao();
	@Autowired
	private MemberDao mDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession; // 스프링에서 다룸
	
	@Override
	public Member loginMember(Member m) {

		// 이전에는 sqlSession 객체 만들고, MemberDao 생성해서 호출함
		// 이제는 의존성 주입으로 하겠음
		
		Member loginUser = mDao.loginMember(sqlSession, m);
		
		return loginUser;
	}

	/* test
	@Service
	public class MemberServiceImpl implements MemberService {
	    
		@Autowired
		private MemberDao mDao;
		
	    @Autowired
	    private SqlSessionTemplate sqlSession;
	    
	    @Override
	    public Member loginMember(Member m) {
	    	
	    	Member loginUser = mDao.loginMember(sqlSession, m);

	    	return loginUser;
	    }
	    
	}
	*/
	
	@Override
	public int insertMember(Member m) {
		
		int result = mDao.insertMember(sqlSession, m);
		
		return result;
	}

	@Override
	public int updateMember(Member m) {

		int result = mDao.updateMember(sqlSession, m);
		
		return result;
	}

	@Override
	public int deleteMember(String userId) {
		
		int result = mDao.deleteMember(sqlSession, userId);
		
		return result;
	}

	@Override
	public int idCheck(String checkId) {
		
		return mDao.idCheck(sqlSession, checkId);
	}

}
