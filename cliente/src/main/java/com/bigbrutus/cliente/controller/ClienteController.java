package com.bigbrutus.cliente.controller;

import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.model.Cliente;
import com.bigbrutus.cliente.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.findDTOList());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {

        ClienteDTO cliente = clienteService.findDTO(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    // REGISTRAR
    @PostMapping
    public ResponseEntity<Cliente> registrar(@RequestBody Cliente cliente) {

        Cliente clienteNuevo = clienteService.save(cliente);

        return new ResponseEntity<>(clienteNuevo, HttpStatus.CREATED);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id,
                                              @RequestBody Cliente cliente) {

        Cliente clienteActualizado = clienteService.update(id, cliente);

        if (clienteActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clienteActualizado);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        clienteService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // ENDPOINTS PERSONALIZADOS
    // =========================

    // BUSCAR POR EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDTO> buscarPorEmail(@PathVariable String email) {

        ClienteDTO cliente = clienteService.findByEmail(email);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    // BUSCAR POR NOMBRE
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClienteDTO>> buscarPorNombre(@PathVariable String nombre) {

        List<ClienteDTO> clientes = clienteService.findByNombre(nombre);

        return ResponseEntity.ok(clientes);
    }

    // LISTAR CLIENTES ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<ClienteDTO>> listarActivos() {

        return ResponseEntity.ok(clienteService.findActivos());
    }

    // ACTUALIZAR SOLO ESTADO ACTIVO
    @PatchMapping("/{id}/activo/{activo}")
    public ResponseEntity<Cliente> actualizarActivo(@PathVariable Long id,
                                                    @PathVariable boolean activo) {

        Cliente clienteActualizado = clienteService.updateActivo(id, activo);

        if (clienteActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clienteActualizado);
    }
}