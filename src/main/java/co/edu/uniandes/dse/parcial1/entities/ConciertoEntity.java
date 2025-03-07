package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.Date;

import co.edu.uniandes.dse.parcial1.podam.DateStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Temporal;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;

    private Integer capacidadAforo;

    private Integer presupuesto;
    
    @Temporal(TemporalType.DATE)
	private Date fecha;

    @PodamExclude
	@ManyToOne
	private EstadioEntity estadio;
}
