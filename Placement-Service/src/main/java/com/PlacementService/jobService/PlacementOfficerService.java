package com.PlacementService.jobService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PlacementService.Dto.CompanyDTO;
import com.PlacementService.Dto.RegisterAuthUserRequest;
import com.PlacementService.Dto.appliedJobDTO;
import com.PlacementService.Messaging.EventActivityPublisher;
import com.PlacementService.Model.PlacementOfficer;
import com.PlacementService.Model.StudentPlacement;
import com.PlacementService.Repository.PlacementOfficerRepository;
import com.PlacementService.client.AuthServiceClient;
import com.PlacementService.client.CompanyFeignClient;
import com.PlacementService.client.StudentFeignClient;

@Service
public class PlacementOfficerService {
      private EventActivityPublisher eventActivityPublisher;
	 private final PlacementOfficerRepository officerRepo;
	    private final PasswordEncoder passwordEncoder;
	    private final AuthServiceClient authServiceClient;
	    private final StudentFeignClient studentserviceClient;
	    private final CompanyFeignClient companyfeignclient;

	    public PlacementOfficerService(PlacementOfficerRepository officerRepo, PasswordEncoder passwordEncoder,AuthServiceClient authServiceClient
	    		,StudentFeignClient studentserviceClient, CompanyFeignClient companyfeignclient ,EventActivityPublisher eventActivityPublisher) {
	        this.officerRepo = officerRepo;
	        this.passwordEncoder = passwordEncoder;
	        this.authServiceClient=authServiceClient;
	        this.studentserviceClient=studentserviceClient;
	        this.companyfeignclient=companyfeignclient;
	        this.eventActivityPublisher=eventActivityPublisher;
	    }

    // Signup logic for Placement TPO
    public PlacementOfficer signupTPO(PlacementOfficer officer) {
        officer.setPassword(passwordEncoder.encode(officer.getPassword()));
        officer.setRole("ROLE_TPO");
        officer.setStatus("PENDING"); 
        // waiting for admin approval
        RegisterAuthUserRequest authUser = new RegisterAuthUserRequest();
	    authUser.setUsername(officer.getEmail());
	    authUser.setPassword(officer.getPassword());
	    authUser.setRole("ROLE_TPO");
	    authServiceClient.registerUser(authUser);
	    eventActivityPublisher.publishActivity("PLacement_cordinator has register"  , officer.getEmail()+" has applied for Placement cordinator role");
        return officerRepo.save(officer);
    }

    public List<StudentPlacement> fetchAllStudents() {
    	 List<StudentPlacement>studentList= studentserviceClient.getAllStudents();
        return studentList;
    }

public  List<CompanyDTO>  fetchAllCompnay() {
           List<CompanyDTO> compnaies= companyfeignclient.allCompany();
           return compnaies;
	}

public List<appliedJobDTO> fetchAllApploedJobs() {
List<appliedJobDTO> appliedjobs=companyfeignclient.alljobs();
return appliedjobs;
}

public long countStudent() {
	return	studentserviceClient.countStudent();
}

public long countcompany() {
	// TODO Auto-generated method stub
	return companyfeignclient.countcompany();
}

public long countjobs() {
	// TODO Auto-generated method stub
	return companyfeignclient.getjobcount();
}


	}