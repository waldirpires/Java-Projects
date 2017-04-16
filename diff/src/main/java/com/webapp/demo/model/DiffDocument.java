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
	
	public void updateSide(String side, String value)
	{
		if (StringUtils.isEmpty(side))
		{
			return;
		}
		
		if (DIFF_SIDE_LEFT.equals(side) && !StringUtils.isEmpty(value))
		{
			setLeft(value);
		} else if (DIFF_SIDE_RIGHT.equals(side) && !StringUtils.isEmpty(value))
		{
			setRight(value);
		}

	}
	
	public Integer getLengthLeft() {
		return left == null?0:left.length();
	}
	
	public Integer getLengthRight() {
		return right == null?0:right.length();
	}

	public boolean isEmpty()
	{
		return StringUtils.isEmpty(left) && StringUtils.isEmpty(right);
	}
	
	public boolean isEqual()
	{
		return left !=null&& right!=null && left.equals(right);
	}
	
	public boolean isSameSize()
	{
		return left !=null&& right!=null && left.length() == right.length();
	}
	
	public boolean isComplete()
	{
		return left != null && right != null;
	}
}
