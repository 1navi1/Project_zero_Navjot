package com.revature.dao;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {

	public static String fileLocation = "src\\users.txt";
	private static File userFile = new File(fileLocation);
	private static int id =0;
	public static List<User> userList = new ArrayList<>();

	public User addUser(User user) {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
			oos.writeObject(user);
			System.out.println("User Successfully Registered");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;

	}

	public User getUser(Integer userId) {
		User requestedUser = null;// TODO Auto-generated method stub
		for (User user : userList) {
			if (user.getId() == (userId))
				requestedUser = user;
		}
		return requestedUser;
	}

	public User getUser(String username, String pass) {
		User requestedUser = null;// TODO Auto-generated method stub
		for (User user : userList) {
			if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(pass))
				requestedUser = user;
		}
		return requestedUser;
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
			
			do  {
				userList.add((User) ois.readObject());
			}while (ois.readObject()!=null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public User updateUser(User u) {

		return null;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		boolean status = false;
		for (User user : userList) {
			if (user.getId() == u.getId())
				status = userList.remove(u);
		}
		return status;
	}

}
