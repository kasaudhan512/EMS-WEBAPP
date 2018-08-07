package com.girnarsoft.emstwo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.girnarsoft.emstwo.beans.Employee;
import com.girnarsoft.emstwo.dao.ApplicationDao;

@Component
public class ApplicationService {

	@Autowired
	ApplicationDao applicationDao;

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
	
	public boolean isEmployeeExist(int employeeId)
	{
		int count = applicationDao.isEmployeeExist(employeeId);
		if(count <=0 )
		{
			return false;
		}
		return true;
	}
	
	public int getPin(int employeeId) {
		int pin = applicationDao.getPin(employeeId);
	
		return pin;
	}
	
	public String addEmployee(String firstName, String lastName, String designation, int pin) {
		boolean flag = false;
		String returnData = "";
		try {
			if(designation.equals("CEO"))
			{
				if(!applicationDao.isCEOExist())
				{
					applicationDao.insertEmployee(firstName, lastName, designation, pin);
					returnData = "You have successfully Signed Up. Your Autogenerate Id is " + getNewUserId();
				}
				else
				{
					returnData = "Ceo Already Exist in The database";
				}
			}
			else
			{
				applicationDao.insertEmployee(firstName, lastName, designation, pin);
				returnData = "You have successfully Signed Up. Your Autogenerate Id is " + getNewUserId();
			}
		} catch (Exception e) {
			flag = false;
		}
		return returnData;
	}
	
	public String getFirstName(int employeeId) {
		return applicationDao.getFirstName(employeeId);
	}
	
	public String getLastName(int employeeId) {
		return applicationDao.getLastName(employeeId);
	}
	
	public String getDesignation(int employeeId)
	{
		return applicationDao.getDesignation(employeeId);
	}
	
	public int getDesignationId(int employeeId) {
		return applicationDao.getDesignationId(employeeId);
	}
	
	public int getNewUserId() {
		return applicationDao.getNewUserId();
	}
	
	public String promoteEmployee(int employeeId, int promotingEmployeeId, String upperDesignation) {
		String returnData="";
		
		try {
			int count = applicationDao.isEmployeeExist(promotingEmployeeId);
			if(count <= 0)
			{
				returnData = "No employee Exist With Given Employee Id";
			}
			else
			{
				String employeeDesignation = applicationDao.getDesignation(employeeId);
				String promotingEmployeeDesignation = applicationDao.getDesignation(promotingEmployeeId);
				if(employeeDesignation.equals("New Joiner"))
				{
					returnData = "You Are a newbie. You can not Promote Any Employee";
				}
				else if(employeeDesignation.equals("Manager"))
				{
					
					if(promotingEmployeeDesignation.equals("New Joiner"))
					{
						returnData = promoteNewJoiner(promotingEmployeeId, upperDesignation);
					}
					else
					{
						returnData = "You are not Authorized to promote a " + promotingEmployeeDesignation;
					}
				}
				else if(employeeDesignation.equals("Director") || employeeDesignation.equals("HR")) 
				{
					if(promotingEmployeeDesignation.equals("New Joiner"))
					{
						returnData = promoteNewJoiner(promotingEmployeeId,upperDesignation);
					}
					else if(promotingEmployeeDesignation.equals("Manager"))
					{
						returnData = promoteManager(promotingEmployeeId, upperDesignation);
					}
					else
					{
						returnData = "You are not Authorized to promote a " + promotingEmployeeDesignation;
					}
				}
				else if(employeeDesignation.equals("CEO")) 
				{
					if(promotingEmployeeDesignation.equals("New Joiner"))
					{
						returnData = promoteNewJoiner(promotingEmployeeId,upperDesignation);
					}
					else if(promotingEmployeeDesignation.equals("Manager"))
					{
						returnData = promoteManager(promotingEmployeeId, upperDesignation);
					}
					else if(promotingEmployeeDesignation.equals("HR"))
					{
						returnData = applicationDao.getFirstName(promotingEmployeeId) +" "+applicationDao.getLastName(promotingEmployeeId) + " is HR of The Company. And HR can Not be promoted.";
					}
					else if(promotingEmployeeDesignation.equals("Director"))
					{
						returnData = applicationDao.getFirstName(promotingEmployeeId) +" "+applicationDao.getLastName(promotingEmployeeId) +" is Director of The Company. And Director can Not be promoted.";
					}
					else
					{
						returnData = applicationDao.getFirstName(promotingEmployeeId) + " " + applicationDao.getLastName(promotingEmployeeId) + " is the CEO of the Company. And CEO can not be promoted.";
					}
						
				}
				
			}
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return "exception Thown";
		}
		
		return returnData;
		
	}
	
	public String promoteManager(int promotingEmployeeId, String upperDesignation) {
		String returnData ="";
		if(upperDesignation.equals("Director") )
		{
			applicationDao.promoteEmployee(promotingEmployeeId, upperDesignation);
			returnData = "You have Successfully Promoted " + applicationDao.getFirstName(promotingEmployeeId) + " " + applicationDao.getLastName(promotingEmployeeId) + " " + "as Director";
		}
		else
		{
			returnData = "You can not promote a Manager as a " + upperDesignation;
		}
		
		return returnData;
	}
	
	public String promoteNewJoiner(int promotingEmployeeId, String upperDesignation) {
		String returnData ="";
		if(upperDesignation.equals("Manager"))
		{
			applicationDao.promoteEmployee(promotingEmployeeId, upperDesignation);
			returnData = "You have Successfully Promoted " + applicationDao.getFirstName(promotingEmployeeId) + " " + applicationDao.getLastName(promotingEmployeeId) + " " + "as Manager";
		}
		else
		{
			returnData = "You can not promote a new joiner as a " + upperDesignation;
		}
		return returnData;
	}
	
