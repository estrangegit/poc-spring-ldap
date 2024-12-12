package com.example.app.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.List;

@Entry(base = "ou=permissions", objectClasses = { "groupOfNames" })
@Data
public final class Permission {
    @Id
    @JsonIgnore
    private Name dn;
    private @Attribute(name = "cn") String name;
    @JsonIgnore
    private @Attribute(name = "member") List<Name> members;
}
