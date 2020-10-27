package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the subfunctionalities database table.
 * 
 */
@Entity
@Table(name="subfunctionalities", schema="dbo")

public class Subfunctionality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long subfunctionalityid;

	private String subfunctionalitydescription;

	private String subfunctionalityname;

	//bi-directional many-to-one association to Functionality
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="functionalityid")
	private Functionality functionality;

	//bi-directional many-to-one association to Testfunctionalityassoc
	@JsonBackReference
	@OneToMany(mappedBy="subfunctionality")
	private List<Testfunctionalityassoc> testfunctionalityassocs;

	public Subfunctionality() {
	}

	public long getSubfunctionalityid() {
		return this.subfunctionalityid;
	}

	public void setSubfunctionalityid(long subfunctionalityid) {
		this.subfunctionalityid = subfunctionalityid;
	}

	public String getSubfunctionalitydescription() {
		return this.subfunctionalitydescription;
	}

	public void setSubfunctionalitydescription(String subfunctionalitydescription) {
		this.subfunctionalitydescription = subfunctionalitydescription;
	}

	public String getSubfunctionalityname() {
		return this.subfunctionalityname;
	}

	public void setSubfunctionalityname(String subfunctionalityname) {
		this.subfunctionalityname = subfunctionalityname;
	}

	public Functionality getFunctionality() {
		return this.functionality;
	}

	public void setFunctionality(Functionality functionality) {
		this.functionality = functionality;
	}

	public List<Testfunctionalityassoc> getTestfunctionalityassocs() {
		return this.testfunctionalityassocs;
	}

	public void setTestfunctionalityassocs(List<Testfunctionalityassoc> testfunctionalityassocs) {
		this.testfunctionalityassocs = testfunctionalityassocs;
	}

	public Testfunctionalityassoc addTestfunctionalityassoc(Testfunctionalityassoc testfunctionalityassoc) {
		getTestfunctionalityassocs().add(testfunctionalityassoc);
		testfunctionalityassoc.setSubfunctionality(this);

		return testfunctionalityassoc;
	}

	public Testfunctionalityassoc removeTestfunctionalityassoc(Testfunctionalityassoc testfunctionalityassoc) {
		getTestfunctionalityassocs().remove(testfunctionalityassoc);
		testfunctionalityassoc.setSubfunctionality(null);

		return testfunctionalityassoc;
	}

}
