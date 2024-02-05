package com.antonio.sistema_stock.security.services;


import com.antonio.sistema_stock.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


// REcupera los usuarios de la base de datos con los permisos y asi autenticar
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override //este metodo Spring Security lo consulta por debajo en su core para asegurarse cual es el user que se consulta IMPORTANTE IMPLEMENTAR para que sepa de donde buscar los users
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.antonio.sistema_stock.entities.User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not exist"));
//con esto recuperamos el user de la base de datos
        //nos falta decirle a spring Security que tiene que usar este user

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(user.getRole().concat(",").split(",")).map(SimpleGrantedAuthority::new).toList();

        return new User(user.getUsername(),user.getPassword(),user.getActive(),true,true,true,authorities);
        // devolvemos un user de spring securiti y le decimos que el user lo tiene que buscar en ls db
    }
}






















/*
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

