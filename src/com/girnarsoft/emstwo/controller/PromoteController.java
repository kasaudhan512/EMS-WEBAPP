package com.girnarsoft.emstwo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.girnarsoft.emstwo.service.ApplicationService;
import com.sun.javafx.sg.prism.NGShape.Mode;

@Component
@Controller
public class PromoteController {

	@Autowired
	private ApplicationService applicationService;
	private int employeeId;
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/promotepage")
	public ModelAndView showPromotePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			model.addObject("employeeId",employeeId);
			model.setViewName("promote");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
	
	@RequestMapping("/welcomepage")
	public ModelAndView showWelcomePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			String firstName = applicationService.getFirstName(employeeId);
			String lastName = applicationService.getLastName(employeeId);
			String desgination = applicationService.getDesignation(employeeId);
			model.addObject("firstName",firstName);
			model.addObject("lastName",lastName);
			model.addObject("employeeId",employeeId);
			model.addObject("designation", desgination);
			model.setViewName("welcome");
			return model;	
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;		}
		
	}
	
	@RequestMapping("/promote")
	public ModelAndView promote(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			int promotingEmployeeId = Integer.parseInt(request.getParameter("promoteEmployeeId").toString());
			String upperDesignation = request.getParameter("designation");
			String message = applicationService.promoteEmployee(employeeId,promotingEmployeeId,upperDesignation);
			model.addObject("message", message);
			model.addObject("employeeId",employeeId);
			model.setViewName("promote");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
	}
}
