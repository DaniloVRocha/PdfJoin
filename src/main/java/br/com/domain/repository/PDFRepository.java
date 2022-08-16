package br.com.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.domain.model.PDF;

@Repository
public interface PDFRepository extends JpaRepository<PDF, Long>{

}
