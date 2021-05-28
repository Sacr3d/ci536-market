package karna.api.data.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 496024897701634318L;

	private @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id", unique = true, nullable = false) Long id;
	
	private String owner;
	private String userAdress;
	private String userContactNumber;
	private Date userDOB;
	private String userEmail;
	private String userName;

}