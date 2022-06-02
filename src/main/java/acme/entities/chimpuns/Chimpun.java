package acme.entities.chimpuns;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.items.Item;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Chimpun extends AbstractEntity {
	
	// Serialisation identifier

	protected static final long	serialVersionUID = 1L;
	
	// Attributes
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{2}\\/[0-9]{2}\\/[0-9]{2}")
	protected String code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date creationMoment;
	
	@NotBlank
	@Length(min=1, max=100)
	protected String title;
	
	@NotBlank
	@Length(min=1, max=255)
	protected String description;
	
	//Period ----
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date endingTime;
	// ----
	
	@NotNull
	protected Money budget;
	
	@URL
	protected String optionalLink;
	
	// Relations -------------------------------------------------------------
	
	@Valid
	@NotNull
	@OneToOne(optional = false)
	protected Item item;
	

}
