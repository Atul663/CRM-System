package com.example.CRM;

import com.example.CRM.config.AppConstant;
import com.example.CRM.entity.Role;
import com.example.CRM.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class CrmApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {

		try {
			Role role1 = new Role();

			role1.setId(Long.valueOf(AppConstant.ADMIN_USER));
			role1.setName("ADMIN_USER");

			Role role2 = new Role();
			role2.setId(Long.valueOf(AppConstant.NORMAL_USER));
			role2.setName("NORMAL_USER");

			List<Role> roles = List.of(role1,role2);
			List<Role> resultList = this.roleRepo.saveAll(roles);
			resultList.forEach(r-> {
				System.out.println(r.getName());
			});

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
