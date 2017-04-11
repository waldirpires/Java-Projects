package com.wpjr.difftool.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.wpjr.difftool.model.DiffDocument;
import com.wpjr.difftool.repository.DiffDocumentRepository;

public class DiffDocumentServiceImpl {

	@Autowired
	private DiffDocumentRepository diffDocumentRepository;


	public DiffDocument saveDocument(Long id, String left, String right)
	{
		DiffDocument diffDoc = null;
		if (diffDocumentRepository.exists(id))
		{
			diffDoc = diffDocumentRepository.findOne(id);
		}
		else {
			diffDoc = new DiffDocument();
			diffDoc.setId(id);
		}
		diffDoc.setLeft(left);
		diffDoc.setRight(right);
		diffDoc = diffDocumentRepository.save(diffDoc);
		return diffDoc;
	}
	
	public DiffDocument getDocument(Long id)
	{
		return diffDocumentRepository.findOne(id);
	}
}
