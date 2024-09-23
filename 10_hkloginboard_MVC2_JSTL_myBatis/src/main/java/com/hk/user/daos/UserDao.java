package com.hk.user.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.hk.ans.config.SqlMapConfig;
import com.hk.datasource.DataBase;
import com.hk.user.dtos.RoleStatus;
import com.hk.user.dtos.UserDto;

//외부에서 메서드를 호출하는 방법
// 객체생성해서 객체명.메서드()
// Static선언 클래스명.메서드()

public class UserDao extends SqlMapConfig{

	private String namespace="com.hk.ans.";
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
		int count =0;
		SqlSession sqlSession = null;
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			count = sqlSession.insert(namespace+"insertUser",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
	
	//2. 아이디 중복 체크하기
	//   select문 실행
	public String idCheck(String id) {
		String resultID = null;
		SqlSession sqlSession = null;
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			resultID = sqlSession.selectOne(namespace+"idCheck", id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return resultID;
	}
	
	//3. 로그인 기능 : ID와 password를 통해 회원정보 조회
	// 로그인 기능 만들어보기
	// main 메서드에 지금 만든 메서드 실행해 보기
	public UserDto getLogin(String id, String password) {
		UserDto dto = null;
		UserDto ldto =new UserDto(id,password);
		SqlSession sqlSession = null;
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			dto = sqlSession.selectOne(namespace+"getLogin",ldto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return dto;
	}
	
	//나의 정보 조회
	public UserDto getUserInfo(String id) {
		UserDto dto= null;
		SqlSession sqlSession = null;
		try {
			sqlSession= getSqlSessionFactory().openSession(true);
			dto = sqlSession.selectOne(namespace+"getUserInfo", id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return dto;
	}
	
	//나의 정보 수정하기 : update문, 파라미터:UserDto
	public boolean updateUser(UserDto dto) {
		int count=0;
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession(true);
			count =sqlSession.update(namespace+"updateUser",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		
		return count>0?true:false;
	}
	
//	//회원 탈퇴하기: update문 작성, enabled='N'
	public boolean delUser(String id) {
		int count=0;
		SqlSession sqlSession = null;
		try {
			sqlSession= getSqlSessionFactory().openSession(true);
			count=sqlSession.update(namespace+"delUser", id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
//	
	//회원목록 전체조회
	public List<UserDto> getAllUserList(){
		List<UserDto> list=new ArrayList<>();
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession(true);
			list = sqlSession.selectList(namespace+"getAllUserList");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return list;
	}
//	
	//회원목록 전제 조회[사용중]
	public List<UserDto> getUserList(){
		List<UserDto> list=new ArrayList<>();
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession(true);
			list = sqlSession.selectList(namespace+"getUserList");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return list;
	}
//	
//	//회원등급수정
	public boolean userUpdateRole(String id, String role) {
		int count=0;
		SqlSession sqlSession = null;
		Map<String, String>map=new HashMap<>();
		map.put("id",id);
		map.put("role", role);
		try {
			sqlSession= getSqlSessionFactory().openSession(true);
			count=sqlSession.update(namespace+"userUpdateRole", map );
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
