package com.webapp.demo.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiffDocumentTest {

	private DiffDocument doc;
	
	@Before
	public void setup()
	{
		doc = new DiffDocument();
	}

	@After
	public void tearDown()
	{
		doc = null;
	}
	
	@Test
	public void testSetLeft()
	{
		String s = "blah";
		doc.setLeft(s);
		Assert.assertEquals(s.length(), doc.getLengthLeft().intValue());
		
		doc.setLeft("");
		Assert.assertEquals(0, doc.getLengthLeft().intValue());

		doc.setLeft(null);
		Assert.assertNull(doc.getLengthLeft());
	}

	@Test
	public void testSetLeftNull()
	{
		doc.setLeft(null);
		Assert.assertNull(doc.getLengthLeft());
	}

	@Test
	public void testSetRight()
	{
		String s = "blah";
		doc.setRight(s);
		Assert.assertEquals(s.length(), doc.getLengthRight().intValue());
		
		doc.setRight("");
		Assert.assertEquals(0, doc.getLengthRight().intValue());

		doc.setRight(null);
		Assert.assertNull(doc.getLengthRight());
	}

	@Test
	public void testSetRightNull()
	{
		doc.setRight(null);
		Assert.assertNull(doc.getLengthRight());
	}
	
	@Test
	public void testUpdateSide()
	{
		String value = "test";
		doc.updateSide(DiffDocument.DIFF_SIDE_LEFT, value);
		Assert.assertEquals(value, doc.getLeft());
		Assert.assertEquals(value.length(), doc.getLengthLeft().intValue());
		
		value = "test2";
		doc.updateSide(DiffDocument.DIFF_SIDE_RIGHT, value);
		Assert.assertEquals(value, doc.getRight());
		Assert.assertEquals(value.length(), doc.getLengthRight().intValue());
	}

	@Test
	public void testUpdateSideInvalid()
	{
		String value = null;
		doc.updateSide(DiffDocument.DIFF_SIDE_LEFT, value);
		Assert.assertEquals(value, doc.getLeft());
		Assert.assertNull(doc.getLengthLeft());
		
		doc.updateSide(DiffDocument.DIFF_SIDE_RIGHT, value);
		Assert.assertEquals(value, doc.getRight());
		Assert.assertNull(doc.getLengthRight());
		
		doc.updateSide("blah", value);
		Assert.assertEquals(value, doc.getRight());
		Assert.assertNull(doc.getLengthRight());
	}
}
