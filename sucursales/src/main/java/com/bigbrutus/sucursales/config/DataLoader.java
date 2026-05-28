package com.bigbrutus.sucursales.config;

import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        if (sucursalRepository.count() == 0) {

            List<Sucursal> sucursalesDePrueba = List.of(
                    new Sucursal(null, "Brutus Providencia", "Av. Providencia 1234", 222334455, "Providencia", Time.valueOf("08:00:00"), Time.valueOf("22:00:00"), true, TipoSucursal.PARA_SERVIR),
                    new Sucursal(null, "Brutus Santiago Centro", "Alameda 456", 222334456, "Santiago", Time.valueOf("08:30:00"), Time.valueOf("21:30:00"), true, TipoSucursal.PARA_LLEVAR),
                    new Sucursal(null, "Brutus Las Condes", "Av. Vitacura 7890", 222334457, "Las Condes", Time.valueOf("09:00:00"), Time.valueOf("23:00:00"), true, TipoSucursal.PARA_SERVIR),
                    new Sucursal(null, "Brutus Ñuñoa", "Irarrázaval 555", 222334458, "Ñuñoa", Time.valueOf("08:00:00"), Time.valueOf("22:00:00"), true, TipoSucursal.PARA_SERVIR),
                    new Sucursal(null, "Brutus Maipú", "Av. Pajaritos 2400", 222334459, "Maipú", Time.valueOf("09:00:00"), Time.valueOf("21:00:00"), false, TipoSucursal.PARA_LLEVAR), // En remodelación
                    new Sucursal(null, "Brutus Viña Express", "Libertad 321", 322556677, "Viña del Mar", Time.valueOf("10:00:00"), Time.valueOf("22:30:00"), true, TipoSucursal.PARA_LLEVAR),
                    new Sucursal(null, "Brutus Concepción Centro", "O'Higgins 890", 412556688, "Concepción", Time.valueOf("08:30:00"), Time.valueOf("22:00:00"), true, TipoSucursal.PARA_SERVIR),
                    new Sucursal(null, "Brutus La Florida", "Vicuña Mackenna 9000", 222334460, "La Florida", Time.valueOf("08:00:00"), Time.valueOf("23:00:00"), true, TipoSucursal.PARA_SERVIR),
                    new Sucursal(null, "Brutus Antofagasta", "Av. Brasil 450", 552667788, "Antofagasta", Time.valueOf("09:00:00"), Time.valueOf("22:00:00"), true, TipoSucursal.PARA_LLEVAR),
                    new Sucursal(null, "Brutus San Miguel", "Gran Avenida 3400", 222334461, "San Miguel", Time.valueOf("08:30:00"), Time.valueOf("21:30:00"), true, TipoSucursal.PARA_LLEVAR)
            );

            sucursalRepository.saveAll(sucursalesDePrueba);
            System.out.println(">>>>> Base de Datos Sucursales Inicializada: 10 registros cargados. <<<<<");
        }
    }
}
