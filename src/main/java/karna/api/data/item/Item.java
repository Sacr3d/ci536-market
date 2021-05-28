package karna.api.data.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@Builder
@EntityScan
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5113551517690896010L;

	private @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id", unique = true, nullable = false) Long id;
	
	private @ElementCollection(fetch = FetchType.EAGER) @Builder.Default List<String> itemCategory = new ArrayList<>();
	private Date itemCreatedDate;
	private String itemName;
	private int itemTrend;
	private int itemPrice;
	private ItemStatus itemStatus;
	private String owner;

}