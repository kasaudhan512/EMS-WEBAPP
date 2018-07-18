package com.girnarsoft.emstwo.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class DisplayUnderEmployeeController {

	@Autowired
	private ApplicationService applicationService;
	private int employeeId;
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/displayunderemployeepage")
	public ModelAndView showAddMentorPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			List<Map<String,Object>> underEmployees = applicationService.displayUnderEmployee(employeeId);
			
			if(underEmployees == null || underEmployees.isEmpty())
			{
				System.out.println("empty");
				model.addObject("message","You don't have any any employee who works under you Yet!!");
			}
			model.addObject("employeeId",employeeId);
			model.addObject("underEmployees", underEmployees);
			model.setViewName("displayunderemployee");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
	
}
