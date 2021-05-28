package karna.api.data.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository<T> extends JpaRepository<Order, Long> {

	List<T> findAllByOrderBuyer(String username);

	List<T> findAllByOrderSeller(String username);

	Optional<T> findByItemId(Long id);

}