package org.chat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "groups")
@EqualsAndHashCode(callSuper = true)
@Data
@Setter
@Getter
public class Group extends PanacheEntity {
    @Column(nullable = false, unique = true)
    private String groupName;

    @Column(nullable = false)
    private String admin;

    @JsonManagedReference
    @ManyToMany
    private Set<User> users;
}
