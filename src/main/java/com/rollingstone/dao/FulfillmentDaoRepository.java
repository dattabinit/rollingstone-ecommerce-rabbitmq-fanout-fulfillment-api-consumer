package com.rollingstone.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rollingstone.model.AccountRceivable;
import com.rollingstone.model.Fulfillment;

public interface FulfillmentDaoRepository extends PagingAndSortingRepository<Fulfillment, Long> {

	Page<Fulfillment> findAll(Pageable pageable);
	
	@Query(name = "FulfillmentDaoRepository.getFulfillmentByID", nativeQuery = true)
	Fulfillment getFulfillmentByID(@Param("id") long id);
}
