package com.hk.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hk.login.daos.IHkDao;
import com.hk.login.dtos.HkDto;

@Service
public class HkServiceImp implements IHkService{

	@Autowired
	private IHkDao hkDao;
	
	@Override
	public List<HkDto> getAllList() {
		return hkDao.getAllList();
	}

	@Override
	public boolean insertBoard(HkDto dto) {
		return hkDao.insertBoard(dto);
	}

	@Override
	public HkDto getBoard(int seq) {
		return hkDao.getBoard(seq);
	}

	@Override
	public boolean updateBoard(HkDto dto) {
		return hkDao.updateBoard(dto);
	}

	@Override
	public boolean mulDel(String[] seqs) {
		return hkDao.mulDel(seqs);
	}

	@Override
	public String idChk(String id) {
		return hkDao.idChk(id);
	}

	@Override
	public String getUserInfo(String id) {
		return hkDao.getUserInfo(id);
	}

	


}




