package com.berheley.ichart.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Echarts信息实体类
 * @author pwb
 */
@Entity
@Table(name="bi_gis_echarts")
public class TEcharts {

    
	@Id
	@Column(name="id",length=50)
	private String id;
	
	@Column(name="type",length=50)
	private String type;
	
	@Lob
	@Column(name="options")
	private String options;
	
	@Lob
	@Column(name="datas")
	private String datas;
	
	@Column(name="create_user_",length=50)
	private String create_user_;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public String getCreate_user_() {
		return create_user_;
	}

	public void setCreate_user_(String create_user_) {
		this.create_user_ = create_user_;
	}
	
}
