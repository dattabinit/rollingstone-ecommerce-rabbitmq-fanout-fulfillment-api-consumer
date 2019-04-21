package com.rollingstone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rollingstone.dao.FulfillmentDaoRepository;
import com.rollingstone.model.AccountRceivable;
import com.rollingstone.model.Fulfillment;

@Service
public class FulfillmentServiceImpl implements FulfillmentService {

	final static Logger logger  = LoggerFactory.getLogger(FulfillmentServiceImpl.class);
	
	private FulfillmentDaoRepository fulfillmentDaoRepository;
	
	public FulfillmentServiceImpl(FulfillmentDaoRepository fulfillmentDaoRepository) {
		this.fulfillmentDaoRepository = fulfillmentDaoRepository;
	}
	
	@Override
	public Fulfillment save(Fulfillment fulfillment) {
		logger.info("This is the save method of FulfillmentServiceImpl");
		logger.info("Fulfillment Model Received from SalesOrder Producer :"+ fulfillment.toString());
		return fulfillmentDaoRepository.save(fulfillment);
	}

	@Override
	public Fulfillment getFulfillment(long id) {
	
		Fulfillment accountRceivable = fulfillmentDaoRepository.getFulfillmentByID(id);
		return accountRceivable;
	}

	@Override
	public Page<Fulfillment> getFulfillmentByPage(Integer pageNumber, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("fulfillmentNumber").descending());
		return fulfillmentDaoRepository.findAll(pageable);
	}

	@Override
	public void update(Fulfillment fulfillment) {
		fulfillmentDaoRepository.save(fulfillment);
		
	}

	@Override
	public void delete(long id) {
		fulfillmentDaoRepository.deleteById(id);
		
	}

}
