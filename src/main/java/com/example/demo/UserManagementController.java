package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserManagementController {

    // Inject a service or repository that handles data access
    private final UserManagementService userManagementService;

    @Autowired
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/emp")
    public List<Employee> getEmp() {
        return userManagementService.getAllEmp();
    }
    
    @GetMapping("/admin")
    public List<Admin> getPatches() {
        return userManagementService.getAllAdmin();
    }
 
    @GetMapping("/region")
    public List<Regions> getRegions() {
        return userManagementService.getAllRegions();
    }
    
    @GetMapping("/role")
    public List<Roles> getRoles() {
        return userManagementService.getAllRoles();
    }
    
    
	@RequestMapping(value = "/add-emp", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestBody Employee empModel)
			throws ParseException, IOException, GeneralSecurityException {

		String msg = "";
		msg = userManagementService.addEmp(empModel);

		return msg;

	}

	@RequestMapping(value = "/add-region", method = RequestMethod.POST)
	@ResponseBody
	public String addRegion(@RequestBody Regions empModel)
			throws ParseException, IOException, GeneralSecurityException {

		String msg = "";
		msg = userManagementService.addRegion(empModel);

		return msg;

	}

	@RequestMapping(value = "/add-role", method = RequestMethod.POST)
	@ResponseBody
	public String addRole(@RequestBody Roles empModel)
			throws ParseException, IOException, GeneralSecurityException {

		String msg = "";
		msg = userManagementService.addRole(empModel);

		return msg;

	}
	
	@RequestMapping(value = "/remove-emp", method = RequestMethod.POST)
	@ResponseBody
	public String removeUser(@RequestBody Employee empModel)
			throws ParseException, IOException, GeneralSecurityException {
		String msg = "";
		msg = userManagementService.removeEmp(empModel);

		return msg;

	}
	@RequestMapping(value = "/add-admin", method = RequestMethod.POST)
	@ResponseBody
	public String addAdmin(@RequestBody Admin adminModel)
			throws ParseException, IOException, GeneralSecurityException {

		String msg = "";
		msg = userManagementService.addAdmin(adminModel);

		return msg;

	}

	@RequestMapping(value = "/remove-admin", method = RequestMethod.POST)
	@ResponseBody
	public String removeAdmin(@RequestBody Admin adminModel)
			throws ParseException, IOException, GeneralSecurityException {
		String msg = "";
		msg = userManagementService.removeAdmin(adminModel);

		return msg;

	}



}
