package com.javaweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rentarea")
public class RentAreaEntity extends BaseEntity{

	@Column(name = "value", nullable = false)
	private Integer value;
	
	@ManyToOne
	@JoinColumn(name = "buildingid")
	private BuildingEntity building;

	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public BuildingEntity  getBuilding() {
		return building;
	}
	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}
	
}
