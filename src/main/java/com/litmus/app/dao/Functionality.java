package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the functionalities database table.
 * 
 */
@Entity
@Table(name="functionalities", schema="dbo")

public class Functionality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long functionalityid;

	private String functionalitydescription;

	private String functionalityname;

	//bi-directional many-to-one association to Subfunctionality
	@JsonBackReference
	@OneToMany(mappedBy="functionality")
	private List<Subfunctionality> subfunctionalities;

	public Functionality() {
	}

	public long getFunctionalityid() {
		return this.functionalityid;
	}

	public void setFunctionalityid(long functionalityid) {
		this.functionalityid = functionalityid;
	}

	public String getFunctionalitydescription() {
		return this.functionalitydescription;
	}

	public void setFunctionalitydescription(String functionalitydescription) {
		this.functionalitydescription = functionalitydescription;
	}

	public String getFunctionalityname() {
		return this.functionalityname;
	}

	public void setFunctionalityname(String functionalityname) {
		this.functionalityname = functionalityname;
	}

	public List<Subfunctionality> getSubfunctionalities() {
		return this.subfunctionalities;
	}

	public void setSubfunctionalities(List<Subfunctionality> subfunctionalities) {
		this.subfunctionalities = subfunctionalities;
	}

	public Subfunctionality addSubfunctionality(Subfunctionality subfunctionality) {
		getSubfunctionalities().add(subfunctionality);
		subfunctionality.setFunctionality(this);

		return subfunctionality;
	}

	public Subfunctionality removeSubfunctionality(Subfunctionality subfunctionality) {
		getSubfunctionalities().remove(subfunctionality);
		subfunctionality.setFunctionality(null);

		return subfunctionality;
	}

}
