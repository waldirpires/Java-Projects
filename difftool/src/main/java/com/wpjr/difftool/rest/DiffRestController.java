package com.wpjr.difftool.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/diff")
public class DiffRestController {

	@RequestMapping(value = "/{id}/{side}", method = RequestMethod.POST, produces = "application/json")
	public boolean acceptDoc(@PathVariable("id") Long id, @PathVariable("side") String side)
	{
		if ("left".equals(side))
		{
			
		}
		
		return true;
	}
}
