package com.hk.board.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hk.board.dtos.HkDto;

//Spring container가 dao라는 객체를 알아야 생성해서 주입을 할 수 있다.
//@Component
@Repository // Dao객체에 작성한다.
public class HkDaoImp implements IHkDao{

	//쿼리를 실행해야 함:myBatis객체
	//Autowired : 객체의 타입으로 구분해서 주입시켜 주는 기능
	@Autowired
	private SqlSessionTemplate sqlSession;//spring이 객체를 주입시킨다.
	
	//기존 방식
//	public HkDaoImp() {
//		sqlSession=new SqlSessionTemplate(null);
//	}
//	public HkDaoImp(SqlSessionTemplate sqlSession) {
//		sqlSession=sqlSession;
//	}
	
	private String namespace="com.hk.board.";
	
	@Override
	public List<HkDto> getAllList() {
		return sqlSession.selectList(namespace+"getAllList");
	}

	@Override
	public boolean insertBoard(HkDto dto) {
		int count=sqlSession.insert(namespace+"insertBoard", dto);
		return count>0?true:false;
	}

	@Override
	public HkDto getBoard(int seq) {
		return sqlSession.selectOne(namespace+"getBoard", seq);
	}

	@Override
	public boolean updateBoard(HkDto dto) {
		int count=sqlSession.update(namespace+"updateBoard", dto);
		return count>0?true:false;
	}

	@Override
	public boolean mulDel(String[] seqs) {
		Map<String, String[]>map=new HashMap<>();
		map.put("seqs", seqs);
		int count=sqlSession.delete(namespace+"mulDel", map);
		return count>0?true:false;
	}

}



