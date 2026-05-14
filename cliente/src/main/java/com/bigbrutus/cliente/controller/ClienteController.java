package com.bigbrutus.cliente.controller;

import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.model.Cliente;
import com.bigbrutus.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id_cliente){
        ClienteDTO cliente = clienteService.findbyId(id_cliente);
        if (cliente== null)
            return ResponseEntity.notFound().build();
        return  ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente){
        Cliente clienteNuevo = clienteService.save(cliente);
        return new ResponseEntity<>(clienteNuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id_cliente){
        clienteService.delete(id_cliente);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id_cliente, @RequestBody Cliente cliente){
        Cliente clienteActualizado = clienteService.update(id_cliente,cliente);
        if(clienteActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(clienteActualizado);
    }
}
