package com.assignment.sp.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

/**
 * By Default Hibernate does not support Postgres Enum Types, 
 * so this class maps a Java Enum to a PostgreSQL ENUM column type.
 */
public class PostgreSQLEnumType extends EnumType{
	private static final long serialVersionUID = 3639948370577129006L;

	@Override
	public void nullSafeSet(
			PreparedStatement st, 
			Object value, 
			int index, 
			SharedSessionContractImplementor session
			) throws SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
		} else {
			st.setObject(index, value.toString(), Types.OTHER);
		}
	}


}
