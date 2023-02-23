package com.phc.identity.security;

import com.phc.identity.entity.UserIdentity;
import com.phc.identity.repository.UserIdentityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DBUserDetailsServiceTest {

    @MockBean
    private UserIdentityRepository userIdentityRepository;

    private DbUserDetailsService dbUserDetailsService;

    @Test
    public void testLoadUser() {

//        final List<GrantedAuthority> grantedAuthorities = Stream.of("ADMIN", "USER").map(a -> new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return a.toString();
//            }
//        }).collect(Collectors.toList());

//        when(userIdentityRepository.findByUserName("jeeuser")).thenReturn(UserIdentity.builder()
//                        .userName("jeeuser").authorities("ROLE_ADMIN,ROLE_USER")
//                .build());
//
//        dbUserDetailsService = new DbUserDetailsService(userIdentityRepository);
//
//        var userDetails =  dbUserDetailsService.loadUserByUsername("jeeuser");
//
//        //assertNotNull(userDetails);
//
//        assertThat(dbUserDetailsService.loadUserByUsername("jeeuser"))
//                .isNotNull()
//               .satisfies(ud -> ud.getUsername().equals("jeeuser"));

    }
}
