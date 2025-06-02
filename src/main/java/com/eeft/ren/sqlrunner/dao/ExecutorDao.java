package com.eeft.ren.sqlrunner.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ExecutorDao {
	
    private static final Logger logger = LoggerFactory.getLogger(ExecutorDao.class);
	protected JdbcTemplate jdbcTemplate;
	
    public Object executeStatement(String sql) {
        logger.debug("Executing SQL: {}", sql);
        Object response = new Object();
        try {
            String lowered = sql.trim().toLowerCase();

            if (lowered.startsWith("select")) {
                List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
                response = results;
                logger.info("Result: {}", results);
            } else if (lowered.startsWith("update") || lowered.startsWith("delete") || lowered.startsWith("insert")) {
                int affected = jdbcTemplate.update(sql);
                response = affected;
                logger.info("Rows affected: {}", affected);
            } else {
                jdbcTemplate.execute(sql);
                logger.info("Statement executed.");
                response = "Success";
            }
        } catch (Exception e) {
            logger.error("Error executing SQL", e);
            throw e;
        }
        
        return response;
    }
}
