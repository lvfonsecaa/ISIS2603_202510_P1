package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Prize - Author
 *
 * @author ISIS2603
 */
@DataJpaTest
@Transactional
@Import(ConciertoEstadioService.class)

public class ConciertoEstadioServiceTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

	@Autowired
	private ConciertoEstadioService conciertoEstadioService;

	@Autowired
	private TestEntityManager entityManager;

	private List<ConciertoEntity> conciertoList = new ArrayList<>();
	private List<EstadioEntity> estadiosList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity ").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
			

			entityManager.persist(org);
			entityManager.persist(prizes);
			prizesList.add(prizes);
		}
		for (int i = 0; i < 3; i++) {
			AuthorEntity entity = factory.manufacturePojo(AuthorEntity.class);
			entityManager.persist(entity);
			authorsList.add(entity);
			if (i == 0) {
				prizesList.get(i).setAuthor(entity);
			}
		}
	}

}
