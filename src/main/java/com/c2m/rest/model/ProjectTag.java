package com.c2m.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "project")
public class ProjectTag {

	public ProjectTag() {
		super();
	}

	private Long id;
	private String name;
	private String updated;
	private Integer jobsInProject;
	private List<Job> jobs;
	private Integer status;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Integer getJobsInProject() {
		return jobsInProject;
	}

	public void setJobsInProject(Integer jobsInProject) {
		this.jobsInProject = jobsInProject;
	}

	@XmlElementWrapper(name = "jobs")
	@XmlElement(name = "job")
	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}