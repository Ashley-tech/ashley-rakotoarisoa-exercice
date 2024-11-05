package com.exo1.exo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VueMaterialiseeService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VueMaterialiseeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProjectTaskCountView() {
        String sql = "CREATE MATERIALIZED VIEW projet_task_number AS " +
                "SELECT p.projet_id as projet, COUNT(task_id) AS nombre_de_taches FROM projet p LEFT JOIN task t ON t.projet_id = p.projet_id GROUP BY p.projet_id ORDER BY projet";
        jdbcTemplate.execute(sql);
    }

    // refresh
    public void refreshProjectTaskCountView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW project_task_count");
    }
}
