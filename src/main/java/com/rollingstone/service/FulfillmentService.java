package com.rollingstone.service;

import org.springframework.data.domain.Page;

import com.rollingstone.model.AccountRceivable;
import com.rollingstone.model.Fulfillment;

public interface FulfillmentService {

	Fulfillment save(Fulfillment fulfillment); 
	Fulfillment getFulfillment(long id);
	Page<Fulfillment> getFulfillmentByPage(Integer pageNumber, Integer pageSize);
	void update(Fulfillment fulfillment);
	void delete(long id);
}
