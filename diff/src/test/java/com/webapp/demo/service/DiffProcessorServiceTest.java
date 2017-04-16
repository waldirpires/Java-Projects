package com.webapp.demo.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.webapp.demo.model.DiffDocument;
import com.webapp.demo.model.DiffOccurrence;
import com.webapp.demo.model.DiffProcessorReport;

public class DiffProcessorServiceTest {

	private DiffProcessorService diffProcessorService = new DiffProcessorServiceImpl();
	private DiffDocument doc;
	
	@Before
	public void setup()
	{
		doc = new DiffDocument();
	}

	@Test
	public void testExecuteDiffToolEqual()
	{
		String right = "dGhlIGJvb2sgaXMgb24gdGhlIHRhYmxl";
		String left = "dGhlIGJvb2sgaXMgb24gdGhlIHRhYmxl";
		Long id = new Long(2);
		
		doc.setId(id);
		doc.setLeft(left);
		doc.setRight(right);
		
		DiffProcessorReport report = diffProcessorService.executeDiffTool(doc);
		Assert.assertThat(report.getId(), CoreMatchers.equalTo(doc.getId()));
		Assert.assertThat(report.isEqual(), CoreMatchers.equalTo(true));
	}
	
	@Test
	public void testExecuteDiffToolDifferentSizes()
	{
		String right = "test1";
		String left = "test12";
		Long id = new Long(2);
		doc.setId(id);
		doc.setLeft(left);
		doc.setRight(right);
		
		DiffProcessorReport report = diffProcessorService.executeDiffTool(doc);
		Assert.assertThat(report.getId(), CoreMatchers.equalTo(doc.getId()));
		Assert.assertThat(report.isEqual(), CoreMatchers.equalTo(false));
	}

	@Test
	public void testExecuteDiffToolDifferentWithSameSizes()
	{
		String right = "the book is on the table";
		String left =  "thymbook is an the tombe";
		Long id = new Long(2);
		doc.setId(id);
		doc.setLeft(left);
		doc.setRight(right);
		
		DiffProcessorReport report = diffProcessorService.executeDiffTool(doc);
		Assert.assertThat(report.getId(), CoreMatchers.equalTo(doc.getId()));
		Assert.assertThat(report.isEqual(), CoreMatchers.equalTo(false));
		Assert.assertThat(report.getOccurrences().size(), CoreMatchers.equalTo(3));
		verifyPositionAndSize(report, 0, 2, 2);
		verifyPositionAndSize(report, 1, 12, 1);
		verifyPositionAndSize(report, 2, 20, 3);
	}

	private void verifyPositionAndSize(DiffProcessorReport report, int index, int position, int size)
	{
		Assert.assertThat(report.getOccurrences().size() > index, CoreMatchers.equalTo(true));
		Assert.assertThat(report.getOccurrences().get(index), CoreMatchers.notNullValue());
		Assert.assertThat(report.getOccurrences().get(index).getPosition(), CoreMatchers.equalTo(position));
		Assert.assertThat(report.getOccurrences().get(index).getSize(), CoreMatchers.equalTo(size));
	}
	
	@Test
	public void testExecuteDiffToolAllDifferentWithSameSizes()
	{
		String right = "the book is on the table";
		String left =  "fdgkjgsnkjfskjsfhksfdkjf";
		Long id = new Long(2);
		doc.setId(id);
		doc.setLeft(left);
		doc.setRight(right);
		
		DiffProcessorReport report = diffProcessorService.executeDiffTool(doc);
		Assert.assertThat(report.getId(), CoreMatchers.equalTo(doc.getId()));
		Assert.assertThat(report.isEqual(), CoreMatchers.equalTo(false));
		Assert.assertThat(report.getOccurrences().size(), CoreMatchers.equalTo(1));
	}
}
