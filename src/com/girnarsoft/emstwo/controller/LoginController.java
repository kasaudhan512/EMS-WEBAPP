package com.girnarsoft.emstwo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.girnarsoft.emstwo.beans.Employee;
import com.girnarsoft.emstwo.service.*;;

@Component
@Controller
public class LoginController {

	@Autowired
	private ApplicationService applicationService;
	private int employeeId;
	
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/")
	public String welcome() {
		return "index";
	}
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			int employeeId = Integer.parseInt(request.getParameter("employeeId"));
			int pin = Integer.parseInt(request.getParameter("passWord"));
			if(applicationService.isEmployeeExist(employeeId))
			{
				if(applicationService.getPin(employeeId) == pin)
				{
					//Employee employee= applicationService.getEmployee(employeeId);
					String firstName = applicationService.getFirstName(employeeId);
					String lastName = applicationService.getLastName(employeeId);
					String desgination = applicationService.getDesignation(employeeId);
					model.addObject("firstName",firstName);
					model.addObject("lastName",lastName);
					model.addObject("employeeId",employeeId);
					model.addObject("designation", desgination);
					model.setViewName("welcome");
				}
				else
				{
					model.addObject("message", "Worng Credential, Please Enter Correct Details");
					model.setViewName("index");
				}
			}
			else
			{
				model.addObject("message", "Worng Credential, Please Enter Correct Details");
				model.setViewName("index");
			}
			
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
	@RequestMapping("/signuppage")
	public ModelAndView showSignUpPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("signup");
		return model;
	}
	
	@RequestMapping("/loginpage")
	public ModelAndView showLoginPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}
	
	@RequestMapping("/signup")
	public ModelAndView signup(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String designation = request.getParameter("designation");
			int passWord = Integer.parseInt(request.getParameter("passWord"));
			String message = applicationService.addEmployee(firstName, lastName, designation, passWord);
			model.addObject("message",message);
			model.setViewName("index");
			return model;
		} catch(Exception ex)
		{
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
		
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			int employeeId1 = Integer.parseInt(request.getParameter("employeeId").toString());
			Integer employeeId = new Integer(employeeId1);
			employeeId = null;
			model.addObject("employeeId",employeeId);
			model.addObject("message","Logged Out");
			model.setViewName("index");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
}
