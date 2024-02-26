package com.contact.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.contact.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	
	User findByPhoneNumber(long phoneNumber);
	User findByUemail(String uemail);
	
	User findByPhoneNumberAndPasswd(long phoneNumber,String pwd);
	
	@Modifying
    @Transactional
	@Query(value="select s.uid from user_login s where s.phone_number = ?1",nativeQuery = true)
    int userByPhoneNumber(long phoneNumber);
	
}
