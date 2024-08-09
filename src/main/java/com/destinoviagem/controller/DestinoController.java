package com.destinoviagem.controller;

import com.destinoviagem.model.Destino;
import com.destinoviagem.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public List<Destino> listarDestinos() {
        return destinoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarDestino(@PathVariable Long id) {
        return destinoRepository.findById(id)
                .map(destino -> new ResponseEntity<>(destino, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Destino> criarDestino(@RequestBody Destino destino) {
        Destino novoDestino = destinoRepository.save(destino);
        return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizarDestino(@PathVariable Long id, @RequestBody Destino destino) {
        if (destinoRepository.existsById(id)) {
            destino.setId(id);
            Destino destinoAtualizado = destinoRepository.save(destino);
            return new ResponseEntity<>(destinoAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDestino(@PathVariable Long id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ordenados")
    public List<Destino> listarDestinosOrdenados() {
        return destinoRepository.findAllByOrderByNomeAsc();
    }

    //"/ordenados" => retorna todos os destinos ordenados pelo nome
}