package com.example.app.authentication.model;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(base = "ou=agents", objectClasses = { "inetOrgPerson" })
@Data
public final class Agent {
    @Id
    private Name dn;
    private @Attribute(name = "uid") String login;
    private @Attribute(name = "cn") String fullName;
    private @Attribute(name = "sn") String lastName;
    private @Attribute(name = "userPassword") String password;
}
