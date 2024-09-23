package com.hk.ansboard.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk.ansboard.dtos.AnsDto;

@Repository
public class AnsDaoImp implements IAnsDao{

	private String namespace="com.hk.ansboard.";
	
	private Logger logger=LoggerFactory.getLogger(AnsDaoImp.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<AnsDto> getAllList(String pnum) {
//		logger.info("getAllList실행");
		Map<String,String>map=new HashMap<>();
		map.put("pnum", pnum);
		return sqlSession.selectList(namespace+"getAllList", map) ;
	}

	@Override
	public int getPCount() {
		return sqlSession.selectOne(namespace+"getPCount");
	}

	@Override
	public boolean insertBoard(AnsDto dto) {
		int count=sqlSession.insert(namespace+"insertBoard",dto);
		return count>0?true:false;
	}

	@Override
	public AnsDto getBoard(int seq) {
		return sqlSession.selectOne(namespace+"getBoard", seq);
	}

	@Override
	public boolean updateBoard(AnsDto dto) {
		int count=sqlSession.update(namespace+"updateBoard", dto);
		return count>0?true:false;
	}

	@Override
	public boolean readCount(int seq) {
		int count=sqlSession.update(namespace+"readCount", seq);
		return count>0?true:false;
	}

	@Override
	public boolean mulDel(String[] seqs) {
		Map<String, String[]>map=new HashMap<>();
		map.put("seqs", seqs);
		int count=sqlSession.update(namespace+"mulDel", map);
		return count>0?true:false;
	}
	@Override
	public int replyUpdate(AnsDto dto) {
		return sqlSession.update(namespace+"replyUpdate", dto);
	}
	@Override
	public int replyInsert(AnsDto dto) {
		return sqlSession.update(namespace+"replyInsert", dto);
	}

}












