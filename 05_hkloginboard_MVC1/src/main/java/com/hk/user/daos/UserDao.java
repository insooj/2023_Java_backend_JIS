package com.hk.user.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hk.datasource.DataBase;
import com.hk.user.dtos.RoleStatus;
import com.hk.user.dtos.UserDto;

//외부에서 메서드를 호출하는 방법
// 객체생성해서 객체명.메서드()
// Static선언 클래스명.메서드()

public class UserDao extends DataBase{

	//싱글톤 패턴 : 객체를 한번만 생성하자
	private static UserDao userDao;//생성된 객체를 저장
	private UserDao() {}// new LoginDao() X : 외부접근금지
	public static UserDao getUserDao() {//메서드를 통해 접근 가능
		if(userDao==null) {
			userDao=new UserDao();//내부에서 객체생성
		}
		return userDao;
	}
	
	//사용자 기능
	
	//1. 회원가입 기능(enabled:'Y', role:'USER', regDate:현재날짜)
	// insert문 작성
	public boolean insertUser(UserDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" INSERT INTO userinfo "
				 + " VALUES (NULL,?,?,?,?,?,'Y',?,SYSDATE())";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getPassword());
			psmt.setString(4, dto.getAddress());
			psmt.setString(5, dto.getEmail());
			psmt.setString(6, String.valueOf(RoleStatus.USER));
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
	
	//2. 아이디 중복 체크하기
	//   select문 실행
	public String idCheck(String id) {
		String resultId=null;
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql=" SELECT id FROM userinfo WHERE id=? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs=psmt.executeQuery();
			while(rs.next()) {
				resultId=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		return resultId;
	}
	
	//3. 로그인 기능 : ID와 password를 통해 회원정보 조회
	// 로그인 기능 만들어보기
	// main 메서드에 지금 만든 메서드 실행해 보기
	public UserDto getLogin(String id, String password) {
		UserDto dto=new UserDto();
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
//		String sql=" SELECT id,NAME,role "
//				 + " FROM userinfo "
//				 + " WHERE id=? AND password=? AND enabled='Y' ";
		
		//String 객체에 값이 빈번하게 변경되는 상황이라면.. 객체 생성해서 사용
		//--> 메모리 효율이 더 좋음
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT id,NAME,role ");
		sb.append(" FROM userinfo ");
		sb.append(" WHERE id=? AND password=? AND enabled='Y' ");
		
		try {
			conn=getConnection();
			
			psmt=conn.prepareStatement(sb.toString());
			psmt.setString(1, id);
			psmt.setString(2, password);
			
			rs=psmt.executeQuery();
			
			while(rs.next()) {
				dto.setId(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setRole(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		
		return dto;
	}
	
	//나의 정보 조회
	public UserDto getUserInfo(String id) {
		UserDto dto=new UserDto();
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql=" SELECT id, NAME, address, email, role, regdate "
				 + " FROM userinfo "
				 + " WHERE id=? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs=psmt.executeQuery();
			while(rs.next()) {
				dto.setId(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setRole(rs.getString(5));
				dto.setRegDate(rs.getDate(6));
				System.out.println(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		return dto;
	}
	
	//나의 정보 수정하기 : update문, 파라미터:UserDto
	public boolean updateUser(UserDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" UPDATE userinfo "
				 + " SET address=? , email=? "
				 + " WHERE id = ? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, dto.getAddress());
			psmt.setString(2, dto.getEmail());
			psmt.setString(3, dto.getId());
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
	
	//회원 탈퇴하기: update문 작성, enabled='N'
	public boolean delUser(String id) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" UPDATE userinfo "
				 + " SET enabled = 'N' "
				 + " WHERE id =? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, id);
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
	
	//회원목록 전체조회
	public List<UserDto> getAllUserList(){
		List<UserDto> list=new ArrayList<>();
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql=" SELECT seq, id, NAME, address, email, role, "
				 + " enabled,regdate "
				 + " FROM userinfo ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			rs=psmt.executeQuery();
			while(rs.next()) {
				UserDto dto=new UserDto();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setAddress(rs.getString(4));
				dto.setEmail(rs.getString(5));
				dto.setRole(rs.getString(6));
				dto.setEnabled(rs.getString(7));
				dto.setRegDate(rs.getDate(8));
				list.add(dto);
				System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		return list;
	}
	
	//회원목록 전제 조회[사용중]
	public List<UserDto> getUserList(){
		List<UserDto> list=new ArrayList<>();
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql=" SELECT seq, id, NAME, role, "
				 + " regdate "
				 + " FROM userinfo "
				 + " WHERE enabled='Y' ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			rs=psmt.executeQuery();
			while(rs.next()) {
				UserDto dto=new UserDto();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setRole(rs.getString(4));
				dto.setRegDate(rs.getDate(5));
				list.add(dto);
				System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		return list;
	}
	
	//회원등급수정
	public boolean userUpdateRole(String id, String role) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" UPDATE userinfo "
				 + " SET role=? "
				 + " WHERE id=? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, role);
			psmt.setString(2, id);
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		
		return count>0?true:false;
	}
}

















