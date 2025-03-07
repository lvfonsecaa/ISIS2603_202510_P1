package co.edu.uniandes.dse.parcial1.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService 
{
    @Autowired
	private ConciertoRepository conciertoRepository;

	@Autowired
	private EstadioRepository estadioRepository;

    @Transactional
	public ConciertoEntity addConcierto(Long conciertoId, Long estadioId) throws IllegalOperationException 
    {

		log.info("Inicia proceso de asociar el concierto con id {} al estadio con id{} " + conciertoId, estadioId);
		Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
        Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
		if (estadioEntity.get().getCapacidadMaxima()>conciertoEntity.get().getCapacidadAforo())
			throw new IllegalOperationException("La capacidad de un concierto no debe superar la capacidad del estadio.");
		if (estadioEntity.get().getPrecioAlquiler()>conciertoEntity.get().getPresupuesto())
			throw new IllegalOperationException("El precio de alquiler del estadio no debe superar el presupuesto del concierto.");

        log.info("Inicia proceso de creación de concierto");
        Calendar calendar = Calendar.getInstance();  
        if(conciertoEntity.get().getFecha().compareTo(calendar.getTime()) < 2) {
			throw new IllegalOperationException("Siempre debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio.");
	    }

		conciertoEntity.get().setEstadio(estadioEntity.get());
		log.info("Termina proceso de asociar el concierto con id = {} al estadio con id {}", conciertoId, estadioId);
		return conciertoEntity.get();
	}


}
