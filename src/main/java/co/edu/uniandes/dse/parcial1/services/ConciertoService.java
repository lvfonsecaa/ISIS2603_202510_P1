package co.edu.uniandes.dse.parcial1.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService 
{
    @Autowired
	ConciertoRepository conciertoRepository;

    @Transactional
	public ConciertoEntity createConcierto(ConciertoEntity conciertoEntity) throws IllegalOperationException {
		log.info("Inicia proceso de creación de concierto");
		Calendar calendar = Calendar.getInstance();
		if(conciertoEntity.getFecha().compareTo(calendar.getTime()) < 0) {
			throw new IllegalOperationException("La fecha del concierto ya pasó");
	    }
        if (conciertoEntity.getCapacidadAforo()<10)
        {
            throw new IllegalOperationException("El aforo tiene que ser mas de 10");
        }
        if (conciertoEntity.getPresupuesto()<1000)
        {
            throw new IllegalOperationException("El aforo tiene que ser mas de 10");
        }
        log.info("Termina proceso de creación de concierto");
		return conciertoRepository.save(conciertoEntity);
	}
}
