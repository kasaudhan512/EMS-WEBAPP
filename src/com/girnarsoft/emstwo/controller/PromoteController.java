package com.girnarsoft.emstwo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.girnarsoft.emstwo.beans.Employee;
import com.girnarsoft.emstwo.service.ApplicationService;
import com.sun.javafx.sg.prism.NGShape.Mode;

@Component
@Controller
@SessionAttributes("employeeId")
public class PromoteController {

	@Autowired
	private ApplicationService applicationService;

	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/promotepage")
	public String showPromotePage(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			//model.addObject("employeeId",employeeId);
			//model.setViewName("promote");
			return "promote";
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			return "index";
		}
		
	}
	
	@RequestMapping("/welcomepage")
	public String showWelcomePage(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			//employeeId = model.get("employeeId");
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			System.out.println("**- "+request.getSession().getAttribute("employee"));
			//String firstName = applicationService.getFirstName((int)model.get("employeeId"));
			//String lastName = applicationService.getLastName((int)model.get("employeeId"));
			String desgination = applicationService.getDesignation(employee.getEmployeeId());
			//model.put("firstName",firstName);
			//model.put("lastName",lastName);
			//model.put("employeeId",employeeId);
			model.put("designation", desgination);
			//model.setViewName("welcome");
			return "welcome";	
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";		}
		
	}
	
	@RequestMapping("/promote")
	public String promote(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			int promotingEmployeeId = Integer.parseInt(request.getParameter("promoteEmployeeId").toString());
			String upperDesignation = request.getParameter("designation");
			String message = applicationService.promoteEmployee(employee.getEmployeeId(),promotingEmployeeId,upperDesignation);
			model.put("message", message);
			//model.addObject("employeeId",employeeId);
			//model.setViewName("promote");
			return "promote";
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
	}
}
