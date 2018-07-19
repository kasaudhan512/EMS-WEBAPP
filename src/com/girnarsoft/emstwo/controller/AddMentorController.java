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

@Component
@Controller
@SessionAttributes("employeeId")
public class AddMentorController {

	@Autowired
	private ApplicationService applicationService;
	
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/addmentorpage")
	public String showAddMentorPage(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			String message1 = applicationService.mentorDetail(employee.getEmployeeId());
			//model.addObject("employeeId",employeeId);
			model.put("message1", message1);
			//model.setViewName("addmentor");
			return "addmentor";
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
		
	}
	
	@RequestMapping("/addmentor")
	public String addMentor(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		//ModelAndView model = new ModelAndView();
		try {
			int mentorId = Integer.parseInt(request.getParameter("mentorId").toString());
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			String message2 = applicationService.addMentor((int)model.get("employeeId"),mentorId);
			//model.addObject("employeeId", employeeId);
			model.put("message2", message2);
			//model.setViewName("addmentor");
			return "addmentor";
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
		
	}
	
	
}
