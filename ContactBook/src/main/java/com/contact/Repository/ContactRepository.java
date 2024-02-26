package com.contact.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.model.Contact;
import com.contact.model.User;

import jakarta.transaction.Transactional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	
	
	
	@Query(value="select * from contact_list  where cuid=? ",nativeQuery = true)
	List<Contact> findByLid(int id);
	
	void deleteBycId(int cid);
	
	@Modifying
    @Transactional
	@Query(
	          value = "DELETE FROM contact_list WHERE cid = (?1)",
	          nativeQuery = true)
	void jdeleteContact(int cid);
	
	@Query(value="SELECT * FROM contact_list  WHERE c_name LIKE :prefix% And cuid=:cuid",nativeQuery=true)
	  List<Contact> findByPrefix(@Param("prefix") String prefix,@Param("cuid") int id);
	
	//List<Contact> findByCNameContainingAndUser(String keyword,User user);
}
