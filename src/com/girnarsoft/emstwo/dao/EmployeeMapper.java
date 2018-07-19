package com.girnarsoft.emstwo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.girnarsoft.emstwo.beans.Employee;

public class EmployeeMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		Employee employee = new Employee();
		employee.setEmployeeId(resultSet.getInt("emp_id"));
		employee.setFirstName(resultSet.getString("first_name"));
		employee.setLastName(resultSet.getString("last_name"));
		employee.setDesignationId(resultSet.getInt("dep_id"));
		employee.setPin(resultSet.getInt("pin"));
		
		return employee;
	}

}
