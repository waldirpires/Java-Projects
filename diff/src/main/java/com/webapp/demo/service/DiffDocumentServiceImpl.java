package com.webapp.demo.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.demo.model.DiffDocument;
import com.webapp.demo.repository.DiffDocumentRepository;

@Service
public class DiffDocumentServiceImpl implements DiffDocumentService{

	@Autowired
	private DiffDocumentRepository diffDocumentRepository;


	@Override
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
		diffDoc.updateSide(DiffDocument.DIFF_SIDE_LEFT, left);
		diffDoc.updateSide(DiffDocument.DIFF_SIDE_RIGHT, right);
		diffDoc = diffDocumentRepository.save(diffDoc);
		return diffDoc;
	}

	@Override
	public String doDiff(Long id)
	{
		String result = "blah";
		
		DiffDocument diffDoc = null;
		if (diffDocumentRepository.exists(id))
		{
			diffDoc = diffDocumentRepository.findOne(id);
		}
		
		
		return result;
	}
	
	
	@Override
	public DiffDocument getDocument(Long id)
	{
		
		DiffDocument diffDoc = diffDocumentRepository.findOne(id);
		if (diffDoc == null)
		{
			throw new DiffDocumentException("ERROR: Diff document with ID " + id + " does not exist.");
		}
		
		if (!StringUtils.isEmpty(diffDoc.getLeft()) && !StringUtils.isEmpty(diffDoc.getRight()))
		{
			diffDoc.setDiff(StringUtils.difference(diffDoc.getLeft(), diffDoc.getRight()));
			diffDoc = diffDocumentRepository.save(diffDoc);
		}
		
		return diffDoc;		
	}

	@Override
	public boolean delete(Long id)
	{
		diffDocumentRepository.delete(id);
		return diffDocumentRepository.findOne(id) == null;
	}
}
