package com.hk.ans.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.hk.ans.config.SqlMapConfig;
import com.hk.ans.dtos.AnsDto;

public class AnsDao extends SqlMapConfig{

	private String namespace="com.hk.ans.";
	
	//1.글목록 조회하기
//	public List<AnsDto> getAllList(){
//		List<AnsDto> list=new ArrayList<>();
//		SqlSession sqlSession=null;//쿼리를 실행시켜주는 객체
//		try {
//			//sqlSessionFactory객체의 openSession()를 통해 
//			//sqlSession객체를 구한다 (true:autocommit설정)
//			sqlSession=getSqlSessionFactory().openSession(true);
//			
//			//sqlSession객체에 쿼리 실행 메서드를 통해 쿼리실행
//			// 쿼리실행메서드(쿼리이름,파라미터)
//			list=sqlSession.selectList(namespace+"boardList");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			sqlSession.close();
//		}
//		return list;
//	}
	
	//1.글목록조회[페이징처리]
	public List<AnsDto> getAllList(String pnum){
		List<AnsDto>list=new ArrayList<>();
		
		SqlSession sqlSession=null;
		
		Map<String,String>map=new HashMap<>();
		map.put("pnum", pnum);//페이지 번호 저장
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			list=sqlSession.selectList(namespace+"boardList", map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return list;
	}
	
	//1-2.페이지수 구하기
	public int getPCount() {
		int count=0;
		SqlSession sqlSession=null;
		
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			count=sqlSession.selectOne(namespace+"getPCount");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count;
	}
	
	//2.새글추가하기
	public boolean insertBoard(AnsDto dto) {
		int count=0;
		SqlSession sqlSession=null;
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			//파리미터 타입: DTO,Array,Map(파라미터 기본 타입)
			//            값한개(int,String해당타입으로 정의)
			count=sqlSession.insert(namespace+"insertBoard",dto);
		} catch (Exception e) {
			e.printStackTrace();// <--이게 없으면 오류 출력안됨
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
	
	//3.상세조회
	public AnsDto getBoard(int seq) {
		AnsDto dto=null;
		//쿼리를 실행하려면 필요한 객체는 뭐다?
		SqlSession sqlSession=null;
		
		//Map에 담아서 파라미터 전달하기
		Map<String, Integer>map=new HashMap<>();
		map.put("seq", seq);
		
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
//			dto=sqlSession.selectOne(namespace+"getBoard", seq);
			dto=sqlSession.selectOne(namespace+"getBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return dto;
	}
	
	//4.수정하기
	public boolean updateBoard(AnsDto dto) {
		int count=0;
		SqlSession sqlSession=null;
		
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			count=sqlSession.update(namespace+"updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
	
	//5.조회수
	public boolean readCount(int seq) {
		int count=0;
		SqlSession sqlSession=null;
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			count=sqlSession.update(namespace+"readCount", seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count>0?true:false;
	}
	
	//6.삭제하기
	public boolean mulDel(String[] seqs) {
		int count=0;
		SqlSession sqlSession=null;
		Map<String, String[]>map=new HashMap<>();
		map.put("seqs", seqs);
		
		try {
			sqlSession=getSqlSessionFactory().openSession(true);
			count=sqlSession.update(namespace+"mulDel", map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
	
	//7.답글추가하기: update문, insert문 실행 --> transaction 처리 필요
	public boolean replyBoard(AnsDto dto) {
		int count=0;
		SqlSession sqlSession=null;
		
		try {
			//      //autocommit:false 이유는 transaction 처리를 위해
			sqlSession=getSqlSessionFactory().openSession(false);
			//같은 그룹에서 부모의 step보다 큰 글들의 step+1을 해줌
			//update문 실행
			sqlSession.update(namespace+"replyUpdate", dto);
			//insert문 실행
			count=sqlSession.insert(namespace+"replyInsert", dto);
			sqlSession.commit();//쿼리가 정상처리됐다면 DB에 반영
		} catch (Exception e) {
			sqlSession.rollback();//여러작업중 실패가 있다면 모두 되돌려~~
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		
		return count>0?true:false;
	}
	
	public void test() {
		//쿼리를 실행시킬 수 있는 객체 : sqlSession객체를 구함
		SqlSession sqlSession=getSqlSessionFactory().openSession();
		//쿼리를 실행한다.--> sqlMapper.xml에 있는 쿼리를 실행한거임
		List<AnsDto>list= sqlSession.selectList("boardList");
	}
}






