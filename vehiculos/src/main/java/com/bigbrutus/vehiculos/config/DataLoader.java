package com.bigbrutus.vehiculos.config;

import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import com.bigbrutus.vehiculos.model.Vehiculo;
import com.bigbrutus.vehiculos.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (vehiculoRepository.count() == 0) {

            List<Vehiculo> vehiculosDePrueba = List.of(
                    new Vehiculo(null, "AB-CD-12", "Toyota", "Yaris", 2020, TipoVehiculo.AUTO, EstadoVehiculo.DISPONIBLE),
                    new Vehiculo(null, "XY-98-ZW", "Honda", "CB500X", 2022, TipoVehiculo.MOTO, EstadoVehiculo.EN_REPARTO),
                    new Vehiculo(null, "SCOOT-01", "Xiaomi", "Mi Pro 2", 2023, TipoVehiculo.SCOOTER, EstadoVehiculo.DISPONIBLE),
                    new Vehiculo(null, "GH-IJ-34", "Hyundai", "Accent", 2018, TipoVehiculo.AUTO, EstadoVehiculo.MANTENCION),
                    new Vehiculo(null, "KL-MN-56", "Yamaha", "FZ25", 2021, TipoVehiculo.MOTO, EstadoVehiculo.FUERA_DE_SERVICIO),
                    new Vehiculo(null, "OP-QR-78", "Chevrolet", "Sail", 2019, TipoVehiculo.AUTO, EstadoVehiculo.DISPONIBLE),
                    new Vehiculo(null, "SCOOT-02", "Segway", "Ninebot Max", 2024, TipoVehiculo.SCOOTER, EstadoVehiculo.EN_REPARTO),
                    new Vehiculo(null, "ST-UV-90", "Suzuki", "Swift", 2022, TipoVehiculo.AUTO, EstadoVehiculo.DISPONIBLE),
                    new Vehiculo(null, "WX-YZ-11", "BMW", "G310GS", 2023, TipoVehiculo.MOTO, EstadoVehiculo.MANTENCION),
                    new Vehiculo(null, "AA-BB-00", "Ford", "Ranger", 2017, TipoVehiculo.AUTO, EstadoVehiculo.DISPONIBLE)
            );

            vehiculoRepository.saveAll(vehiculosDePrueba);

            System.out.println(">>>>> Base de datos inicializada: Se cargaron " + vehiculosDePrueba.size() + " vehículos de prueba. <<<<<");
        } else {
            System.out.println(">>>>> La base de datos ya contiene registros. Se omitió la carga inicial. <<<<<");
        }
    }
}
