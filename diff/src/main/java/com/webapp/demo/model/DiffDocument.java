package com.webapp.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;

@Entity(name = "diff_document")
public class DiffDocument  implements Serializable {

	public static final String DIFF_SIDE_RIGHT = "right";

	public static final String DIFF_SIDE_LEFT = "left";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(nullable = true)
	String left;
	@Column(nullable = true)
	String right;
	@Column(nullable = true)
	String diff;
	@Column(nullable = true)
	Integer lengthRight;
	@Column(nullable = true)
	Integer lengthLeft;
	
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
		if (this.left != null)
		{
			setLengthLeft(this.left.length());
		} else 
		{
			setLengthLeft(null);
		}
	}
	
	public String getRight() {
		return right;
	}
	
	public void setRight(String right) {
		this.right = right;
		if (this.right != null)
		{
			setLengthRight(this.right.length());
		} else 
		{
			setLengthRight(null);
		}
	}
	
	public void updateSide(String side, String value)
	{
		if (StringUtils.isEmpty(side))
		{
			return;
		}
		
		if (DIFF_SIDE_LEFT.equals(side))
		{
			setLeft(value);
		} else if (DIFF_SIDE_RIGHT.equals(side))
		{
			setRight(value);
		}

	}
	
	public String getDiff() {
		return diff;
	}
	
	public void setDiff(String diff) {
		this.diff = diff;
	}
	
	public Integer getLengthLeft() {
		return lengthLeft;
	}
	
	public Integer getLengthRight() {
		return lengthRight;
	}
	
	public void setLengthLeft(Integer lengthLeft) {
		this.lengthLeft = lengthLeft;
	}
	
	public void setLengthRight(Integer lengthRight) {
		this.lengthRight = lengthRight;
	}
}
