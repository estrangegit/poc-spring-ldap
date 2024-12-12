package com.example.app.authentication.repository;

import com.example.app.authentication.model.Permission;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import javax.naming.Name;
import java.util.List;

@Repository
public interface PermissionRepository extends LdapRepository<Permission> {
    List<Permission> findByMembers(Name member);
}
