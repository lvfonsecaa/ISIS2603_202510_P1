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

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest 
{
    @Autowired
	private ConciertoService conciertoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<ConciertoEntity> conciertoList = new ArrayList<>();
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
			ConciertoEntity conciertoEntity = factory.manufacturePojo(ConciertoEntity.class);
			entityManager.persist(conciertoEntity);
			conciertoList.add(conciertoEntity);
		}
	}

    @Test
	void testCreateConcierto() throws IllegalOperationException 
    {
		ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);

        Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date()); 
			calendar.add(Calendar.DATE, -3);

		newEntity.setFecha(calendar.getTime());
        newEntity.setCapacidadAforo(11);
        newEntity.setPresupuesto(1001);
		ConciertoEntity result = conciertoService.createConcierto(newEntity) ;
		assertNotNull(result);

		ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getCapacidadAforo(), entity.getCapacidadAforo());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getFecha(), entity.getFecha());
        assertEquals(newEntity.getPresupuesto(), entity.getPresupuesto());
	}

    @Test
    void testCreateConciertoMal()
    {
        assertThrows(IllegalOperationException.class, ()->{
			ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
			newEntity.setPresupuesto(900);;
			conciertoService.createConcierto(newEntity);
		});
    }
}
