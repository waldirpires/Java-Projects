package com.webapp.demo.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
public class DiffDocumentServiceTest {

	@Autowired
	private DiffDocumentService diffDocumentService;
	
	public void testgetDocument(Long id)
	{
		
	}

	public void testdoDiff(Long id)
	{
		
	}

	@Test
	public void testSaveDocument()
	{
		String right = null;
		String left = "left";
		Long id = new Long(2);
		DiffDocument doc = diffDocumentService.saveDocument(id , left, right);
		Assert.assertThat(doc.getId(), CoreMatchers.equalTo(id));
	}

	public void testDelete()
	{
		
	}
	
}
