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
 * The persistent class for the projects database table.
 * 
 */
@Entity
@Table(name="projects", schema="dbo")

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long projectid;

	private Date createdon;

	private String projectname;
	
	//bi-directional many-to-one association to User
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="createdby")
	private User user;

	//bi-directional many-to-one association to Businessprocess
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Businessprocess> businessprocesses;

	//bi-directional many-to-one association to Environment
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Environment> environments;

	//bi-directional many-to-one association to Function
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Function> functions;

	//bi-directional many-to-one association to Module
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Module> modules;

	//bi-directional many-to-one association to Objects
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Objects> objects;

	//bi-directional many-to-one association to Screen
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Screen> screens;

	//bi-directional many-to-one association to Suite
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Suite> suites;

	//bi-directional many-to-one association to Test
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Test> tests;

	//bi-directional many-to-one association to Useraccess
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Useraccess> useraccesses;
	
	//bi-directional many-to-one association to Suiteinstance
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Suiteinstance> suiteinstances;

	//bi-directional many-to-one association to Testinstance
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Testinstance> testinstances;
	
	
	//bi-directional many-to-one association to Datarepo
	@JsonBackReference
	@OneToMany(mappedBy="project")
	private List<Datarepo> datarepos;


	public Project() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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


	public long getProjectid() {
		return this.projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}


	public List<Businessprocess> getBusinessprocesses() {
		return this.businessprocesses;
	}

	public void setBusinessprocesses(List<Businessprocess> businessprocesses) {
		this.businessprocesses = businessprocesses;
	}

	public Businessprocess addBusinessprocess(Businessprocess businessprocess) {
		getBusinessprocesses().add(businessprocess);
		businessprocess.setProject(this);

		return businessprocess;
	}

	public Businessprocess removeBusinessprocess(Businessprocess businessprocess) {
		getBusinessprocesses().remove(businessprocess);
		businessprocess.setProject(null);

		return businessprocess;
	}

	public List<Environment> getEnvironments() {
		return this.environments;
	}

	public void setEnvironments(List<Environment> environments) {
		this.environments = environments;
	}

	public Environment addEnvironment(Environment environment) {
		getEnvironments().add(environment);
		environment.setProject(this);

		return environment;
	}

	public Environment removeEnvironment(Environment environment) {
		getEnvironments().remove(environment);
		environment.setProject(null);

		return environment;
	}

	public List<Function> getFunctions() {
		return this.functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public Function addFunction(Function function) {
		getFunctions().add(function);
		function.setProject(this);

		return function;
	}

	public Function removeFunction(Function function) {
		getFunctions().remove(function);
		function.setProject(null);

		return function;
	}

	public List<Module> getModules() {
		return this.modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public Module addModule(Module module) {
		getModules().add(module);
		module.setProject(this);

		return module;
	}

	public Module removeModule(Module module) {
		getModules().remove(module);
		module.setProject(null);

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
		object.setProject(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setProject(null);

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
		screen.setProject(this);

		return screen;
	}

	public Screen removeScreen(Screen screen) {
		getScreens().remove(screen);
		screen.setProject(null);

		return screen;
	}

	public List<Suite> getSuites() {
		return this.suites;
	}

	public void setSuites(List<Suite> suites) {
		this.suites = suites;
	}

	public Suite addSuite(Suite suite) {
		getSuites().add(suite);
		suite.setProject(this);

		return suite;
	}

	public Suite removeSuite(Suite suite) {
		getSuites().remove(suite);
		suite.setProject(null);

		return suite;
	}

	public List<Test> getTests() {
		return this.tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public Test addTest(Test test) {
		getTests().add(test);
		test.setProject(this);

		return test;
	}

	public Test removeTest(Test test) {
		getTests().remove(test);
		test.setProject(null);

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
		useraccess.setProject(this);

		return useraccess;
	}

	public Useraccess removeUseraccess(Useraccess useraccess) {
		getUseraccesses().remove(useraccess);
		useraccess.setProject(null);

		return useraccess;
	}
	

	public List<Datarepo> getDatarepos() {
		return datarepos;
	}

	public void setDatarepos(List<Datarepo> datarepos) {
		this.datarepos = datarepos;
	}


}
