package com.bigbrutus.cliente.config;

import com.bigbrutus.cliente.model.Cliente;
import com.bigbrutus.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception{
        if(clienteRepository.count()==0){
            Cliente c1 = new Cliente(null,"nicolas","rubilar","+56912345678","nico@gmail.com","Avenida Siempre viva 123", LocalDate.now(),true);

            clienteRepository.save(c1);

        }
    }
}
