package karna.api.data.item;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository<T> extends JpaRepository<Item, Long> {

	public static final String KARNA_API_DATA_ITEM_ITEM_STATUS_LISTED = "karna.api.data.item.ItemStatus.LISTED";

	List<T> findAllByOwner(String owner);

	@Query("SELECT i FROM Item i WHERE i.itemStatus=" + KARNA_API_DATA_ITEM_ITEM_STATUS_LISTED
			+ " ORDER BY i.itemCreatedDate DESC")
	List<T> findAllRecentN(Pageable pageable);

	@Query("SELECT i FROM Item i WHERE i.itemStatus=" + KARNA_API_DATA_ITEM_ITEM_STATUS_LISTED
			+ " ORDER BY i.itemTrend DESC")
	List<T> findAllTrendingN(Pageable pageable);

	@Query("SELECT i FROM Item i WHERE i.itemStatus=" + KARNA_API_DATA_ITEM_ITEM_STATUS_LISTED
			+ " ORDER BY i.itemTrend DESC")
	List<T> findAllTrending();

	List<T> findAllByItemCategoryAndItemStatus(String cat, ItemStatus listed);

	List<T> findAllByOwnerAndItemStatus(String username, ItemStatus itemStatus);

}