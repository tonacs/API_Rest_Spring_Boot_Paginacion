package com.tona.springboot.app.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users") //Crea una tabla con el nombre users
@Getter @Setter
public class User implements Serializable {
		
		/**
	 * 
	 */
	private static final long serialVersionUID = -4858943819002829169L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(length = 50)
		private String name;
		
		
		private String surname;
		
		@Column(name="mail", nullable = false, length = 50,unique = true)
		private String email;
		
	
		private Boolean enabled;
}
