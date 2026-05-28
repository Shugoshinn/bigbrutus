package com.bigbrutus.repartidores.config;

import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.repository.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Override
    public void run(String... args) throws Exception {

        if (repartidorRepository.count() == 0) {

        List<Repartidor> repartidoresDePrueba = List.of(
                // Vehículos: 1,4,6,8,10 = AUTO (Clase B) | 2,5,9 = MOTO (Clase C) | 3,7 = SCOOTER (Clase C)
                new Repartidor(null, "Juan", "Pérez", 987654321, "Clase B", EstadoRepartidor.DISPONIBLE, 1L),
                new Repartidor(null, "María", "González", 912345678, "Clase C", EstadoRepartidor.EN_REPARTO, 2L),
                new Repartidor(null, "Diego", "Muñoz", 923456789, "Clase C", EstadoRepartidor.DISPONIBLE, 3L),
                new Repartidor(null, "Ana", "Silva", 934567890, "Clase B", EstadoRepartidor.CON_LICENCIA, 4L),
                new Repartidor(null, "Carlos", "Rojas", 945678901, "Clase C", EstadoRepartidor.EN_VACACIONES, 5L),
                new Repartidor(null, "Valentina", "Soto", 956789012, "Clase B", EstadoRepartidor.DISPONIBLE, 6L),
                new Repartidor(null, "José", "Contreras", 967890123, "Clase C", EstadoRepartidor.EN_REPARTO, 7L),
                new Repartidor(null, "Camila", "Morales", 978901234, "Clase B", EstadoRepartidor.DISPONIBLE, 8L),
                new Repartidor(null, "Martín", "Herrera", 989012345, "Clase C", EstadoRepartidor.DISPONIBLE, 9L),
                new Repartidor(null, "Lucía", "Castro", 990123456, "Clase B", EstadoRepartidor.DISPONIBLE, 10L)
        );

        repartidorRepository.saveAll(repartidoresDePrueba);
        System.out.println(">>>>> Base de Datos Repartidores Inicializada: 10 registros cargados. <<<<<");
        }
    }
}
