package com.fatec.fatura.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.fatura.model.Fatura;
@Repository
public interface FaturaRepository extends JpaRepository <Fatura, Long>{

}
