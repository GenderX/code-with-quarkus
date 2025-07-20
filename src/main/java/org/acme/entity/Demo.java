package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "demo")
public class Demo extends PanacheEntity {
    
    public String name;
    public String description;
    
    public Demo() {}
    
    public Demo(String name, String description) {
        this.name = name;
        this.description = description;
    }
}