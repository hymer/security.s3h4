package com.epic.core.security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epic.core.BaseContoller;
import com.epic.core.security.MD5Utils;
import com.epic.core.security.entity.Authority;
import com.epic.core.security.entity.Resource;
import com.epic.core.security.entity.Role;
import com.epic.core.security.entity.User;
import com.epic.core.security.service.AuthorityService;
import com.epic.core.security.service.ResourceService;
import com.epic.core.security.service.RoleService;
import com.epic.core.security.service.UserService;

@Controller
public class InitDataController extends BaseContoller {

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/init.do", method = RequestMethod.GET)
	public String init() {

		User admin = userService.getByUserName("admin");

		if (admin != null) {
			throw new RuntimeException(
					"you have initialized the datas, could not initialize again.");
		}

		User user = new User();
		user.setUserName("admin");
		user.setPassword(MD5Utils.encode(user.getUserName()));

		Role role = new Role();
		role.setCode(Role.SUPER_ROLE_FLAG);
		role.setDescription("the super administrator role.");
		role.setName("Super Administrator");

		Authority authority0 = new Authority();
		authority0.setName("Administrator");
		Authority authority1 = new Authority();
		authority1.setName("Resource Management");
		Authority authority2 = new Authority();
		authority2.setName("Authority Management");
		Authority authority3 = new Authority();
		authority3.setName("Role Management");
		Authority authority4 = new Authority();
		authority4.setName("User Management");

		Resource resource1 = new Resource();
		resource1.setName("Administrator Home Page");
		resource1.setUrl("/admin/admin.jsp");
		Resource resource2 = new Resource();
		resource2.setName("Resource Query Page");
		resource2.setUrl("(/admin/resource.jsp|/admin/resource_query.do)");
		Resource resource21 = new Resource();
		resource21.setName("Resource Obtain");
		resource21.setUrl("/admin/resource/[0-9]+.do");
		Resource resource22 = new Resource();
		resource22.setName("Resource Edit");
		resource22.setUrl("/admin/resource_edit.do");
		Resource resource3 = new Resource();
		resource3.setName("Authority Query Page");
		resource3.setUrl("(/admin/authority.jsp|/admin/authority_query.do)");
		Resource resource31 = new Resource();
		resource31.setName("Authority Obtain");
		resource31.setUrl("/admin/authority/[0-9]+.do");
		Resource resource32 = new Resource();
		resource32.setName("Authority Edit");
		resource32.setUrl("/admin/authority_edit.do");
		Resource resource4 = new Resource();
		resource4.setName("Role Query Page");
		resource4.setUrl("(/admin/role.jsp|/admin/role_query.do)");
		Resource resource41 = new Resource();
		resource41.setName("Role Obtain");
		resource41.setUrl("/admin/role/[0-9]+.do");
		Resource resource42 = new Resource();
		resource42.setName("Role Edit");
		resource42.setUrl("/admin/role_edit.do");
		Resource resource5 = new Resource();
		resource5.setName("User Query Page");
		resource5.setUrl("(/admin/user.jsp|/admin/user_query.do)");
		Resource resource51 = new Resource();
		resource51.setName("User Obtain");
		resource51.setUrl("/admin/user/[0-9]+.do");
		Resource resource52 = new Resource();
		resource52.setName("User Edit");
		resource52.setUrl("/admin/user_edit.do");
		Resource resource6 = new Resource();
		resource6.setName("Get Authority's Resources");
		resource6.setUrl("/admin/authority/assign/[0-9]+.do");
		Resource resource61 = new Resource();
		resource61.setName("Assign Resources to Authority");
		resource61.setUrl("/admin/assignResource.do");
		Resource resource7 = new Resource();
		resource7.setName("Get Role's Authorities");
		resource7.setUrl("/admin/role/assign/[0-9]+.do");
		Resource resource71 = new Resource();
		resource71.setName("Assign Authorities to Role");
		resource71.setUrl("/admin/assignAuthority.do");
		Resource resource8 = new Resource();
		resource8.setName("List Roles");
		resource8.setUrl("/admin/role/list.do");
		Resource resource9 = new Resource();
		resource9.setName("Reset User's Password");
		resource9.setUrl("/admin/password/reset.do");
		Resource resource10 = new Resource();
		resource10.setName("Resource Delete");
		resource10.setUrl("/admin/resource/delete.do");

		Set<Resource> resources1 = new HashSet<Resource>();
		resources1.add(resource1);
		Set<Resource> resources2 = new HashSet<Resource>();
		resources2.add(resource2);
		resources2.add(resource21);
		resources2.add(resource22);
		Set<Resource> resources3 = new HashSet<Resource>();
		resources3.add(resource3);
		resources3.add(resource31);
		resources3.add(resource32);
		Set<Resource> resources4 = new HashSet<Resource>();
		resources4.add(resource4);
		resources4.add(resource41);
		resources4.add(resource42);
		Set<Resource> resources5 = new HashSet<Resource>();
		resources5.add(resource5);
		resources5.add(resource51);
		resources5.add(resource52);
		Set<Resource> resources6 = new HashSet<Resource>();
		resources6.add(resource6);
		resources6.add(resource61);
		Set<Resource> resources7 = new HashSet<Resource>();
		resources7.add(resource7);
		resources7.add(resource71);
		Set<Resource> resources8 = new HashSet<Resource>();
		resources8.add(resource8);
		Set<Resource> resources9 = new HashSet<Resource>();
		resources9.add(resource9);
		Set<Resource> resources10 = new HashSet<Resource>();
		resources10.add(resource10);

		authority0.setResources(resources1);
		authority1.setResources(resources2);
		authority2.setResources(resources3);
		authority3.setResources(resources4);
		authority4.setResources(resources5);

		authority1.getResources().addAll(resources10);
		authority2.getResources().addAll(resources6);
		authority3.getResources().addAll(resources7);
		authority4.getResources().addAll(resources8);
		authority4.getResources().addAll(resources9);

		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority0);
		authorities.add(authority1);
		authorities.add(authority2);
		authorities.add(authority3);
		authorities.add(authority4);

		for (Authority authority : authorities) {
			for (Resource resource : authority.getResources()) {
				resource.setDescription(resource.getName() + ".");
				resource.setPriority(9);
				resource.setDisabled(false);
				resource.setType("ADMIN");
				resourceService.save(resource);
			}
			authority.setDescription(authority.getName() + ".");
			authority.setDisabled(false);
			authorityService.save(authority);
		}
		// Super Administrator do not need any authority.
//		role.setAuthorities(authorities);
		roleService.save(role);
		user.setRole(role);
		userService.save(user);
		resourceService.refreshProtectedResources();
		return "init_success";
	}

}
