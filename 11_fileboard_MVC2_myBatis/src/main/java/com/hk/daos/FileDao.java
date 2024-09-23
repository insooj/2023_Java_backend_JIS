package com.hk.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.hk.config.SqlMapConfig;
import com.hk.dtos.FileDto;

public class FileDao extends SqlMapConfig{
	
	private String namespace="com.hk.";
	
	//1.업로드 파일의 정보 추가 
	public boolean insertFile(FileDto dto) {
		int count = 0;
		SqlSession sqlSession  = null;
		try {
			sqlSession = getSqlSessionFactory().openSession(true);
			count= sqlSession.insert(namespace+"insertFile",dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
	//2. 업로드한 파일의 목록 조회 
	public List<FileDto> getFileList(){
		List<FileDto> list=null;
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession(true);
			list = sqlSession.selectList(namespace+"getFileList");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return list;
	}
	//3. 업로드 파일의 상세내용 조회 
		public FileDto getFileInfo(int seq) {
			FileDto dto = null;
			SqlSession sqlSession = null;
			Map<String, Integer>map = new HashMap<>();
			map.put("seq", seq);
			try {
				sqlSession = getSqlSessionFactory().openSession(true);
				dto = sqlSession.selectOne(namespace+"getFileList",map);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				sqlSession.close();
			}
			return dto;
					
		}
	
}
