package com.attendance.scheduler.Config.Authority;

import com.attendance.scheduler.Entity.TeacherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class TeacherDetails implements UserDetails {

    private final TeacherEntity teacherEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        return collection;
    }

    @Override
    public String getPassword() {
        return teacherEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return teacherEntity.getTeacherId();
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
        return teacherEntity.isApproved();
    }
}
