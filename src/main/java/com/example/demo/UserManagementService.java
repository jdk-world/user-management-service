package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManagementService {
	@Autowired
	JdbcTemplate jdbc;

	private static final String DEFAULT_APPLICABLE_PATCH_ID = "1";
	private static final String DEFAULT_PATCH_UNCOMPLIANCE = "Non-compliant";
	public static final String DEFAULT_DATE_UNCOMPLETION = "1900-01-01";//2023-11-02T11:01//2023-10-01

	
	public List<Employee> getAllEmp() {


		String sql = "SELECT DISTINCT roll_no, emp_name, emp_region, e_mail_id  FROM springbootdb.Employee";
		String sql2 = "SELECT roll_no, asset_id FROM springbootdb.EMPLOYEE_ASSET";
		List<Map<String, Object>> rows2 = jdbc.queryForList(sql2);

		HashMap<Integer, String> assetMap = new HashMap<>();

		for (Map<String, Object> row : rows2) {
		    Integer rollNo = ((Integer) row.get("roll_no"));
		    String assetId = row.get("asset_id").toString();
		    assetMap.put(rollNo, assetId);
		}


		List<Employee> emps = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Employee obj = new Employee();
			Integer rollNo = ((Integer) row.get("roll_no"));
			
			obj.setRoll_no(rollNo);

			obj.setEmp_name(((String) row.get("emp_name")).toString());
			obj.setEmp_region(((String) row.get("emp_region")).toString());
			//obj.setApplicable_patch_id(row.get("applicable_patch_id")+"");
			//obj.setPatch_compliance(row.get("patch_compliance").toString());
			//obj.setDate_of_completion(row.get("date_of_completion").toString());

			obj.setE_mail_id(((String) row.get("e_mail_id")).toString());

			obj.setHost_name(assetMap.get(rollNo));
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

	public HashMap<Integer, String> getAllEmpAssetMap() {

		String sql = "SELECT * FROM EMPLOYEE_ASSET";

		HashMap<Integer, String> map = new HashMap<>();
		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Integer roleId = (Integer) row.get("roll_no");

			String asset_id = row.get("asset_id").toString();

			map.put(roleId, asset_id);
		}

		return map;

	}
	
	public String addEmp(Employee emp) {

		String applicable_patch_id = StringUtils.isBlank(emp.getApplicable_patch_id()) ? DEFAULT_APPLICABLE_PATCH_ID : emp.getApplicable_patch_id();
		String patch_compliance = StringUtils.isBlank(emp.getPatch_compliance()) ? DEFAULT_PATCH_UNCOMPLIANCE : emp.getPatch_compliance();		
	    String date_of_completion = StringUtils.isBlank(emp.getDate_of_completion()) ? DEFAULT_DATE_UNCOMPLETION : "'" + emp.getDate_of_completion() + "'";		

	    
	    String query = "INSERT INTO `springbootdb`.`Employee` (`roll_no`, `emp_name`, `emp_region`, `applicable_patch_id`, `patch_compliance`, `date_of_completion`, `e_mail_id`) VALUES ('"
				+ emp.getRoll_no() + "', '" + emp.getEmp_name() + "', '" + emp.getEmp_region() + "', '"
				+ applicable_patch_id + "', '" + patch_compliance + "', '"
				+ date_of_completion + "', '" + emp.getE_mail_id() + "');";

		jdbc.execute(query);
		
		String query2 = "INSERT INTO `springbootdb`.`EMPLOYEE_ASSET` (`roll_no`, `asset_id`) VALUES ('"
				+ emp.getRoll_no() + "', '" + emp.getHost_name() + "');";

		jdbc.execute(query2);

		
		
		return "Employee entry created Successfully, Emp Id : " + emp.getRoll_no();
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

	
	public String removeEmp(List<String> slotIdList) {
	    if (slotIdList.isEmpty()) {
	        return "No admin to delete";
	    }

	    // Create a comma-separated list of slot IDs
	    String slotIds = String.join(",", slotIdList);

	    String query = "DELETE FROM `springbootdb`.`Employee` WHERE roll_no IN (" + slotIds + ");";

	    jdbc.execute(query);

	    return "Employee entries " + slotIdList + " deleted successfully";
	}

	
	

	public String addAdmin(Admin emp) {

		String query = "INSERT INTO `springbootdb`.`ADMIN` (`roll_no`, `emp_name`, `emp_region`, `org_role`, `e_mail_id`) VALUES ('"
				+ emp.getRoll_no() + "', '" + emp.getEmp_name() + "', '" + emp.getEmp_region() + "', '"
				+ emp.getOrg_role() + "', '" + emp.getE_mail_id()  + "');";

		jdbc.execute(query);
		return "Admin entry created Successfully";
	}



	public String removeAdmin(List<String> slotIdList) {
	    if (slotIdList.isEmpty()) {
	        return "No admin to delete";
	    }

	    // Create a comma-separated list of slot IDs
	    String slotIds = String.join(",", slotIdList);

	    String query = "DELETE FROM `springbootdb`.`ADMIN` WHERE roll_no IN (" + slotIds + ");";

	    jdbc.execute(query);

	    return "Admin entries " + slotIdList + " deleted successfully";
	}

	
	
	
}
