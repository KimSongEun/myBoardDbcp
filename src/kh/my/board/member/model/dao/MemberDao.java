package kh.my.board.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kh.my.board.common.JdbcTemplate;
import kh.my.board.member.model.vo.Member;

public class MemberDao {

	public MemberDao() {
	}

	public int login(Connection conn, String member_id, String member_pwd) {
		int result = -1; // 로그인 실패 : 0
//		String sql = "select member_id from member where member_id=? AND member_pwd = ?";
		String sql = "select member_pwd from member where member_id=?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
//			pstmt.setString(2, member_pwd);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				String pwd = rset.getString(1);
				if(member_pwd.equals(pwd)) {
				result = 1; // 로그인 성공 : 1
				} else {
					result=0;
				}
			} else {
				result =2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return result;
	}

	// CRUD 메소드 작성
	public ArrayList<Member> readMemberListAll(Connection conn) {
		ArrayList<Member> volist = null;
		String sql = "Select * from member";
		PreparedStatement pstmt = null;
		ResultSet rset = null; // null로 뽑아 사용 // 많이 까먹음.

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			System.out.println("[sekim]--0");
			if (rset.next()) { // 찾은게 하나라도 있으면 null로 해준 volist를 생성
				System.out.println("[sekim]--1");
				volist = new ArrayList<Member>();

				do { // 이미 if에서 next()한번 갔다왔으므로 do를 해준다.
					Member vo = new Member();
					vo.setAddress(rset.getString("address"));
					vo.setMember_id(rset.getString("member_id"));
					vo.setMember_pwd(rset.getString("member_pwd"));
					vo.setMember_name(rset.getString("member_name"));
					vo.setEmail(rset.getString("email"));
					vo.setPhone(rset.getString("phone"));
					vo.setAddress(rset.getNString("address"));
					vo.setAge(rset.getInt("age"));
					vo.setGender(rset.getString("gender").charAt(0));
					vo.setEnroll_date(rset.getDate("enroll_date"));
					vo.setPoint(rset.getInt("point"));
					volist.add(vo); // 많이 까먹음.
					System.out.println("[sekim]--몇번?");
				} while (rset.next());
			}

		} catch (Exception e) {
			System.out.println("[sekim]--몇번?");
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("[sekim]--리턴은" + volist);
		return volist;
	}

	public int checkDuplicatedMember(Connection conn, Member vo) {
		int result = -1;
		String sql = "select member_id from member where member_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMember_id());
			rset = pstmt.executeQuery();
			if (rset.next()) {
//				System.out.println("기존회원있음");
				// 여기 리턴해주는걸 약속해주면 된다.
				result = 2;
			} else {
//				System.out.println("기존회원 없음");
				result = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 여기 -1
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return result;
	}

	public int insertMember(Connection conn, Member vo) {
		int result = -1;
		String sqlInsert = "insert into" + " member"
				+ "(member_id, member_pwd, member_name, gender, email, phone, address, age, enroll_date) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setString(1, vo.getMember_id());
			pstmt.setString(2, vo.getMember_pwd());
			pstmt.setString(3, vo.getMember_name());
			pstmt.setString(4, String.valueOf(vo.getGender())); // !!!!!!!!중요!!!!!!
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getPhone());
			pstmt.setString(7, vo.getAddress());
			pstmt.setInt(8, vo.getAge());

			result = pstmt.executeUpdate();
//				pstmt.close(); // finally로!!!!!
		} catch (Exception e) {
			e.printStackTrace();
			// 여기 -1
		} finally {
			JdbcTemplate.close(pstmt);
		}
		return result;
	}

	public int updatePointMember(Connection conn, String member_id, int point) {
		int result = -1;
		String sql = "update member set point = point + ? where member_id = ?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setString(2, member_id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}

		return result;
	}

	// ---------------

	public int updateMember(Connection conn, String id, String pwd, String email, String phone, String address) {
		int result = -1;
		try {
			String sql = "update member set member_pwd = ?, member_email = ?, member_phone = ?, member_address = ? where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setString(4, address);
			pstmt.setString(5, id);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
