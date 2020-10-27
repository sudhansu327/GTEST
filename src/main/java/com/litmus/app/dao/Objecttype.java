package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the objecttypes database table.
 * 
 */
@Entity
@Table(name="objecttypes", schema="dbo")

public class Objecttype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long objecttypeid;

	private String objecttypename;

	//bi-directional many-to-one association to Objects
	@JsonBackReference
	@OneToMany(mappedBy="objecttype")
	private List<Objects> objects;

	public Objecttype() {
	}

	public Long getObjecttypeid() {
		return this.objecttypeid;
	}

	public void setObjecttypeid(Long objecttypeid) {
		this.objecttypeid = objecttypeid;
	}

	public String getObjecttypename() {
		return this.objecttypename;
	}

	public void setObjecttypename(String objecttypename) {
		this.objecttypename = objecttypename;
	}

	public List<Objects> getObjects() {
		return this.objects;
	}

	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	public Objects addObject(Objects object) {
		getObjects().add(object);
		object.setObjecttype(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setObjecttype(null);

		return object;
	}

}
