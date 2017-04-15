package com.wpjr.difftool.service;

import com.wpjr.difftool.model.DiffDocument;

public interface DiffDocumentService {

	DiffDocument getDocument(Long id);

	String doDiff(Long id);

	DiffDocument saveDocument(Long id, String left, String right);

}
