package com.apigateway.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apigateway.jwtutil.JwtAuthFilter;

@Configuration
public class GatewayRoutes {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

            // ✅ PUBLIC ROUTES (defined FIRST)
            .route("Auth-Service", r -> r.path("/auth/**")
                .uri("lb://AUTH-SERVICE"))//lb:// means Load Balancer “Find the service instance from the service registry and automatically choose one instance using load balancing.”

            .route("Company-Signup", r -> r.path("/Company/signup")
                .uri("lb://COMPANY-SERVICE"))

            .route("Student-Signup", r -> r.path("/Student/signup")
                .uri("lb://STUDENT-SERVICE"))
            .route("Admint-Signup", r -> r.path("/Admin/signup")
                    .uri("lb://ADMIN-SERVICE"))
            // ✅ PROTECTED ROUTES (defined AFTER public)
            .route("Company-Service-Protected", r -> r.path("/Company/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://COMPANY-SERVICE"))

            .route("Student-Service-Protected", r -> r.path("/Student/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://STUDENT-SERVICE"))

            
            .route("Placement-Service-Protected", r -> r.path("/Placement/**")
                    .filters(f -> f.filter(jwtAuthFilter))
                    .uri("lb://PLACEMENT-SERVICE"))
            
            .route("Admin-Service", r -> r.path("/Admin/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://ADMIN-SERVICE"))

            .build();
    }

    
    
    
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//
//            // PUBLIC ROUTES
//            .route("Auth-Service", r -> r.path("/auth/**")
//                .uri("lb://AUTH-SERVICE"))
//
//            .route("Company-Signup", r -> r.path("/Company/signup")
//                .uri("lb://COMPANY-SERVICE"))
//
//            .route("Student-Signup", r -> r.path("/Student/signup")
//                .uri("lb://STUDENT-SERVICE"))
//
//            // PROTECTED ROUTES
//            .route("Company-Service-Protected", r -> r
//                .path("/Company/**")
//                .and()
//                .not(r2 -> r2.path("/Company/signup"))
//                .filters(f -> f.filter(jwtAuthFilter))
//                .uri("lb://COMPANY-SERVICE"))
//
//            .route("Student-Service-Protected", r -> r
//                .path("/Student/**")
//                .and()
//                .not(r2 -> r2.path("/Student/signup"))
//                .filters(f -> f.filter(jwtAuthFilter))
//                .uri("lb://STUDENT-SERVICE"))
//
//            .route("Admin-Service", r -> r.path("/admin/**")
//                .filters(f -> f.filter(jwtAuthFilter))
//                .uri("lb://ADMIN-SERVICE"))
//
//            // Optional: TPO route
////            .route("TPO-Service", r -> r.path("/tpo/**")
////                .filters(f -> f.filter(jwtAuthFilter))
////                .uri("lb://TPO-SERVICE"))
//
//            .build();
//    }

}
