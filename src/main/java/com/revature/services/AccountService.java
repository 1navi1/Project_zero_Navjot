package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.exceptions.OverdraftException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.utils.SessionCache;
/**
 * This class should contain the business logic for performing operations on Accounts
 */
public class AccountService {
	
	public AccountDao actDao;
	public static final double STARTING_BALANCE = 10.0;
	
	public AccountService(AccountDao dao) {
		this.actDao = dao;
	}
	
	/**
	 * Withdraws funds from the specified account
	 * @throws OverdraftException if amount is greater than the account balance
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void withdraw(Account a, Double amount) {
		a=actDao.getAccount(a.getId());
		Double balance = a.getBalance();
		balance-=amount;
		if(amount > balance) {
			throw new OverdraftException();
		
		}else if (amount<0) {
			throw new UnsupportedOperationException();
		}
		a.setBalance(balance);
		actDao.updateAccount(a);
	}
	
	/**
	 * Deposit funds to an account
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void deposit(Account a, Double amount) {
		if (!a.isApproved()) {
			throw new UnsupportedOperationException();
		}
		if (amount < 0 || !a.isApproved()) {
			throw new UnsupportedOperationException();
		}
		a.setBalance(a.getBalance()+amount);
		actDao.updateAccount(a);
	}
	
	/**
	 * Transfers funds between accounts
	 * @throws UnsupportedOperationException if amount is negative or 
	 * the transaction would result in a negative balance for either account
	 * or if either account is not approved
	 * @param fromAct the account to withdraw from
	 * @param toAct the account to deposit to
	 * @param amount the monetary value to transfer
	 */
	public void transfer(Account fromAct, Account toAct, double amount) {
		if (!fromAct.isApproved() || !(toAct.isApproved())) {
	    	throw new UnsupportedOperationException();
	    } else if ((fromAct.getBalance() < amount) || amount < 0)  {
	    	throw new UnsupportedOperationException();
	    	
	    }
	    fromAct.setBalance(fromAct.getBalance() - amount);
	    toAct.setBalance(toAct.getBalance() + amount); 
	    actDao.updateAccount(fromAct);
	    actDao.updateAccount(toAct);
	}
	
	/**
	 * Creates a new account for a given User
	 * @return the Account object that was created
	 */
	public Account createNewAccount(User u) {
		System.out.println(u.getAccounts().get(0));
		return actDao.addAccount(u.getAccounts().get(0));
		
	}
	
	/**
	 * Approve or reject an account.
	 * @param a
	 * @param approval
	 * @throws UnauthorizedException if logged in user is not an Employee
	 * @return true if account is approved, or false if unapproved
	 */
	public boolean approveOrRejectAccount(Account a, boolean approval) {
		if (SessionCache.getCurrentUser().get().getUserType().equals(User.UserType.CUSTOMER)){
			throw new UnauthorizedException();
		} else { 
		a.setApproved(approval);
		actDao.updateAccount(a);
		}
		return false;
	}
	
	public List<Account> getAccounts(User u) {
		List<Account> accountList = new ArrayList<Account>();
		accountList = u.getAccounts();
		return accountList;
	}
}
