package com.girnarsoft.emstwo.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class DisplayUnderEmployeeController {

	@Autowired
	private ApplicationService applicationService;
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/displayunderemployeepage")
	public String showAddMentorPage(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			List<Map<String,Object>> underEmployees = applicationService.displayUnderEmployee(employee.getEmployeeId());
			
			if(underEmployees == null || underEmployees.isEmpty())
			{
				model.put("message","You don't have any any employee who works under you Yet!!");
			}
			//model.addObject("employeeId",employeeId);
			model.put("underEmployees", underEmployees);
			//model.setViewName("displayunderemployee");
			return "displayunderemployee";
		} catch(Exception ex) {
			System.out.println("her");
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
		
	}
	
}
