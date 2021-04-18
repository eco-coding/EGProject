package member.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import member.model.dao.MemberDAO;
import member.model.vo.Member;
import member.model.vo.MemberPageData;

public class MemberService {

	private JDBCTemplate factory;

	public MemberService() {
		factory = JDBCTemplate.getConnection();
	}
	
	public int register(Member member) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			result = new MemberDAO().register(conn, member);
			
			if (0 < result) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}
	
	
	public Member select(String memberId, String memberPwd) {
		Member member = null;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			member = new MemberDAO().select(conn, memberId, memberPwd);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return member;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// JoinViewServlet.java
	// 아이디,패스워드에 맞는 정보 취득
	// Servlet → Member member = new MemberService().selectOneMember(userId,
	// userPwd);
	public Member selectOneMember(String userId, String userPwd) {
		Member member = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			member = new MemberDAO().selectOneMember(conn, userId, userPwd);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return member;
	}

	// JoinViewServlet.java
	// 유저 등록
	// Servlet → Member member = new MemberService().registerMember(member);
	public int registerMember(Member member) {
		int result = 0;
		Connection conn = null;

		try {
			conn = factory.createConnection();
			result = new MemberDAO().insertMember(conn, member);
			if (result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	// MyinfoServlet.java
	// 유저 등록시 ID중복 체크
	// 유저ID가 있으면 1 없으면 0 반환
//	public Member selectOneById(String userId) {
	public int selectIdCheck(Member member) {
//		Member member = null;
		int result = 0;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			result = new MemberDAO().selectIdCheck(conn, member);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	// 회원수정
	public int modifyMember(Member member) {
		int result = 0;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			result = new MemberDAO().updateMember(conn, member);
			if (result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	// 회원 리스트
	public MemberPageData selectMemberList(String usertype, int currentPage) {
		Connection conn = null;
		MemberPageData pagedata = new MemberPageData();
		try {
			conn = factory.createConnection();
			pagedata.setMemberList(new MemberDAO().selectMemberList(conn, usertype, currentPage));
			pagedata.setPageNavi(new MemberDAO().getPageNavi(conn, usertype, currentPage));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pagedata;
	}

	// 회원 검색
	public MemberPageData selectByIdList(String usertype, String search, int currentPage) {
		Connection conn = null;
		MemberPageData pagedata = new MemberPageData();
		try {
			conn = factory.createConnection();
			pagedata.setMemberList(new MemberDAO().selectByIdList(conn, usertype,search, currentPage));
			pagedata.setPageNavi(new MemberDAO().getSearchPageNavi(conn, usertype,search, currentPage));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pagedata;
	}
}
