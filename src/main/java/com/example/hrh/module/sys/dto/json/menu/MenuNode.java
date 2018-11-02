package com.example.hrh.module.sys.dto.json.menu;



import com.example.hrh.module.sys.dao.entities.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 21:36
 */
public class MenuNode implements Serializable,Comparable<MenuNode>{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String url;
	
	private Integer order;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean check;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<MenuNode> subs = Collections.emptyList();

	public MenuNode(Menu model) {
		setId(model.getId());
		setName(model.getName());
		setOrder(model.getOrder());
		setUrl(model.getUrl());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<MenuNode> getSubs() {
		return subs;
	}

	public void setSubs(List<MenuNode> subs) {
		this.subs = subs;
	}

	public Boolean isCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	@Override
	public int compareTo(MenuNode o) {
		return this.order - o.order;
	}

	@Override
	public String toString() {
		return "MenuNode{" +
				"id=" + id +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				", order=" + order +
				", check=" + check +
				", subs=" + subs +
				'}';
	}
}
