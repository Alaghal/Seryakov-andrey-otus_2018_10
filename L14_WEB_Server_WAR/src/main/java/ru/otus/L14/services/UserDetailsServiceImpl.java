package ru.otus.L14.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.L14.entity.enums.UserRoleEnum;
import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private  UserService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser user = service.getUserOfLogin("admin");

        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority( UserRoleEnum.USER.name()));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getLogin(),
                        user.getPassword(),
                        roles);

        return userDetails;
    }
}
