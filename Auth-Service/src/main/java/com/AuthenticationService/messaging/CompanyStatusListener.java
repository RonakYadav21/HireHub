package com.AuthenticationService.messaging;


import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.AuthenticationService.Model.CompanyStatusEvent;
import com.AuthenticationService.Model.Users;
import com.AuthenticationService.Repository.UserRepository;


@Service
public class CompanyStatusListener {

    private final UserRepository userrepository;

    public CompanyStatusListener(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    @RabbitListener(queues = RabbitMqConfig.AUTH_QUEUE )
    public void handleCompanyStatusUpdate(CompanyStatusEvent event) {

        System.out.println("Received company status event: " + event);

        Optional<Users> company = userrepository.findByUsername(event.getEmail());

        if (company.isPresent()) {

            Users user = company.get();   // extract user from Optional
            user.setStatus(event.getStatus());

            userrepository.save(user);

            System.out.println("Company status updated for Authservice: " + event.getEmail());

        } else {
            System.out.println("Company not found for email: " + event.getEmail());
        }
    }
}