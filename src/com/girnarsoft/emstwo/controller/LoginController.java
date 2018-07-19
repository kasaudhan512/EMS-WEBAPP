package com.girnarsoft.emstwo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.girnarsoft.emstwo.beans.Employee;
import com.girnarsoft.emstwo.service.*;;

@Component
@Controller
@SessionAttributes("employee")
public class LoginController {

	@Autowired
	private ApplicationService applicationService;
	
	public ApplicationService getApplicationService() {
		return applicationService;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@ModelAttribute("employee")
	public Employee setUpEmployeeId() {
		return new Employee();
	}
	
	@RequestMapping("/")
	public String welcome() {
		return "index";
	}
	@RequestMapping(value="login", method = RequestMethod.POST)
	public String login(@ModelAttribute("employee") Employee employee, HttpServletRequest request, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		
		System.out.println("welco");
		try {
			//System.out.println(request.getParameter("employeeId"));
			
			System.out.println(employee.getEmployeeId());
			System.out.println(employee.getPin());
			//int pin = Integer.parseInt(request.getParameter("passWord"));
			if(applicationService.isEmployeeExist(employee.getEmployeeId()))
			{
				if(applicationService.getPin(employee.getEmployeeId()) == employee.getPin())
		  		{
					//employeeId = Integer.parseInt(request.getParameter("employeeId"));
					//Employee employee= applicationService.getEmployee(employeeId);
					//String firstName = applicationService.getFirstName(employeeId);
					//String lastName = applicationService.getLastName(employeeId);
					String desgination = applicationService.getDesignation(employee.getEmployeeId());
					employee.setFirstName(applicationService.getFirstName(employee.getEmployeeId()));
					employee.setLastName(applicationService.getLastName(employee.getEmployeeId()));
					employee.setDesignationId(applicationService.getDesignationId(employee.getEmployeeId()));
					//model.put("firstName",firstName);
					//model.put("lastName",lastName);
					//model.put("employeeId",employeeId);
					model.put("designation", desgination);
					//request.getSession().setAttribute("employeeId", employeeId);
					System.out.println("--" + request.getSession().getAttribute("employee"));
					return "welcome";
				}
				else
				{
					model.put("message", "Worng Credential, Please Enter Correct Details");
					return "index";
				}
			}
			else
			{
				model.put("message", "Worng Credential, Please Enter Correct Details");
				return "index";
			}
			
		} catch(Exception ex) {
			model.put("message", "Something went Wrong Please LogIn !!");
			return "index";
		}
		
	}
	@RequestMapping("/signuppage")
	public String showSignUpPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		//model.setViewName("signup");
		return "signup";
	}
	
	@RequestMapping("/loginpage")
	public String showLoginPage(ModelMap model) {
		//ModelAndView model = new ModelAndView();
		//model.setViewName("index");
		return "index";
	}
	
	@RequestMapping("/signup")
	public String signup(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//ModelAndView model = new ModelAndView();
		try {
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String designation = request.getParameter("designation");
			int passWord = Integer.parseInt(request.getParameter("passWord"));
			String message = applicationService.addEmployee(firstName, lastName, designation, passWord);
			model.put("message",message);
			//model.setViewName("index");
			return "index";
		} catch(Exception ex)
		{
			model.put("message", "Something went Wrong Please LogIn !!");
			//model.setViewName("index");
			return "index";
		}
		
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, ModelMap model) {
        System.out.println("logout()");
        //HttpSession httpSession = request.getSession();
        //httpSession.invalidate();
        //request.getSession(false);
        //request.getSession().setAttribute("employeeId", null);
        request.getSession().removeAttribute("employee");
        System.out.println((request.getSession().getAttribute("employee")));
        //System.out.println(model.get("employeeId"));
        
        model.put("message","Successfully Logged Out");
        return "index";
    }
	
	
	/*public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		/*try {
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
		return model;
		
	}*/
}
