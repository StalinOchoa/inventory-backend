package com.company.inventory.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="product")
//objetos transportable con serializable
public class Product  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7461389651533509262L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//aqui sera actugenerando segun como se aumente los registro uno por uno
	private Long id;
	
	private String name;
	
	private int price;
	
	private int account;
	
	//relacion entre tablas y //objeto de la categoria
	@ManyToOne(fetch= FetchType.LAZY)
	@JsonIgnoreProperties ({"hibernateLazyInitializer","handler"})
	private Category category;
	
	//propiedad foto-- se almacena en bytes de 64 lo convierte en la bdd y lo almacena par adevolver en el from con objeto de tipo byte
	@Column ( name="picture", length= 1000)
	private byte[] picture;
	
	

}
