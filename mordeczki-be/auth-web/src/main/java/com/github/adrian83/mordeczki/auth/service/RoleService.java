package com.github.adrian83.mordeczki.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.model.command.NewRoleCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.model.query.GetRoleQuery;
import com.github.adrian83.mordeczki.auth.repository.RoleRepository;
import com.github.adrian83.mordeczki.auth.web.model.request.PageQuery;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> getRoleById(GetRoleQuery query) {
        return roleRepository.findById(query.id());
    }

    public Role createRole(NewRoleCommand command) {
        var role = new Role(command.name());
        return roleRepository.save(role);
    }

    public Page<Role> listRole(PageQuery query) {
        return roleRepository.findAll(PageRequest.of(query.pageNo(), query.pageSize()));
    }

}
