package com.girnarsoft.emstwo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.girnarsoft.emstwo.service.ApplicationService;

@Component
@Controller
public class AddUnderEmployeeController {

	@Autowired
	private ApplicationService applicationService;
	private int employeeId;
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/addunderemployeepage")
	public ModelAndView showAddUnserEmployeePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		
		try {
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			model.addObject("employeeId",employeeId);
			model.setViewName("addunderemployee");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
	}
	
	@RequestMapping("/addunderemployee")
	ModelAndView addUnderEmployee(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			int underEmployeeId = Integer.parseInt(request.getParameter("underEmployeeId").toString());
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			String message = applicationService.addUnderEmployee(employeeId,underEmployeeId);
			model.addObject("message", message);
			model.addObject("employeeId",employeeId);
			model.setViewName("addunderemployee");
			
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
	
}