	public String addMentor(int employeeId, int mentorId) {
		String returnData = "";
		int count = applicationDao.isEmployeeExist(mentorId);
		if(count <= 0)
		{
			returnData = "No employee Exist With Given Mentor Id";
		}
		else
		{
			String employeeDesignation = applicationDao.getDesignation(employeeId);
			String mentorDesignation = applicationDao.getDesignation(mentorId);
			if((employeeDesignation.equals("New Joiner") && !mentorDesignation.equals("New Joiner")) || 
			(employeeDesignation.equals("Manager") && !mentorDesignation.equals("Manager") && !mentorDesignation.equals("New Joiner")) || 
			(employeeDesignation.equals("Director") && !mentorDesignation.equals("Director") && !mentorDesignation.equals("Manager") && !mentorDesignation.equals("New Joiner")) || 
			(employeeDesignation.equals("HR") && !mentorDesignation.equals("HR") && !mentorDesignation.equals("Manager") && !mentorDesignation.equals("New Joiner")) || 
			(employeeDesignation.equals("CEO") && !mentorDesignation.equals("CEO") && !mentorDesignation.equals("Director") && !mentorDesignation.equals("HR") && !mentorDesignation.equals("Manager") && !mentorDesignation.equals("New Joiner")))
			{
				applicationDao.updateMentor(employeeId, mentorId);
				returnData = "	" + applicationDao.getFirstName(mentorId) + " " + applicationDao.getLastName(mentorId) + " " + "as Your Mentor";
			}
			else
			{
				returnData = "You can not add your colleague, Junior or Yourself as your mentor.<br>";
				returnData += "Employee ID " + mentorId + " " + applicationDao.getFirstName(mentorId) + " " + applicationDao.getLastName(mentorId) + " is the " + applicationDao.getDesignation(mentorId) + " of the company";
			}
			
		}
		return returnData;
		
	}
	
	public String addUnderEmployee(int employeeId,int underEmployeeId) {
		String returnData = "";
		int count = applicationDao.isEmployeeExist(underEmployeeId);
		if(count <= 0)
		{
			returnData = "No employee Exist With Given Employee Id";
		}
		else
		{
			String employeeDesignation = applicationDao.getDesignation(employeeId);
			String underEmployeeDesignation = applicationDao.getDesignation(underEmployeeId);
			if ((employeeDesignation.equals("CEO") && !underEmployeeDesignation.equals("CEO")) || 
				(employeeDesignation.equals("HR") && !underEmployeeDesignation.equals("CEO") && !underEmployeeDesignation.equals("Director") && !underEmployeeDesignation.equals("HR")) || 
				(employeeDesignation.equals("Director") && !underEmployeeDesignation.equals("CEO") && !underEmployeeDesignation.equals("HR") && !underEmployeeDesignation.equals("Director")) || 
				(employeeDesignation.equals("Manager") && !underEmployeeDesignation.equals("CEO") && !underEmployeeDesignation.equals("HR") && !underEmployeeDesignation.equals("Director") && !underEmployeeDesignation.equals("Manager")) || 
				(employeeDesignation.equals("New Joiner") && !underEmployeeDesignation.equals("CEO") && !underEmployeeDesignation.equals("Director") && !underEmployeeDesignation.equals("HR") && !underEmployeeDesignation.equals("Manager") && !underEmployeeDesignation.equals("New Joiner")))
			{
				applicationDao.updateMentor(underEmployeeId, employeeId);
				returnData = "You have Successfully Added " + applicationDao.getFirstName(underEmployeeId) + " " + applicationDao.getLastName(underEmployeeId) + " " + "as Employee Who Works Under You";
			}
			else
			{
				returnData = "You can not add your colleague, Senior or Yourself as your as Employee Who works under you.<br>";
				returnData += "Employee ID " + underEmployeeId + " " + applicationDao.getFirstName(underEmployeeId) + " " + applicationDao.getLastName(underEmployeeId) + " is the " + applicationDao.getDesignation(underEmployeeId) + " of the company";
			}
		
		}
		return returnData;	
	}
	
	public String mentorDetail(int employeeId) {
		int count = applicationDao.isMentorExist(employeeId);
		String returnData;
		if(count <= 0)
		{
			returnData = "You don't have any Mentor Yet. Please Add Your Mentor";
		}
		else
		{
			returnData = applicationDao.getMentorFirstName(employeeId) + " " + applicationDao.getMentorLastName(employeeId) + " is Already your mentor. If you want to Update Please fill the details";
		}
		return returnData;
	}
	
	public String underEmployeeDetail(int employeeId) {
		int count = applicationDao.isUnderEmployeeExist(employeeId);
		String returnData = ""; 
		if(count <= 0)
		{
			returnData = "There is No Employee Who works under you yet.";
		}
		else
		{
			returnData = "";
		}
		return returnData;
	}
	
	public List<Map<String,Object>> displayMentor(int employeeId) {
		
		List<Map<String,Object>> mentors = applicationDao.getMentor(employeeId); 
		return mentors;
		
	}
	
	public List<Map<String, Object>> displayUnderEmployee(int employeeId) {
		
		List<Map<String,Object>> underEmployees = applicationDao.getUnderEmployees(employeeId);
		return underEmployees;
	}
	
//	public Employee getEmployee(int employeeId) {
//		Employee employee= null;
//		List<Employee> employees = applicationDao.getEmployee(employeeId);
//		for(Employee employee1 : employees)
//			employee = employee1;
//		return employee;
//		
//	}
	
	
}
