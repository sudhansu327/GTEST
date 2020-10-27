package com.litmus.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the screens database table.
 * 
 */
@Entity
@Table(name="screens", schema="dbo")
/*@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "Insert_Screen_Objects", 
            procedureName = "dbo.Insert_Screen_Objects",
            parameters = {
               @StoredProcedureParameter(mode = ParameterMode.IN, name = "screen_list_data", type =List.class),
               @StoredProcedureParameter(mode = ParameterMode.IN, name = "object_list_data", type = List.class)
            }),
})*/
public class Screen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long screenid;

	private Date lastmodified;

	private String screendescription;

	private String screenname;

	//bi-directional many-to-one association to Modulestep
	@JsonBackReference
	@OneToMany(mappedBy="screen")
	private List<Modulestep> modulesteps;

	//bi-directional many-to-one association to Objects
	@JsonBackReference
	@OneToMany(mappedBy="screen")
	private List<Objects> objects;

	//bi-directional many-to-one association to Project
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="projectid")
	private Project project;

	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	
	//bi-directional many-to-one association to Datarepo
	@JsonBackReference
	@OneToMany(mappedBy="screen")
	private List<Datarepo> datarepos;

	
	public Screen() {
	}

	public long getScreenid() {
		return this.screenid;
	}

	public void setScreenid(long screenid) {
		this.screenid = screenid;
	}

	public Date getLastmodified() {
		return this.lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getScreendescription() {
		return this.screendescription;
	}

	public void setScreendescription(String screendescription) {
		this.screendescription = screendescription;
	}

	public String getScreenname() {
		return this.screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public List<Modulestep> getModulesteps() {
		return this.modulesteps;
	}

	public void setModulesteps(List<Modulestep> modulesteps) {
		this.modulesteps = modulesteps;
	}

	public Modulestep addModulestep(Modulestep modulestep) {
		getModulesteps().add(modulestep);
		modulestep.setScreen(this);

		return modulestep;
	}

	public Modulestep removeModulestep(Modulestep modulestep) {
		getModulesteps().remove(modulestep);
		modulestep.setScreen(null);

		return modulestep;
	}

	public List<Objects> getObjects() {
		return this.objects;
	}

	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	public Objects addObject(Objects object) {
		getObjects().add(object);
		object.setScreen(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setScreen(null);

		return object;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Datarepo> getDatarepos() {
		return datarepos;
	}

	public void setDatarepos(List<Datarepo> datarepos) {
		this.datarepos = datarepos;
	}

}
