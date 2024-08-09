package com.destinoviagem.controller;

import com.destinoviagem.model.Destino;
import com.destinoviagem.model.Viagem;
import com.destinoviagem.repository.DestinoRepository;
import com.destinoviagem.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public List<Viagem> listarViagens() {
        return viagemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viagem> buscarViagem(@PathVariable Long id) {
        return viagemRepository.findById(id)
                .map(viagem -> new ResponseEntity<>(viagem, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Viagem> criarViagem(@RequestBody Viagem viagem) {
        if (destinoRepository.existsById(viagem.getDestino().getId())) {
            Viagem novaViagem = viagemRepository.save(viagem);
            return new ResponseEntity<>(novaViagem, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viagem> atualizarViagem(@PathVariable Long id, @RequestBody Viagem viagem) {
        if (viagemRepository.existsById(id)) {
            viagem.setId(id);
            Viagem viagemAtualizada = viagemRepository.save(viagem);
            return new ResponseEntity<>(viagemAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarViagem(@PathVariable Long id) {
        if (viagemRepository.existsById(id)) {
            viagemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/destinos/{destinoId}/viagens")
    public List<Viagem> listarViagensPorDestino(@PathVariable Long destinoId) {
        return viagemRepository.findByDestinoId(destinoId);
    }

    @GetMapping("/titulo/{titulo}")
    public List<Viagem> buscarViagensPorTitulo(@PathVariable String titulo) {
        return viagemRepository.findByTituloContainingIgnoreCase(titulo);
    }

    //"/titulo/{titulo}" => retorna todas as viagens que tem um titulo especifico
}