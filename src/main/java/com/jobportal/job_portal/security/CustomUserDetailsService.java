package com.jobportal.job_portal.security;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.repository.UserRepository;




@Service

public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(email)
								.orElseThrow(()->new UsernameNotFoundException
										("User not found with email: "+email));
		
		return new User(user.getEmail(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority("Role_" + user.getRole().name())));
				
	}

}
