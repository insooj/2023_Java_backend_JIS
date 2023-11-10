package com.hk.board.daos;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hk.board.dtos.HkDto;
import com.hk.datasource.DataBase;

public class HkDao extends DataBase {
	
	public HkDao() { //생략가능 
		super(); // 생략가능  -> 부모의 생성자 호출  -> 객체 생성 
	}

	//글목록 조회 기능 : 반환값 List<HkDto> -> 여러개의 행일 경우  
	public List<HkDto> getAllList(){
		List<HkDto> list = new ArrayList<>();
		Connection conn = null; //DB연결된 객체 
//		Statement -> ?파라미터를 제공X
		PreparedStatement psmt = null; //쿼리준비를 위한 객체 : ? 파라미터를 제공 
		ResultSet rs = null;//쿼리실행결과를 받아줄 객체
		
		String sql = " SELECT seq, id, title, content, regdate "
				+ " FROM hkboard ORDER BY regdate DESC 	";
		
		
		try {
			conn = getConnection(); //2단계 : DB 연결하기 
			psmt=conn.prepareStatement(sql);//3단계: 쿼리 준비하기 
			rs = psmt.executeQuery();//4단계: 쿼리 실행하기
			//5단계: 쿼리결과 받기 -> 자바에서 사용할 수 있게 처리 	
			while(rs.next()) {//rs객체안에 데이터가있는지 여부를 확인 
				HkDto dto = new HkDto();//행단위로 저장
				dto.setSeq(rs.getInt(1));//DB에서 인덱스 체계는 1부터 시작
				dto.setId(rs.getString(2));//
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setRegDate(rs.getDate(5));
				list.add(dto);
				System.out.println(dto);
				
				
			}
		} catch (SQLException e) {
			System.out.println("JDBC실패:"+getClass()+":"+"getAllList()");
			e.printStackTrace();
		}finally {
			
			close(rs, psmt, conn); // 6단계: 쿼리결과 닫기 
		}
		return list;
	}
	
	//글 추가  insert문 실행, 파라미터(id,title,content) , seq, regdate -> 쿼리에서 추가
	//테이블 수정 -> 결과값이 없음
	public boolean insertBoard(HkDto dto) {
		int count = 0 ; 
		
		Connection conn = null; //DB연결된 객체 
		PreparedStatement psmt = null; //쿼리준비를 위한 객체 : ? 파라미터를 제공 

		String sql = " INSERT INTO hkboard "
				+ " VALUES(null, ? , ? , ? ,SYSDATE()) ";
		
		try {
			conn = getConnection(); //2단계 :DB연결 
			psmt = conn.prepareStatement(sql);//3단계 : 쿼리준비
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			count = psmt.executeUpdate();// 테이블을 수정하기 때문에 executeUpdate()
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count<0?true:false;
	}
	//상세 조회: select문 실행, 파라미터(pk: seq)
	// 조회기능이니깐 결과가 있음 -> ResultSet 필요 
	// 반환타입: 
	public HkDto getBoard(int seq) {
		HkDto dto = new HkDto();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = " SELECT seq, id, title, content, regdate "
				+ " FROM hkboard "
				+ " WHERE seq = ?";
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			rs = psmt.executeQuery();// 조회
			while(rs.next()) {
				dto.setSeq(rs.getInt(1)); 
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setRegDate(rs.getDate(5));
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
	//글 수정: update문 실행, 파라미터(seq, title, content), regdate는 쿼리에서 수정 
	//반환 타입 X -> 테이블을 수정 
	public boolean updateBoard(HkDto dto) {
		int count = 0 ;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = " UPDATE hkboard "
				+ " SET title = ? , content= ? , regdate=SYSDATE() "
				+ " WHERE seq = ? ";
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getSeq());

			count = psmt.executeUpdate(); // 수정된 행의 개수를 반환 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count>0?true:false;
	}
	//글 삭제 : delete문 실행, 파라미터(seq) 
	//테이블 수정 : ResultSet X 결과값이 없음 
	public boolean deleteBoard(int seq) {
		int count =0;
		Connection conn=null;
		PreparedStatement psmt = null;
		String sql = " DELETE FROM hkboard WHERE seq = ? ";
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(null ,psmt, conn);
		}
		return count>0?true:false;
	}
	
	//화면처리: 여러개의 체크박스중에 체크된 seq만 전달받기 - > 같은 name 여러값전달
	//                                             chk:[seq,seq,seq...]
	//여러개의 쿼리 실행 --> 배치개념 
	//실행하고 오류 없으면 
	//conn.commit() 실행 --> DB 반영
	//오류가 있으면 --> conn.rollback()
	public boolean mulDel(String[] seqs) {
		boolean isS=true;//성공여부
		int [] count=null;//쿼리 실행결과 개수
		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql="delete from hkboard where seq = ? ";
	
		try {
			conn=getConnection();
			//TX처리: 자동커밋 - 수동 설정
			conn.setAutoCommit(false);//rollback이 안되기때문에
			
			//TX처리
			psmt=conn.prepareStatement(sql);
			for (int i = 0; i < seqs.length; i++) {
				psmt.setString(1, seqs[i]);//여기서는 seq의 타입이 String임:setString()사용				
				psmt.addBatch();//delete문 하나 저장
			}
			//3단계:쿼리 준비 성공
			
			count=psmt.executeBatch();//실행결과를 배열로 반환:[1,1,1]
			//TX처리
			conn.commit();//DB에 반영하기
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				//TX처리
				conn.rollback();//작업이 실패하면 모두 되돌리기
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try {
				//TX처리
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close(null, psmt, conn);
			//화면처리를 위한 성공여부 확인
			for (int i = 0; i < count.length; i++) {
				if(count[i]!=1) {
					isS=false;
					break;
				}
			}
		}
		return isS;
	}
}












