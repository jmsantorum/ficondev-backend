package es.ficondev.web.modelutil.model.orderservice;

import java.util.List;

import es.ficondev.web.modelutil.model.order.Order;

public interface OrderService {

	String calculateDistribution(List<Order> orders);
	
}
