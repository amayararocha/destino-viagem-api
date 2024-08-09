package com.destinoviagem.repository;

import com.destinoviagem.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    List<Destino> findAllByOrderByNomeAsc();
}


