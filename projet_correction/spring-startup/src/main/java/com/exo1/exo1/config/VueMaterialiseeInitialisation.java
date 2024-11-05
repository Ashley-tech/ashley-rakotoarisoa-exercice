package com.exo1.exo1.config;

import com.exo1.exo1.service.VueMaterialiseeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VueMaterialiseeInitialisation implements CommandLineRunner {

    private final VueMaterialiseeService materializedViewService;

    @Autowired
    public VueMaterialiseeInitialisation(VueMaterialiseeService materializedViewService) {
        this.materializedViewService = materializedViewService;
    }

    @Override
    public void run(String... args) throws Exception {
        materializedViewService.createProjectTaskCountView();
    }

}