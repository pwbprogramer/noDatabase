package com.berheley.ichart.dialect;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

public class MySql5ExtendDialect extends MySQL5Dialect{

	public MySql5ExtendDialect() {
		// TODO Auto-generated constructor stub
		super();
		this.registerColumnType( Types.JAVA_OBJECT, "varchar(32)" );
	}
}
