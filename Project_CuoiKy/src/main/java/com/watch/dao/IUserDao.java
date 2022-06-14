package com.watch.dao;

import com.watch.model.User;

public interface IUserDao {
	User getUser(String userName);
	boolean insert(String firstName, String lastName, String email, String password);
}
