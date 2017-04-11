package com.wpjr.difftool.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class DiffDocument  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	Long id;
	@Column(nullable = true)
	String left;
	@Column(nullable = true)
	String right;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLeft() {
		return left;
	}
	
	public void setLeft(String left) {
		this.left = left;
	}
	
	public String getRight() {
		return right;
	}
	
	public void setRight(String right) {
		this.right = right;
	}
}
