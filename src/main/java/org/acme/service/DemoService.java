package org.acme.service;

import org.acme.entity.Demo;
import org.acme.interceptor.Logged;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Logged
public class DemoService {
    
    public List<Demo> findAll() {
        return Demo.listAll();
    }
    
    public Demo findById(Long id) {
        return Demo.findById(id);
    }
    
    @Transactional
    public Demo create(Demo demo) {
        demo.persist();
        return demo;
    }
    
    @Transactional
    @Logged(logParameters = true, logResult = false, logExecutionTime = true)
    public Demo updateWithoutResultLogging(Long id, Demo demo) {
        Demo entity = Demo.findById(id);
        if (entity != null) {
            entity.name = demo.name;
            entity.description = demo.description;
        }
        return entity;
    }
    
    @Transactional
    public Demo update(Long id, Demo demo) {
        Demo entity = Demo.findById(id);
        if (entity != null) {
            entity.name = demo.name;
            entity.description = demo.description;
        }
        return entity;
    }
    
    @Transactional
    public boolean delete(Long id) {
        return Demo.deleteById(id);
    }
}