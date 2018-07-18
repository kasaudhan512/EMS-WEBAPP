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
public class AddMentorController {

	@Autowired
	private ApplicationService applicationService;
	private int employeeId;
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/addmentorpage")
	public ModelAndView showAddMentorPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			String message1 = applicationService.mentorDetail(employeeId);
			model.addObject("employeeId",employeeId);
			model.addObject("message1", message1);
			model.setViewName("addmentor");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
	
	@RequestMapping("/addmentor")
	public ModelAndView addMentor(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView model = new ModelAndView();
		try {
			int mentorId = Integer.parseInt(request.getParameter("mentorId").toString());
			employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			String message2 = applicationService.addMentor(employeeId,mentorId);
			model.addObject("employeeId", employeeId);
			model.addObject("message2", message2);
			model.setViewName("addmentor");
			return model;
		} catch(Exception ex) {
			model.addObject("message", "Something went Wrong Please LogIn !!");
			model.setViewName("index");
			return model;
		}
		
	}
	
	
}
