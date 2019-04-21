package com.rollingstone.rabbitmq.consumer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.rollingstone.model.AccountRceivable;
import com.rollingstone.model.Address;
import com.rollingstone.model.Fulfillment;
import com.rollingstone.model.ItemMaster;
import com.rollingstone.model.SalesOrderDTO;
import com.rollingstone.service.FulfillmentService;

@Service
public class SalesOrderRabbitMQMessageConsumerListerner {

	final static Logger logger  = LoggerFactory.getLogger(SalesOrderRabbitMQMessageConsumerListerner.class);
	
	private FulfillmentService fulfillmentService;
	
	public SalesOrderRabbitMQMessageConsumerListerner(FulfillmentService fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}
	
	@RabbitListener(queues = "${fulfillment.queue.name}")
	public void receiveMessageForFinanceConsumerFromSalesProducer(final SalesOrderDTO salesOrderDTO) {
		logger.info("Received message from Sales Order Producer Application :"+salesOrderDTO.toString());
		
		try {
			retrieveDataForFulfillmentAndSave(salesOrderDTO);
		}
		catch(Exception e) {
			logger.info("Exception occured during message receipt and processing : "+e.getLocalizedMessage());
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
	
	private void retrieveDataForFulfillmentAndSave(SalesOrderDTO salesOrderDTO) {
		
		String fulfillmentNumber = "FULFILLMENT-"+salesOrderDTO.getOrderNumber();
		
		String orderNumber = salesOrderDTO.getOrderNumber();
		
		String specialInstruction = salesOrderDTO.getSpecialInstruction();
		
		String shippingMethod = salesOrderDTO.getShippingMethod();
		
		Date estimatedDeliveryDate = salesOrderDTO.getEstimatedDeliveryDate();
		
		ItemMaster item = salesOrderDTO.getItem();
		
		String itemUPCCode =item.getItemUPCCode();
		
		String itemName = item.getItemName();
		
		int itemQuantity = salesOrderDTO.getItemQuantity();
		
		String color = item.getItemColor();
		
		String size = item.getItemSize();
		
		String shippedBy = item.getShippedBy();
		
		Address address = salesOrderDTO.getAddress();
		
		String shippingAddress = salesOrderDTO.getAccount().getUser().getFirstName() + " " + salesOrderDTO.getAccount().getUser().getLastName() 
				+ " " + address.getHouseNumber() + " " + address.getStreetAddress() + " " + address.getCity() + " "+ address.getState() + " " + address.getZipCode();
		
		Fulfillment fullfiment = new Fulfillment(fulfillmentNumber,orderNumber,specialInstruction,shippingMethod,
				estimatedDeliveryDate, itemUPCCode, itemName, new Integer(itemQuantity).toString(), size, color, shippedBy, shippingAddress );
	
		fulfillmentService.save(fullfiment);
	}

}
