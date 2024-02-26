package com.contact.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.contact.model.Contact;
import com.contact.model.User;
import com.contact.services.ContactService;
import com.contact.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {
	@Autowired
	private ContactService contactService;
	@Autowired
	private UserServices userServices;
	
	Logger logger=LoggerFactory.getLogger(ContactController.class);
	
	
	
	@GetMapping("/add")
	public String contactPage() {
		if(userServices.getLoggingDetails()!=null) {
			logger.info("An addition of contact was requested by the user.");
			return "contactadd";
		}else {
			logger.error("There was no active user discovered.");
			return "redirect:/";
		}
		
	}
	@GetMapping("/showContacts")
	public String show(Model model) {
		if(userServices.getLoggingDetails()!=null) {
			int u = userServices.uidByLid();
			User user=userServices.getUser(u);
			List<Contact> contact = contactService.contactByLid(u);
			//List<Contact> contacts = contactService.getAllContacts();
			model.addAttribute("contacts", contact);
			logger.info(user.getUName()+ " saw every one of his contacts.");

			return "contactcrud";
			
		}
		else {
			logger.error("There was no active user discovered.");
			return "redirect:/'";
		}
		//session.getAttribute()
		
	}
	
	@PostMapping("/addCont")
	public ModelAndView addCOntact(@ModelAttribute("contact") Contact contact,HttpSession session){
		
		if(userServices.getLoggingDetails()!=null) {
			
			int u = userServices.uidByLid();
			User user= userServices.getUser(u);
			contact.setUser(user);
			user.getContacts().add(contact);
			this.contactService.saveContact(contact);
			logger.info(user.getUName()+"has saved "+contact.getCName());
			return new ModelAndView("redirect:/showContacts");
			
		}
		else {
			logger.error("There was no active user discovered.");
			return new ModelAndView("redirect:/");
		}
		
	}
	@GetMapping("/showFormForUpdate/{cid}")
	public String showFormForUpdate(@PathVariable ( value = "cid") int cid, Model model) {
		
		if(userServices.getLoggingDetails()!=null) {
			Contact contact = contactService.getContactByCid(cid);
			
			model.addAttribute("contact", contact);
			logger.info(contact.getCName()+" is sent to an updated contact page.");
			return "contactupdate";
			
		}
		else {
			logger.error("There was no active user discovered.");
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/deleteEmployee/{cid}")
	public String deleteEmployee(@PathVariable (value = "cid") int cid) {
		// call delete employee method
		if(userServices.getLoggingDetails()!=null) {
			Contact contact=contactService.getContact(cid);
			
			this.contactService.contactDelete(cid);
			return "redirect:/showContacts";
			
		}
		else {
			logger.error("There was no active user discovered.");
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/backs")
	public String back() {
		return "contacthome";
	}
	
	@GetMapping("/searchContacts")
	public String searchContact(@RequestParam("prefix") String prefix, Model model) {
		
		if(userServices.getLoggingDetails()!=null) {
			int u=userServices.uidByLid();
			User user=userServices.getUser(u);
			
			
			List<Contact> contacts = contactService.searchPrefix(prefix,u);
			model.addAttribute("contacts", contacts);
			logger.info(user.getUName()+" searched for "+prefix +"in his contact list");
			return "contactcrud";
			
		}
		else {
			logger.error("No Active User Found");
			return "redirect:/";
		}
		
	}
	
}
