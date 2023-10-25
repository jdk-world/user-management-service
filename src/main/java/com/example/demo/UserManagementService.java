package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserManagementService {
	@Autowired
	JdbcTemplate jdbc;

	public List<Employee> getAllEmp() {


		String sql = "SELECT DISTINCT roll_no, emp_name, emp_region, e_mail_id  FROM springbootdb.Employee";

		List<Employee> emps = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Employee obj = new Employee();
			obj.setRoll_no(((Integer) row.get("roll_no")));

			obj.setEmp_name(((String) row.get("emp_name")).toString());
			obj.setEmp_region(((String) row.get("emp_region")).toString());
			//obj.setApplicable_patch_id(row.get("applicable_patch_id")+"");
			//obj.setPatch_compliance(row.get("patch_compliance").toString());
			//obj.setDate_of_completion(row.get("date_of_completion").toString());

			obj.setE_mail_id(((String) row.get("e_mail_id")).toString());

			emps.add(obj);
		}

		return emps;

	}
	public List<Admin> getAllAdmin() {

		String sql = "SELECT * FROM ADMIN";

		List<Admin> admins = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Admin obj = new Admin();
			obj.setRoll_no(((Integer) row.get("roll_no")));

			obj.setEmp_name(((String) row.get("emp_name")).toString());
			obj.setEmp_region(((String) row.get("emp_region")).toString());
			obj.setOrg_role(((String) row.get("org_role")).toString());
			obj.setE_mail_id(row.get("e_mail_id").toString());

			admins.add(obj);
		}

		return admins;

	}

	public List<Regions> getAllRegions() {

		String sql = "SELECT * FROM REGION";

		List<Regions> regions = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Regions obj = new Regions();
			Long id = (Long) row.get("region_id");
			obj.setRegion_id(((Integer) id.intValue()));

			obj.setRegion_name(((String) row.get("region_name")).toString());

			regions.add(obj);
		}

		return regions;

	}

	public List<Roles> getAllRoles() {

		String sql = "SELECT * FROM IT_ADMIN_ROLE";

		List<Roles> roles = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Roles obj = new Roles();
			Long roleId = (Long) row.get("role_id");
			obj.setRole_id(((Integer) roleId.intValue()));

			obj.setRole_name(((String) row.get("role_name")).toString());

			roles.add(obj);
		}

		return roles;

	}


	
	public String addEmp(Employee emp) {

		String query = "INSERT INTO `springbootdb`.`Employee` (`roll_no`, `emp_name`, `emp_region`, `applicable_patch_id`, `patch_compliance`, `date_of_completion`, `e_mail_id`) VALUES ('"
				+ emp.getRoll_no() + "', '" + emp.getEmp_name() + "', '" + emp.getEmp_region() + "', '"
				+ emp.getApplicable_patch_id() + "', '" + emp.getPatch_compliance() + "', '"
				+ emp.getDate_of_completion() + "', '" + emp.getE_mail_id() + "');";

		jdbc.execute(query);
		return "Employee entry created Successfully";
	}

	
	
	public String addRegion(Regions region) {

		String query = "INSERT INTO `springbootdb`.`REGION` (`region_id`, `region_name`) VALUES ('"
				+ region.getRegion_id() + "', '" + region.getRegion_name() + "');";

		jdbc.execute(query);
		return "Region entry created Successfully";
	}

	public String addRole(Roles role) {

		String query = "INSERT INTO `springbootdb`.`IT_ADMIN_ROLE` (`role_id`, `role_name`) VALUES ('"
				+ role.getRole_id() + "', '" + role.getRole_name() + "');";

		jdbc.execute(query);
		return "Region entry created Successfully";
	}
	public String removeEmp(Employee emp) {

		String query= "DELETE from `springbootdb`.`Employee` WHERE roll_no = "+emp.getRoll_no()+";";

		jdbc.execute(query);
		return "Employee entry deleted Successfully";
	}

	public String addAdmin(Admin emp) {

		String query = "INSERT INTO `springbootdb`.`ADMIN` (`roll_no`, `emp_name`, `emp_region`, `org_role`, `e_mail_id`) VALUES ('"
				+ emp.getRoll_no() + "', '" + emp.getEmp_name() + "', '" + emp.getEmp_region() + "', '"
				+ emp.getOrg_role() + "', '" + emp.getE_mail_id()  + "');";

		jdbc.execute(query);
		return "Admin entry created Successfully";
	}

	public String removeAdmin(Admin emp) {

		String query= "DELETE from `springbootdb`.`ADMIN` WHERE roll_no = "+emp.getRoll_no() +";";

		jdbc.execute(query);
		return "Admin entry deleted Successfully";
	}
		

}
