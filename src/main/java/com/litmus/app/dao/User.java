package com.litmus.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users", schema="dbo")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userid;

	private Date createdon;

	private String username;

	private String userpassword;
	
	private String role;

	//bi-directional many-to-one association to Businessprocess
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Businessprocess> businessprocesses;

	//bi-directional many-to-one association to Module
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Module> modules;

	//bi-directional many-to-one association to Objects
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Objects> objects;

	//bi-directional many-to-one association to Screen
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Screen> screens;

	//bi-directional many-to-one association to Test
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Test> tests;

	//bi-directional many-to-one association to Useraccess
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Useraccess> useraccesses;
	
	//bi-directional many-to-one association to Suiteinstance
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Suiteinstance> suiteinstances;

	//bi-directional many-to-one association to Testinstance
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Testinstance> testinstances;
	
	//bi-directional many-to-one association to Project
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Project> projects;
	
	//bi-directional many-to-one association to Suite
	@JsonBackReference
	@OneToMany(mappedBy="user")
	private List<Suite> suites;
	
	public List<Suite> getSuites() {
		return suites;
	}

	public void setSuites(List<Suite> suites) {
		this.suites = suites;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Suiteinstance> getSuiteinstances() {
		return suiteinstances;
	}

	public void setSuiteinstances(List<Suiteinstance> suiteinstances) {
		this.suiteinstances = suiteinstances;
	}

	public List<Testinstance> getTestinstances() {
		return testinstances;
	}

	public void setTestinstances(List<Testinstance> testinstances) {
		this.testinstances = testinstances;
	}

	public User() {
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return this.userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Businessprocess> getBusinessprocesses() {
		return this.businessprocesses;
	}

	public void setBusinessprocesses(List<Businessprocess> businessprocesses) {
		this.businessprocesses = businessprocesses;
	}

	public Businessprocess addBusinessprocess(Businessprocess businessprocess) {
		getBusinessprocesses().add(businessprocess);
		businessprocess.setUser(this);

		return businessprocess;
	}

	public Businessprocess removeBusinessprocess(Businessprocess businessprocess) {
		getBusinessprocesses().remove(businessprocess);
		businessprocess.setUser(null);

		return businessprocess;
	}

	public List<Module> getModules() {
		return this.modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public Module addModule(Module module) {
		getModules().add(module);
		module.setUser(this);

		return module;
	}

	public Module removeModule(Module module) {
		getModules().remove(module);
		module.setUser(null);

		return module;
	}

	public List<Objects> getObjects() {
		return this.objects;
	}

	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	public Objects addObject(Objects object) {
		getObjects().add(object);
		object.setUser(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setUser(null);

		return object;
	}

	public List<Screen> getScreens() {
		return this.screens;
	}

	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}

	public Screen addScreen(Screen screen) {
		getScreens().add(screen);
		screen.setUser(this);

		return screen;
	}

	public Screen removeScreen(Screen screen) {
		getScreens().remove(screen);
		screen.setUser(null);

		return screen;
	}

	public List<Test> getTests() {
		return this.tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public Test addTest(Test test) {
		getTests().add(test);
		test.setUser(this);

		return test;
	}

	public Test removeTest(Test test) {
		getTests().remove(test);
		test.setUser(null);

		return test;
	}

	public List<Useraccess> getUseraccesses() {
		return this.useraccesses;
	}

	public void setUseraccesses(List<Useraccess> useraccesses) {
		this.useraccesses = useraccesses;
	}

	public Useraccess addUseraccess(Useraccess useraccess) {
		getUseraccesses().add(useraccess);
		useraccess.setUser(this);

		return useraccess;
	}

	public Useraccess removeUseraccess(Useraccess useraccess) {
		getUseraccesses().remove(useraccess);
		useraccess.setUser(null);

		return useraccess;
	}

}
