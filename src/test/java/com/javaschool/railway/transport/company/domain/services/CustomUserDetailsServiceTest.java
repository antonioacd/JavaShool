package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        // Arrange
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword("password");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_USER");
        userEntity.setRoles(Collections.singletonList(roleEntity));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(email);
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act/Assert
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Email not found");
    }

    /*@Test
    void mapRolesToAuthorities_MapsRoles_ReturnsGrantedAuthorities() {
        // Arrange
        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setName("ROLE_USER");
        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setName("ROLE_ADMIN");
        List<RoleEntity> roles = Arrays.asList(roleEntity1, roleEntity2);

        // Act
        List<GrantedAuthority> authorities = userDetailsService.mapRolesToAuthorities(roles);

        // Assert
        assertThat(authorities).hasSize(2);
        assertThat(authorities).extracting("authority").containsExactly("ROLE_USER", "ROLE_ADMIN");
    }*/
}
