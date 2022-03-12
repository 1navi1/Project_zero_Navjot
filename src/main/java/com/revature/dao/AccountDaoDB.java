package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {
	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	public AccountDaoDB() {
		conn = ConnectionUtil.getConnection();
		
	}
	public Account addAccount(Account a) {
		// TODO Auto-generated method stub
		int status = 0;
		String query = "insert into account (accountId,ownerId, balance,account_type) values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, a.getOwnerId());
			pstmt.setInt(2, a.getOwnerId());
			pstmt.setDouble(3, a.getBalance());
			//if (a.getType().equals(Account.AccountType.CHECKING)){
				//pstmt.setString(3, "CHECKING");
			//} else {
			//	pstmt.setString(3, "SAVINGS");
			//}

			pstmt.setObject(4, a.getType().toString());
			//pstmt.setBoolean(5, a.isApproved());
			status = pstmt.executeUpdate();
			if(status>0) {
				System.out.println("Account created and waiting for approval!");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	//This method will get account by id

	public Account getAccount(Integer actId) {
		String query = "select * from account where accountId=" +actId.intValue();
		Account a = new Account();		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next());
			a.setId(rs.getInt("accountId"));
			a.setOwnerId(rs.getInt("ownerId"));
			a.setBalance(rs.getDouble("balance"));
			a.setType(rs.getString("account_type"));
			a.setApproved(rs.getBoolean("approved"));
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return a;
	}

	public List<Account> getAccounts() {
		String query = "select * from account";
		List<Account> accountList = new ArrayList<Account>();	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				Account a = new Account();	
			
				a.setId(rs.getInt("accountId"));
				a.setOwnerId(rs.getInt("ownerId"));
				a.setBalance(rs.getDouble("balance"));
				a.setType(rs.getString("account_type"));
				a.setApproved(rs.getBoolean("approved"));
				accountList.add(a);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
		
	}

	public List<Account> getAccountsByUser(User u) {
		String query = "select * from account where ownerId=" +u.getId();
		List<Account> accountList = new ArrayList<Account>();		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("accountId"));
				a.setOwnerId(rs.getInt("ownerId"));
				a.setBalance(rs.getDouble("balance"));
				a.setType(rs.getString("account_type"));
				a.setApproved(rs.getBoolean("approved"));
				accountList.add(a);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}

	public Account updateAccount(Account a) {
		return null;
	}

	public boolean removeAccount(Account a) {
		
	return false;
	}

}
