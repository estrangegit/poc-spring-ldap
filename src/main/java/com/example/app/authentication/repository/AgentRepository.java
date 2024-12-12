package com.example.app.authentication.repository;

import com.example.app.authentication.model.Agent;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends LdapRepository<Agent> {
    Optional<Agent> findByLoginAndPassword(String login, String password);
    Optional<Agent> findByLogin(String login);
}
