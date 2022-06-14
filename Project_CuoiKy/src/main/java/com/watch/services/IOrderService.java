package com.watch.services;

import com.watch.model.Cart;
import com.watch.model.Order;
import com.watch.model.User;

public interface IOrderService {
	String insertOrder(Cart cart, User user, String ho, String ten, String diachi, String thanhpho, String sdt);

	Order getOrder(int orderId);
}
