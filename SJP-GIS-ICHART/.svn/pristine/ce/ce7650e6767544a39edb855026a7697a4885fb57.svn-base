package com.berheley.ichart.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.berheley.ichart.domain.SysUser;

public interface userRepository extends PagingAndSortingRepository<SysUser,String>{
   
	 public List<SysUser> findAll();
	 
	 public SysUser save(SysUser user);
	 
	 public SysUser findByUsernameLike(String username);
}
