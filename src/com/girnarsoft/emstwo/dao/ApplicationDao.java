package com.girnarsoft.emstwo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.girnarsoft.emstwo.beans.Employee;

@Component
public class ApplicationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public boolean isCEOExist() {
		boolean flag = true;
		String sql = "select count(*) from employee where des_id = 1";
		int count = jdbcTemplate.queryForObject(sql,Integer.class);
		if(count <= 0)
		{
			return false;
		}
		return flag;
	}

	public int isEmployeeExist(int employeeId) {
		String sql = "select count(*) from employee where emp_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, Integer.class);
		return count;
	}
	
	public int isMentorExist(int employeeId) {
		String sql = "select mentor_id from employee where emp_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, Integer.class);
		return count;
	}
	
	public List getMentor(int employeeId) {
		String sql = "select mentor_id from employee where emp_id = ?";
		int mentorId = (int) jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, Integer.class);
		sql = "select emp.emp_id, emp.first_name, emp.last_name, des.des_name from employee emp join designation des on emp.des_id = des.des_id where emp.emp_id= ?";
		List mentors = jdbcTemplate.queryForList(sql,mentorId);
		return mentors;
	}
	
	public List getUnderEmployees(int employeeId) {
		String sql = "select emp.emp_id, emp.first_name, emp.last_name, des.des_name from employee emp join designation des on emp.des_id = des.des_id where emp.mentor_id= ?";
		List underEmployees = jdbcTemplate.queryForList(sql,employeeId);
		return underEmployees;
	}

	public String getMentorFirstName(int employeeId) {
		String sql = "select mentor_id from employee where emp_id = ?";
		int mentorId = jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, Integer.class);
		sql = "select first_name from employee where emp_id = ?";
		String firstName = jdbcTemplate.queryForObject(sql, new Object[] { mentorId }, String.class);
		return firstName;
	}
	
	public String getMentorLastName(int employeeId) {
		String sql = "select mentor_id from employee where emp_id = ?";
		int mentorId = jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, Integer.class);
		sql = "select last_name from employee where emp_id = ?";
		String lastName = jdbcTemplate.queryForObject(sql, new Object[] { mentorId }, String.class);
		return lastName;
	}
	
	public void insertEmployee(String firstName, String lastName, String designation, int pin) {
		String sql = "select des_id from designation where des_name = ?";
		int designationId = (int) jdbcTemplate.queryForObject(sql, new Object[] { designation }, Integer.class);
		sql = "insert into employee values(null, ?, ?, ?, ?, NOW(), NOW(), 0)";
		int update = jdbcTemplate.update(sql, firstName, lastName, designationId, pin);
		
	}

	public int getPin(int employeeId) {
		String sql = "select pin from employee where emp_id = ?";
		Object[] input = new Object[] { employeeId };
		int pin = (int) jdbcTemplate.queryForObject(sql, input, Integer.class);
		return pin;
	}

	public String getFirstName(int employeeId) {
		String sql = "select first_name from employee where emp_id = ?";
		String firstName = jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, String.class);
		return firstName;

	}

	public String getLastName(int employeeId) {
		String sql = "select last_name from employee where emp_id = ?";
		String firstName = jdbcTemplate.queryForObject(sql, new Object[] { employeeId }, String.class);
		return firstName;
	}

	public String getDesignation(int employeeId) {
		String sql = "select des_id from employee where emp_id = " + employeeId;
		List<Map<String, Object>> li = jdbcTemplate.queryForList(sql);
		int designationId  = Integer.parseInt(li.get(0).get("des_id").toString());
		/*
		 * int designationId = (int)jdbcTemplate.queryForObject(sql, new Object[]
		 * {employeeId}, Integer.class); System.out.println("desId == " +
		 * designationId);
		 */
		sql = "select des_name from designation where des_id = " + designationId;
		li = jdbcTemplate.queryForList(sql);
		String designationName = li.get(0).get("des_name").toString();
		/*
		 * String designationName = jdbcTemplate.queryForObject(sql, new Object[]
		 * {designationId}, String.class); System.out.println("des-- " +
		 * designationName);
		 */
		return designationName;
	}

	public int getNewUserId() {
		String sql = "select max(emp_id) from employee";
		int newId = (int) jdbcTemplate.queryForObject(sql, Integer.class);
		return newId;
	}

	public void promoteEmployee(int promotingEmployeeId, String upperDesignation) {
		
		String sql = "select des_id from designation where des_name = ?";
		int designationId = jdbcTemplate.queryForObject(sql, new Object[] { upperDesignation }, Integer.class);
		sql = "update employee set des_id = ? where emp_id = ?";
		jdbcTemplate.update(sql, designationId, promotingEmployeeId);

	}
	
	public void updateMentor(int employeeId, int mentorId) {
		String sql = "update employee set mentor_id = ? where emp_id = ?";
		jdbcTemplate.update(sql, mentorId, employeeId);
	}

	// public List<Employee> getEmployee(int employeeId) {
	// String SQL = "select emp_id,first_name, last_name, des_id, pin from
	// employee";
	// List<Employee> employee = jdbcTemplate.query(SQL, new EmployeeMapper());
	// return employee;
	// }

}
