package com.cozastore.securityservice.provider;

import com.cozastore.securityservice.entity.UserEntity;
import com.cozastore.securityservice.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public CustomAuthenticationProvider(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserEntity user = userRepository.findByEmailAndStatus(
                email, 1
        );
        if (user != null){
            if (user.isEnabled()){
                String userPass = user.getPassword();
                if (!passwordEncoder.matches(password, userPass)){
                        log.info("Username or password not exist !");
                        throw new RuntimeException("Username or password not exist !");
                }
                List<GrantedAuthority> roles = new ArrayList<>();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                        user.getRole().getRoleName()
                );
                roles.add(grantedAuthority);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        email, user.getPassword(), roles
                );
                SecurityContextHolder.getContext().setAuthentication(token);
                return token;
            }else{
                log.info("User is disabled !");
                throw new RuntimeException("User is disabled !");
            }
        }else {
            log.info("User not found or not exist !");
            throw new RuntimeException("User not found or not exist !");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);    }
}
