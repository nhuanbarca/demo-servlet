package com.watch.dao.Imp;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.watch.connection.DBConnection;
import com.watch.dao.IUserDao;
import com.watch.model.User;

public class UserDaoImp implements IUserDao{
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	public UserDaoImp() {
		this.conn = DBConnection.getConnection();
	}

	@Override
	public User getUser(String userName) {
		String query = "select * from KhachHang where TaiKhoan = ?";
		User user = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String uname = rs.getString(1);
				String pass = rs.getString(2);
				String fname = rs.getString(3);
				String lname = rs.getString(4);
				user = new User(uname, pass, fname, lname, null, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	} 

	@Override
	public boolean insert(String firstName, String lastName, String email, String password) {
		String query = "insert into KhachHang(TaiKhoan, MatKhau, Ho, Ten) values (?,?,?,?)";
		try {
			ps= conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
