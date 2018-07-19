package com.girnarsoft.emstwo.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class DisplayMentorController {
	
	@Autowired
	private ApplicationService applicationService;
	
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/displaymentorpage")
	public String showAddMentorPage(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			List<Map<String,Object>> mentors = applicationService.displayMentor(employee.getEmployeeId());
			
			if(mentors == null || mentors.isEmpty())
			{
				System.out.println("empty");
				model.put("message","You don't have any Mentor Yet!!");
			}
			//model.addObject("employeeId",employeeId);
			model.put("mentors", mentors);
			//model.setViewName("displaymentor");
			return "displaymentor";
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
		
	}

}
