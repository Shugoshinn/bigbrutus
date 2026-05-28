package com.bigbrutus.producto.config;

import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initProductos(ProductoRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Producto p1 = new Producto();
                p1.setNombre("Pizza Napolitana");
                p1.setDescripcion("Masa tradicional con salsa de tomate, queso y hojas de albaca");
                p1.setCategoria("Comida");
                p1.setPrecio(Long.valueOf("5000"));
                p1.setStock(Integer.parseInt("15"));
                p1.setDisponible(true);

                Producto p2 = new Producto();
                p2.setNombre("Coca cola");
                p2.setDescripcion("Bebida 2L");
                p2.setCategoria("Bebestible");
                p2.setPrecio(Long.valueOf("2000"));
                p2.setStock(Integer.parseInt("12"));
                p2.setDisponible(true);

                repository.save(p1);
                repository.save(p2);
            }
        };
    }
}
