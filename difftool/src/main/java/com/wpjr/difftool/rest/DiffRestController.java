package com.wpjr.difftool.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wpjr.difftool.model.DiffDocument;
import com.wpjr.difftool.service.DiffDocumentService;

@RestController
@RequestMapping("/v1/diff")
public class DiffRestController {

    @Autowired
    private DiffDocumentService diffDocumentService;
	
	@RequestMapping(value = "/{id}/{side}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> acceptDoc(@PathVariable("id") Long id, @PathVariable("side") String side, @RequestBody String payload)
	{
		String left = null;
		String right = null;
		if ("left".equals(side))
		{
			left = payload;
		} else if ("right".equals(side))
		{
			left = payload;
		}

		DiffDocument diffDoc = diffDocumentService.saveDocument(id, left, right);
		
		return ResponseEntity
	}
	
	private DiffDocument validateUser(String id) {
		return this.diffDocumentService.getDocument(id);
	}	
}
