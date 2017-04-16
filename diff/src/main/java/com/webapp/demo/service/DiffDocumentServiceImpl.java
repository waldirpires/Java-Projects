package com.webapp.demo.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.demo.model.DiffDocument;
import com.webapp.demo.model.DiffProcessorReport;
import com.webapp.demo.repository.DiffDocumentRepository;

@Service
public class DiffDocumentServiceImpl implements DiffDocumentService{

	@Autowired
	private DiffDocumentRepository diffDocumentRepository;

	@Autowired
	private DiffProcessorService diffProcessorService;
	

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
		
		if (diffDoc.isEmpty())
		{
			throw new DiffDocumentException("ERROR: Document with ID " + id + " is empty!");
		}
		
		diffDoc = diffDocumentRepository.save(diffDoc);
		return diffDoc;
	}

	@Override
	public DiffProcessorReport doDiff(Long id)
	{
		DiffDocument diffDoc = null;
		if (!diffDocumentRepository.exists(id))
		{
			throw new DiffDocumentException("ERROR: Document with ID " + id + " does not exist!");
		} 
			
		diffDoc = diffDocumentRepository.findOne(id);
		
		if (!diffDoc.isComplete())
		{
			throw new DiffDocumentException("ERROR: Document with ID " + id + " is incomplete!");
		}
		
		return diffProcessorService.executeDiffTool(diffDoc);		
	}
	
	
	@Override
	public DiffDocument getDocument(Long id)
	{
		
		if (!diffDocumentRepository.exists(id))
		{
			throw new DiffDocumentException("ERROR: Diff document with ID " + id + " does not exist.");
		}
		DiffDocument diffDoc = diffDocumentRepository.findOne(id);
		
		return diffDoc;		
	}

	@Override
	public boolean delete(Long id)
	{
		diffDocumentRepository.delete(id);
		return diffDocumentRepository.findOne(id) == null;
	}
}
