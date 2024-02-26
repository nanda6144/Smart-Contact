package com.contact.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.contact.exception.PhoneNumberInUseException;
import com.contact.exception.UemailInUseException;
import com.contact.model.User;
import com.contact.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserServices userServices;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/log")
	public String getLogin(){
		logger.info("The user was sent to the login page.");
		return "userlogin";
	}
	@GetMapping("/register")
	public String helloPage() {
		logger.info("The user was sent to the Register page.");
		return "userregister";
	}
	@GetMapping("/")
	public String viewHomePage() {
		logger.info("User went to the Home page.");
		return "Userhome";
	}
	
	@PostMapping("/ProcessRegister")
	public ModelAndView addUser(@ModelAttribute("user") User user,Model model) throws PhoneNumberInUseException,UemailInUseException,Exception{
		ModelAndView modelAndView = new ModelAndView();
		try {
            userServices.saveUser(user);
            model.addAttribute("user",user);
            modelAndView.setViewName("userloginsuccess");
            logger.info("User successfully registered"+user.getUName());
        } catch (PhoneNumberInUseException ex) {
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMessage", ex.getMessage());
            logger.info(user.getUName()+"User has an Error");
        } catch (UemailInUseException ex) {
           	modelAndView.setViewName("error");
            modelAndView.addObject("errorMessage", ex.getMessage());
            logger.info(user.getUName()+"User has an Error");
        
        } catch(Exception ex) {
        	modelAndView.setViewName("error");
        	modelAndView.addObject("errorMessage", ex.getMessage());
        	logger.info(user.getUName()+"User has an Error");
        	}
		 return modelAndView;
		
	}

	@PostMapping("/check")
	public String validate(@RequestParam("phoneNumber")Long PhoneNumber ,@RequestParam("passwd") String passwd , Model model,HttpSession session) {
		if(userServices.validateCredentials(PhoneNumber,passwd)){
			User u = userServices.findByPhoneNumberAndPasswd(PhoneNumber, passwd);
			//session.setAttribute("usersDetails", u);
			userServices.loggingDelete();
			userServices.insertLogging(PhoneNumber, passwd,u.getUid());
			logger.info(u.getUName()+ " Logged In");
		return "contacthome";
		}
		logger.error("The provided credentials are not valid.");
		return "InvalidCredentials";
		}
	
	 @GetMapping("/delete")
	 public String deleteLogg() {
		 int u=userServices.uidByLid();
		 User user=userServices.getUser(u);
		 this.userServices.loggingDelete();
		 logger.info(user.getUName()+" is Logged Out");
		 return "userLogout";
	 }
	 
	 @GetMapping("/getProfile")
	 public String getProfile(Model model) {
		int u = userServices.uidByLid();
		User user=userServices.getUser(u);
		model.addAttribute("user",user);
		return "contactprofile";
	 }
	 
	 @GetMapping("/help")
	 public String helpbox() {
		 return "UserContactus";
	 }
	 @GetMapping("/helpsuccess")
	 public String succhelp() {
		 return "queries";
	 }
	 
	 
	 
	 
	 
}
