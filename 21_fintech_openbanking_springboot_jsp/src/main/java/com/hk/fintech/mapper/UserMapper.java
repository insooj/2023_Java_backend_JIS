package com.hk.fintech.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.fintech.dtos.UserDto;

@Mapper
public interface UserMapper {
	public int addUser(UserDto dto);
	public UserDto loginUser(UserDto dto);
}
