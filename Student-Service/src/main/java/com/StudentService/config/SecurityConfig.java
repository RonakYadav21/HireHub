package com.StudentService.config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//	@Autowired
//	private JwtAuthFilter jwtAuthFilter;

	
//	 @Bean
//	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		    http
//		        .csrf(csrf -> csrf.disable())
//		        .authorizeHttpRequests(auth -> auth
//		            .requestMatchers("/Student/signup", "/Student/login").permitAll()
//		            .requestMatchers("/Student/viewprofile").hasAuthority("ROLE_STUDENT")
//		            .anyRequest().authenticated()
//		        )
//		        .sessionManagement(sess -> sess
//		            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		        );
//
//		    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//		    return http.build();
//		}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
