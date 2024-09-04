package ru.nsu.GameStudy.authentication.models;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "fathers_name")
    private String fathersName;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone_number")//, unique = true)
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "recipients")
    @Fetch(FetchMode.JOIN)
    private List<Notification> receivedNotifications;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "recipientsConfirmed")
    @Fetch(FetchMode.JOIN)
    private List<Notification> confirmedNotifications;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sender",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    private List<Notification> sentNotifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}