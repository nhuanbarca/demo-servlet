package com.watch.dao.Imp;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.watch.connection.DBConnection;
import com.watch.dao.IOrderDao;
import com.watch.dao.IProductDao;
import com.watch.model.Cart;
import com.watch.model.Order;
import com.watch.model.OrderDetails;
import com.watch.model.Product;
import com.watch.model.User;

public class OrderDaoImp implements IOrderDao{
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public OrderDaoImp() {
	}
	@Override
	public String insertOrder(Cart cart, User user, String ho, String ten, String diachi, String thanhpho, String sdt)  {
		String query = "insert into HoaDon(NgayHoaDon, TaiKhoan) output inserted.SoHoaDon values (GETDATE(), ?)";
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "us1");
			rs = ps.executeQuery();
			if (rs.next()) {
				String id = rs.getString(1);
				insertOrderDetails(cart, ho, ten, diachi, thanhpho, sdt, id);
				insertDelivery(id, ho, ten, diachi, thanhpho, sdt);
				return id;
				
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void insertOrderDetails(Cart cart, String ho, String ten, String diachi, String thanhpho, String sdt, String id) throws SQLException {
		String query = "insert into ChiTietHoaDon(SoHoaDon, MaSanPham, SoLuong) values (?, ?, ?)";
		ps = conn.prepareStatement(query);
		Set<String> keys = cart.getItems().keySet();
		for (String key : keys) {
			ps.setString(1, id);
			ps.setString(2, key);
			ps.setInt(3, cart.getItems().get(key).getQuantity());
			ps.executeUpdate();
		}
		
	}
	public void insertDelivery(String id, String ho, String ten, String diachi, String thanhpho, String sdt) throws SQLException {
		String query = "insert into VanChuyen(SoHoaDon, HoTen, DiaChi, ThanhPho, Sdt) values (?,?,?,?,?)";
		ps = conn.prepareStatement(query);
		ps.setString(1, id);
		ps.setNString(2, ho + " " + ten);
		ps.setNString(3, diachi);
		ps.setNString(4,thanhpho);
		ps.setString(5, sdt);
		ps.executeUpdate();
	}
	@Override
	public Order getOrder(int orderId) {
		String query = "select * from HoaDon where SoHoaDon = ?";
		Order order = null;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			while(rs.next()) {
				List<OrderDetails> orderDetails = getOrderDetails(orderId);
				order = new Order(rs.getInt(1), orderDetails, rs.getDate(2), rs.getString(3));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}
	private List<OrderDetails> getOrderDetails(int orderId) {
		String query = "select * from ChiTietHoaDon where SoHoaDon = ?";
		List<OrderDetails> result = new ArrayList<OrderDetails>();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, orderId);
			ResultSet rsDetails = ps.executeQuery();
			IProductDao pDao = new ProductDaoImp();
			while(rsDetails.next()) {
				Product product = pDao.getProductById(rsDetails.getString(2));
				OrderDetails od = new OrderDetails(rsDetails.getInt(1), product, rsDetails.getInt(3));
				result.add(od);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
