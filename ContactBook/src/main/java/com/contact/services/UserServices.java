package com.contact.services;

import java.util.List;

import com.contact.exception.PhoneNumberInUseException;
import com.contact.exception.UemailInUseException;
import com.contact.model.Logging;
import com.contact.model.User;

public interface UserServices {
	List<User> getAllUsers();
	void saveUser(User user) throws PhoneNumberInUseException, UemailInUseException,Exception ;
	
	User getUser(int uid);
	void deleteUserByUid(int uid);
	int userByPhoneNumber(long phoneNumber);
	User findByPhoneNumberAndPasswd(long phoneNumber,String pwd);
	boolean validateCredentials(long phn, String Password);
	
	void loggingDelete();
	int insertLogging(long lPhoneNumber,String passwd,int user_id);
	//void deleteByLPhoneNumber(long phoneNumber);
	void loggerDeleteAll();
	//Logging getLoggingDetails();
	int uidByLid();
	
	Logging getLoggingDetails();
	//Logging findByLphoneNumber(long phnnum);
}
