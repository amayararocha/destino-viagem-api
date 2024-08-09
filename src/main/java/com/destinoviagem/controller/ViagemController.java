package com.destinoviagem.controller;

import com.destinoviagem.dto.ViagemDTO;
import com.destinoviagem.model.Viagem;
import com.destinoviagem.repository.DestinoRepository;
import com.destinoviagem.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Viagem> criarViagem(@RequestBody ViagemDTO viagemDTO) {
        if (destinoRepository.existsById(viagemDTO.getDestinoId()) && viagemDTO.getDataInicio().isBefore(viagemDTO.getDataTermino())) {
            Viagem novaViagem = new Viagem();
            novaViagem.setTitulo(viagemDTO.getTitulo());
            novaViagem.setDestino(destinoRepository.findById(viagemDTO.getDestinoId()).orElse(null));
            novaViagem.setDataInicio(viagemDTO.getDataInicio());
            novaViagem.setDataTermino(viagemDTO.getDataTermino());

            return new ResponseEntity<>(viagemRepository.save(novaViagem), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viagem> atualizarViagem(@PathVariable Long id, @RequestBody ViagemDTO viagemDTO) {
        Optional<Viagem> viagemExistente = viagemRepository.findById(id);
        if (viagemExistente.isPresent() && destinoRepository.existsById(viagemDTO.getDestinoId()) && viagemDTO.getDataInicio().isBefore(viagemDTO.getDataTermino())) {
            Viagem viagem = viagemExistente.get();
            viagem.setTitulo(viagemDTO.getTitulo());
            viagem.setDestino(destinoRepository.findById(viagemDTO.getDestinoId()).orElse(null));
            viagem.setDataInicio(viagemDTO.getDataInicio());
            viagem.setDataTermino(viagemDTO.getDataTermino());

            return new ResponseEntity<>(viagemRepository.save(viagem), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarViagem(@PathVariable Long id) {
        if (viagemRepository.existsById(id)) {
            viagemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
