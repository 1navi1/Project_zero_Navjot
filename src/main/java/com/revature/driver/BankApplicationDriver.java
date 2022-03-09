package com.revature.driver;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the entry point to the application
 */
public class BankApplicationDriver {
	//public static void printLine() {
	//for(int i=0;i<2; i++) {
	//		System.out.println("*");
	//}
	//}
		
		public static void main(String[] args) 
			{
		int choice =0;
		int id =0;
		Scanner s = new Scanner(System.in);	
		
		String fname=null;
		String lname=null;
		String username=null;
		String password=null;
		
		UserDao userDao = new UserDaoDB();
		AccountDao accountDao = new AccountDaoDB();
		UserService userService = new UserService(userDao, accountDao);
		AccountService accountService = new AccountService(accountDao); 
		
		
		while(choice<6) {
		//BankApplicationDriver.printLine();
		System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*");
		System.out.println("WELCOME TO LIDDERS BANK");
		System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*");
		//BankApplicationDriver.printLine();
		
		System.out.println("1.Register");
		System.out.println("2.Login");
		System.out.println("3.View a Customer");
		System.out.println("4.Delete a Customer");
		System.out.println("5.Update a existing Customer");
		System.out.println("6.Exit");
		System.out.println("Enter Your Choice :");
		choice = s.nextInt();
		
			switch(choice) {
			case 1:
				id = UserDaoFile.userList.size();
				System.out.print("Enter FirstName: ");
				fname = s.next();
				System.out.print("Entet LastName: ");
				lname =s.next();
				System.out.print("Enter Username: ");
				username = s.next();
				System.out.print("Enter Password: ");
				password = s.next();
			
				User user = new User(id++, fname,lname,username,password,UserType.CUSTOMER);
				userService.register(user); 
				break;
			case 2:
				System.out.print("Enter Username :");
				username = s.next();
				System.out.print("Enter Password :");
				password = s.next();
				User loggedUser = userService.login(username, password);
				System.out.println("logged user :" + loggedUser);
				if (loggedUser != null) {
					System.out.println("Logged in Successfully!!!");
					SessionCache.setCurrentUser(loggedUser);

					int option = 0;
					int accountType = 0;
					double startingBalance = 0;

					while (option <= 6) {
						System.out.println("\t\t\t 1.Apply for new Account ");
						System.out.println("\t\t\t 2.Deposit");
						System.out.println("\t\t\t 3.Withdraw ");
						System.out.println("\t\t\t 4.Fund Transfer ");
						System.out.println("\t\t\t 5.Approve/Reject Account ");
						System.out.println("\t\t\t 6.Logout ");
						System.out.print("Enter your option [1-6]:");
						option = s.nextInt();
						switch (option) {
						case 1:
							System.out.print("select the Account Type [1.Checking/2.Saving]: ");
							accountType = s.nextInt();
							System.out.print("Enter Starting balance:");
							startingBalance = s.nextDouble();
							Account account = new Account();
							account.setBalance(startingBalance);
							System.out.println("Logged user ID :" + SessionCache.getCurrentUser().get().getId());
							account.setOwnerId(loggedUser.getId());
							account.setType(accountType == 1 ? AccountType.CHECKING.toString(): AccountType.SAVINGS.toString());
							List<Account> accountList = new ArrayList<Account>();
							accountList.add(account);
							loggedUser.setAccounts(accountList);
							accountService.createNewAccount(loggedUser);
							break;
						case 2:
							System.out.println("Available Accounts for this user");
							accountService.getAccounts(loggedUser).forEach(System.out::println);
							System.out.print("Enter Account ID to Deposit :");
							int accountId = 0 ;
							accountId = s.nextInt();
							System.out.print("Enter the amount to deposit :");
							double amount = 0;
							amount = s.nextDouble();
							account = accountDao.getAccount(accountId);
							accountService.deposit(account, amount);
							break;
						case 3:

							break;
						case 4:

							break;
						case 5:
							break;
						case 6:
							System.out.print("Logout? (1.Yes/2.No) :");
							int logout = 0;
							logout = s.nextInt();
							if (logout == 1) {
								SessionCache.setCurrentUser(null);
							}
							break;
						default:
							System.out.println("Enter a number between 1 to 6");
							break;
						}

					}
				}
				break;
			
			case 3:
				userDao.getAllUsers().forEach(System.out::println);
			break;
			case 4:
				System.out.print("Enter Id of the customer to remove: ");
				id = s.nextInt();
				User u = userDao.getUser(id);
				userDao.removeUser(u);
			break;
			case 5:
				System.out.print("Enter Id of the customer to Update: ");
				id = s.nextInt();
				System.out.print("Enter First Name to Update :");
				fname = s.next();
				System.out.print("Enter Last Name to Update:");
				lname = s.next();
				System.out.print("Enter Password to Update:");
				password = s.next();
				User updatedUser = new User();
				updatedUser.setId(id);
				updatedUser.setFirstName(fname);
				updatedUser.setLastName(lname);
				updatedUser.setPassword(password);
				userDao.updateUser(updatedUser);
				
				break;
			case 6:
				System.out.println("Thanks for using Lidders Bank");
			System.exit(0);
			
			break;
			default:
			break;
			}
		}
		s.close();
	}
}
	
			

		
	


	
