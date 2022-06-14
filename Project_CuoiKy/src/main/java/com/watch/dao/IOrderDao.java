package com.watch.dao;
import com.watch.model.Cart;
import com.watch.model.Order;
import com.watch.model.User;

public interface IOrderDao {

	String insertOrder(Cart cart, User user ,String ho, String ten, String diachi, String thanhpho, String sdt);

	Order getOrder(int orderId);

}
