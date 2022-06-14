package com.watch.services.Imp;

import com.watch.dao.IOrderDao;
import com.watch.dao.Imp.OrderDaoImp;
import com.watch.model.Cart;
import com.watch.model.Order;
import com.watch.model.User;
import com.watch.services.IOrderService;

public class OrderServiceImp implements IOrderService {
	IOrderDao orderDao;

	public OrderServiceImp() {
		orderDao = new OrderDaoImp();
	}

	@Override
	public String insertOrder(Cart cart, User user, String ho, String ten, String diachi, String thanhpho, String sdt) {
		return orderDao.insertOrder(cart, user, ho, ten, diachi, thanhpho, sdt);
	}

	@Override
	public Order getOrder(int orderId) {
		return orderDao.getOrder(orderId);
	}

}
