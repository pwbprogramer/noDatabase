package com.berheley.ichart.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public class BaseEntity implements Serializable{

	public static final long serialVersionUID = -3621575739735747181L;
	@Id
	@Column(length = 32)
	@GenericGenerator(name="systemUUID",strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	public String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
