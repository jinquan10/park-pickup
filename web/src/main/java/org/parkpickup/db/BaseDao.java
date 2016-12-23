package org.parkpickup.db;

import org.parkpickup.Util;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;

public abstract class BaseDao {
    @Inject
    protected JdbcTemplate jdbcTemplate;

    @Inject
    protected Util util;
}
