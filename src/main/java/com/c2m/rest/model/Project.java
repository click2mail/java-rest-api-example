package com.c2m.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "projects")
public class Project {
	
	private Integer status;
	private String description;
	private List<ProjectTag> projects;

	@XmlElementWrapper(name = "projectList")
	@XmlElement(name = "project")
	public List<ProjectTag> getProjects() {
		return projects;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}