package com.berheley.ichart.dao;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.berheley.ichart.domain.TEcharts;
public interface echartsRepository extends PagingAndSortingRepository<TEcharts,String> {
	
	public TEcharts save(TEcharts techarts);
	
	public TEcharts findOne(String id);
	
	@Transactional(readOnly=true)
	@Query("select u from TEcharts u where u.create_user_ = ?1")
	public List<TEcharts> findListByUserId(@Param("userid")String userid);

}
