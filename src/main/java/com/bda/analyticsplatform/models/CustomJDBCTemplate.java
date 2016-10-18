package com.bda.analyticsplatform.models;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class CustomJDBCTemplate extends JdbcTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1706785633028690189L;

	public CustomJDBCTemplate(DataSource dataSource) {
		super(dataSource);
	}
	

	

}
