package com.webapp.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.webapp.demo.model.DiffDocument;

public interface DiffDocumentRepository extends CrudRepository<DiffDocument, Long> {

}
