package com.webapp.demo.rest;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void test_getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/hello").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"msg\": \"Hello REST World!\"}")));
	}

	/**
	 * Scenario: submit left then right content of equal data
	 * Result: expected equal, no errors
	 * @throws Exception
	 */
	@Test
	public void test_createDiffLeftThenRightEqual_shouldBeSuccessful() throws Exception {
		int id = 2;
		String path = "/v1/diff/"+id+"/left";
		String content = "blah";
		ResultActions result = mvc.perform(MockMvcRequestBuilders.post(path).characterEncoding("utf8").
				contentType("text/plain").accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk());

		String contentAsString = result.andReturn().getResponse().getContentAsString();
		
		DocumentContext json = JsonPath.parse(contentAsString);
		Object read = json.read("$['id']");
		
		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"left\":\"blah\""), CoreMatchers.equalTo(true));

		path = "/v1/diff/"+id+"/right";
		content = "blah";
		result = mvc.perform(MockMvcRequestBuilders.post(path).characterEncoding("utf8").
				contentType("text/plain").accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk());

		contentAsString = result.andReturn().getResponse().getContentAsString();
		
		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"left\":\"blah\""), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"right\":\"blah\""), CoreMatchers.equalTo(true));

		path = "/v1/diff/"+id;
		result = mvc.perform(MockMvcRequestBuilders.get(path).characterEncoding("utf8").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		contentAsString = result.andReturn().getResponse().getContentAsString();

		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"equal\":true"), CoreMatchers.equalTo(true));
	}

	/**
	 * Scenario: submit left then right content of equal data
	 * Result: expected equal, no errors
	 * @throws Exception
	 */
	@Test
	public void test_createDiffLeftThenRightNotEqual_shouldBeSuccessful() throws Exception {
		int id = 3;
		String path = "/v1/diff/"+id+"/left";
		String content = "blah";
		ResultActions result = mvc.perform(MockMvcRequestBuilders.post(path).characterEncoding("utf8").
				contentType("text/plain").accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk());

		String contentAsString = result.andReturn().getResponse().getContentAsString();
		
		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"left\":\""+content+"\""), CoreMatchers.equalTo(true));

		path = "/v1/diff/"+id+"/right";
		content = "bleeh";
		result = mvc.perform(MockMvcRequestBuilders.post(path).characterEncoding("utf8").
				contentType("text/plain").accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk());

		contentAsString = result.andReturn().getResponse().getContentAsString();
		
		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"left\":\"blah\""), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"right\":\""+content+"\""), CoreMatchers.equalTo(true));

		path = "/v1/diff/"+id;
		result = mvc.perform(MockMvcRequestBuilders.get(path).characterEncoding("utf8").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		contentAsString = result.andReturn().getResponse().getContentAsString();

		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"equal\":false"), CoreMatchers.equalTo(true));
	}

	/**
	 * Scenario: submit left then right content of equal data
	 * Result: expected equal, no errors
	 * @throws Exception
	 */
	@Test
	public void test_createDiffLeftThenRightNotEqualSameSize_shouldBeSuccessful() throws Exception {
		int id = 4;
		String path = "/v1/diff/"+id+"/left";
		String content = "blah";
		ResultActions result = mvc.perform(MockMvcRequestBuilders.post(path).characterEncoding("utf8").
				contentType("text/plain").accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk());

		String contentAsString = result.andReturn().getResponse().getContentAsString();
		
		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"left\":\""+content+"\""), CoreMatchers.equalTo(true));

		path = "/v1/diff/"+id+"/right";
		content = "bleh";
		result = mvc.perform(MockMvcRequestBuilders.post(path).characterEncoding("utf8").
				contentType("text/plain").accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk());

		contentAsString = result.andReturn().getResponse().getContentAsString();
		
		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"left\":\"blah\""), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"right\":\""+content+"\""), CoreMatchers.equalTo(true));

		path = "/v1/diff/"+id;
		result = mvc.perform(MockMvcRequestBuilders.get(path).characterEncoding("utf8").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		contentAsString = result.andReturn().getResponse().getContentAsString();

		Assert.assertThat(contentAsString.contains("\"id\":"+id+","), CoreMatchers.equalTo(true));
		Assert.assertThat(contentAsString.contains("\"equal\":false"), CoreMatchers.equalTo(true));
	}
}
