package karna.api.data.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import karna.api.data.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder
@EntityScan
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2871667288197370808L;

	private @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id", unique = true, nullable = false) Long id;

	private Long itemId;
	private String orderItem;
	private String orderSeller;
	private String orderBuyer;
	private String orderBuyerAdress;
	private String userContactNumber;
	private Date orderDate;
	private OrderStatus orderStatus;
	private int orderPrice;

}