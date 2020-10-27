package com.litmus.app.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the locators database table.
 * 
 */
@Entity
@Table(name="locators", schema="dbo")

public class Locator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long locatorid;

	private String locatortype;
	
	//bi-directional many-to-one association to Objects
	@JsonBackReference
	@OneToMany(mappedBy="locator")
	private List<Objects> objects;

	public Locator() {
	}

	public long getLocatorid() {
		return this.locatorid;
	}

	public void setLocatorid(long locatorid) {
		this.locatorid = locatorid;
	}

	public String getLocatortype() {
		return this.locatortype;
	}

	public void setLocatortype(String locatortype) {
		this.locatortype = locatortype;
	}
	
	public List<Objects> getObjects() {
		return this.objects;
	}

	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	public Objects addObject(Objects object) {
		getObjects().add(object);
		object.setLocator(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setLocator(null);

		return object;
	}

}
