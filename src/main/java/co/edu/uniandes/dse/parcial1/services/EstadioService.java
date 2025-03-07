package co.edu.uniandes.dse.parcial1.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService 
{

    @Autowired
	EstadioRepository estadioRepository;

	@Transactional
	public EstadioEntity createEstadio(EstadioEntity estadio) throws IllegalOperationException {
		log.info("Inicia proceso de creación del estadio");
		if(estadio.getNombre().length()<3) {
			throw new IllegalOperationException("El nombre de la ciudad debe tener minimo 3 letras");
	    }
        if(estadio.getCapacidadMaxima()<=1000) {
			throw new IllegalOperationException("La capacidad debe ser de mas de 1000");
	    }
        if(estadio.getPrecioAlquiler()<=100000) {
			throw new IllegalOperationException("El precio debe ser de superior a los 100.000 dolares");
	    }
		
		return estadioRepository.save(estadio);
	}

}
