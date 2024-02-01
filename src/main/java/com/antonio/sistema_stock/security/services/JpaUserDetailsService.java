package com.antonio.sistema_stock.security.services;


import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.security.models.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JpaUserDetailsService implements UserDetailsService {


    @Autowired
    private IUserRepository userRepository;
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: "+username));

    }


}

       /* Optional<User>optionalUser= userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("insert valid username");

        User user= optionalUser.orElseThrow();

        List<GrantedAuthority> authorities = Arrays.stream(Arrays.stream(user.getRoles().getRole().split("a")).map());

        return  new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities);


        */

