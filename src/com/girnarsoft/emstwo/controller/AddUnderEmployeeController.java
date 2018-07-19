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
public class AddUnderEmployeeController {

	@Autowired
	private ApplicationService applicationService;
	
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@RequestMapping("/addunderemployeepage")
	public String showAddUnserEmployeePage(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		
		try {
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			//model.addObject("employeeId",request.getAttribute("employeeId"));
			//model.addObject("employeeId",employeeId);
			//model.setViewName("addunderemployee");
			String message1 = applicationService.underEmployeeDetail(employee.getEmployeeId());
			model.put("message1", message1);
			return "addunderemployee";
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
	}
	
	@RequestMapping("/addunderemployee")
	public String addUnderEmployee(@SessionAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			int underEmployeeId = Integer.parseInt(request.getParameter("underEmployeeId").toString());
			//employeeId = Integer.parseInt(request.getParameter("employeeId").toString());
			String message2 = applicationService.addUnderEmployee(employee.getEmployeeId(),underEmployeeId);
			model.put("message2", message2);
			//model.addObject("employeeId",employeeId);
			//model.setViewName("addunderemployee");
			return "addunderemployee";
			
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
		
	}
	
}
