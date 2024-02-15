package com.insurance_domain;

import com.insurance_domain.entity.Role;
import com.insurance_domain.repository.RoleRepository;
import com.insurance_domain.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class InsuranceDomainApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(InsuranceDomainApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Role role1 = new Role();
		role1.setId(AppConstants.ROLE_CUSTOMER);
		role1.setRoleName("ROLE_CUSTOMER");

		Role role2 = new Role();
		role2.setId(AppConstants.ROLE_ADMINISTRATOR);
		role2.setRoleName("ROLE_ADMINISTRATOR");

		Role role3 = new Role();
		role3.setId(AppConstants.ROLE_AGENT);
		role3.setRoleName("ROLE_AGENT");

		List<Role> roleList = List.of(role1, role2, role3);
		roleRepository.saveAll(roleList);

		roleList.forEach(role->{
			System.out.println(role.getId()+" "+role.getRoleName());
		});

	}
}
