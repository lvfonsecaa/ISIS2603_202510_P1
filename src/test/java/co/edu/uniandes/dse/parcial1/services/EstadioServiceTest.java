package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EstadioService.class)
class EstadioServiceTest 
{
    @Autowired
	private EstadioService estadioService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EstadioEntity> estadioList = new ArrayList<>();
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			EstadioEntity estadioEntity = factory.manufacturePojo(EstadioEntity.class);
			entityManager.persist(estadioEntity);
			estadioList.add(estadioEntity);
		}
	}

    @Test
	void testCreateEstadio() throws IllegalOperationException 
    {
		EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);

		newEntity.setNombre("Bogota");
        newEntity.setCapacidadMaxima(1001);
        newEntity.setPrecioAlquiler(100001);
		EstadioEntity result = estadioService.createEstadio(newEntity);
		assertNotNull(result);

		EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getPrecioAlquiler(), entity.getPrecioAlquiler());
	}

    @Test
    void testCreateEstadioMal()
    {
        assertThrows(IllegalOperationException.class, ()->{
			EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);
			newEntity.setCapacidadMaxima(1);;
			estadioService.createEstadio(newEntity);
		});
    }
}
