package com.Admin_Service.AdminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Admin_Service.Client.AuthServiceClient;
import com.Admin_Service.Client.CompanyFeignClient;
import com.Admin_Service.Client.StudentServiceClient;
import com.Admin_Service.Model.Admin;
import com.Admin_Service.Model.CompanyDTO;
import com.Admin_Service.Model.JobPostingDTO;
import com.Admin_Service.Model.RegisterAuthUserRequest;
import com.Admin_Service.Model.StudentPlacement;
import com.Admin_Service.Model.appliedJobDTO;
import com.Admin_Service.Repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminrepo;

	@Autowired
	 private AuthServiceClient authServiceClient;
	
	@Autowired
	 private StudentServiceClient studentserviceClient;
	@Autowired
	 private  CompanyFeignClient companyfeignclient;
	
	public Admin Register(Admin admin) {
	 Admin savedadmin=	adminrepo.save(admin);
	  RegisterAuthUserRequest authUser = new RegisterAuthUserRequest();
	    authUser.setUsername(admin.getEmail());
	    authUser.setPassword(admin.getPassword());
	    authUser.setRole("ROLE_ADMIN");
	    authServiceClient.registerUser(authUser);
		return savedadmin;
	}

	public long countStudent() {
		return	studentserviceClient.countStudent();
	}
	  public List<StudentPlacement> fetchAllStudents() {
	    	 List<StudentPlacement>studentList= studentserviceClient.getAllStudents();
	        return studentList;
	    }
	  
	  public  List<CompanyDTO>  fetchAllCompnay() {
          List<CompanyDTO> compnaies= companyfeignclient.allCompany();
          return compnaies;
	}
	  
	  public long countcompany() {
			// TODO Auto-generated method stub
			return companyfeignclient.countcompany();
		}

		public long countjobs() {
			// TODO Auto-generated method stub
			return companyfeignclient.getjobcount();
		}
	

		public List<JobPostingDTO> fetchAllJobs() {
			 List<JobPostingDTO> alljobs=companyfeignclient.alljobs();	
			 return alljobs;
		}
}
