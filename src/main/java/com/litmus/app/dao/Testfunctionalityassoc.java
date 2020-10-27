package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the testfunctionalityassoc database table.
 * 
 */
@Entity
@Table(name="testfunctionalityassoc", schema="dbo")

public class Testfunctionalityassoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long associationid;

	//bi-directional many-to-one association to Subfunctionality
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="subfunctionalityid")
	private Subfunctionality subfunctionality;

	//bi-directional many-to-one association to Test
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="testid")
	private Test test;

	public Testfunctionalityassoc() {
	}

	public long getAssociationid() {
		return this.associationid;
	}

	public void setAssociationid(long associationid) {
		this.associationid = associationid;
	}

	public Subfunctionality getSubfunctionality() {
		return this.subfunctionality;
	}

	public void setSubfunctionality(Subfunctionality subfunctionality) {
		this.subfunctionality = subfunctionality;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
