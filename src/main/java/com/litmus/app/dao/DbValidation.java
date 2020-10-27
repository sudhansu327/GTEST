package com.litmus.app.dao;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the db_validations database table.
 * 
 */
@Entity
@Table(name="db_validations", schema="dbo")
//@NamedQuery(name="DbValidation.findAll", query="SELECT d FROM DbValidation d")
public class DbValidation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="S_no")
	private Long s_no;

	@Column(name="test_description")
	private String testDescription;

	@Column(name="test_results")
	private String testResults;

	public DbValidation() {
	}

	public Long getS_no() {
		return this.s_no;
	}

	public void setS_no(Long s_no) {
		this.s_no = s_no;
	}

	public String getTestDescription() {
		return this.testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public String getTestResults() {
		return this.testResults;
	}

	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}

}
