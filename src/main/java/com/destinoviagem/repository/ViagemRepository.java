package com.destinoviagem.repository;

import com.destinoviagem.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    List<Viagem> findByDestinoId(Long destinoId);
    List<Viagem> findByTituloContainingIgnoreCase(String titulo);
}