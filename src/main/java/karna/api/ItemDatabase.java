package karna.api;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import karna.api.data.item.Item;
import karna.api.data.item.ItemRepository;
import karna.api.data.item.ItemStatus;

@Configuration
public class ItemDatabase {

	private static final Logger log = LoggerFactory.getLogger(ItemDatabase.class);

	private static final String INFO_PRELOADING = "Preloading {}";

	private static final Date USER_DOB = new Date();

	private static final String USER1 = "Matt";
	private static final String USER2 = "Tom";
	private static final String USER3 = "Aemon";
	private static final String USER4 = "James";

	private static final String CATEGORY1 = "TECH";
	private static final String CATEGORY2 = "GAMING";
	private static final String CATEGORY3 = "CLOTHING";
	private static final String CATEGORY4 = "SPORT";
	private static final String CATEGORY5 = "LITERATURE";
	private static final String CATEGORY6 = "FILM_TV";

	@Bean
	CommandLineRunner initItemDatabase(@Autowired ItemRepository<Item> repository) {
		return args -> {

			log.info(INFO_PRELOADING, repository.save(Item.builder() //
					.itemName("Controller") //
					.itemCategory(Arrays.asList(CATEGORY1, CATEGORY2)) //
					.itemPrice(32) //
					.itemCreatedDate(USER_DOB) //
					.itemTrend(generateRandomTrend()) //
					.owner(USER1) //
					.itemStatus(ItemStatus.LISTED) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Item.builder() //
					.itemName("Shirt") //
					.itemCategory(Arrays.asList(CATEGORY3)) //
					.itemPrice(2) //
					.itemCreatedDate(USER_DOB) //
					.itemTrend(generateRandomTrend()) //
					.owner(USER1) //
					.itemStatus(ItemStatus.SOLD) //
					.build()));
			
			log.info(INFO_PRELOADING, repository.save(Item.builder() //
					.itemName("Book") //
					.itemCategory(Arrays.asList(CATEGORY5)) //
					.itemPrice(1) //
					.itemCreatedDate(USER_DOB) //
					.itemTrend(generateRandomTrend()) //
					.owner(USER1) //
					.itemStatus(ItemStatus.SOLD) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Item.builder() //
					.itemName("TV") //
					.itemCategory(Arrays.asList(CATEGORY1)) //
					.itemPrice(102) //
					.itemCreatedDate(USER_DOB) //
					.itemTrend(generateRandomTrend()) //
					.owner(USER2) //
					.itemStatus(ItemStatus.LISTED) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Item.builder() //
					.itemName("Tennis Shoes") //
					.itemCategory(Arrays.asList(CATEGORY3, CATEGORY4)) //
					.itemPrice(12) //
					.itemCreatedDate(USER_DOB) //
					.itemTrend(generateRandomTrend()) //
					.owner(USER3) //
					.itemStatus(ItemStatus.LISTED) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Item.builder() //
					.itemName("Basket Ball") //
					.itemCategory(Arrays.asList(CATEGORY4)) //
					.itemPrice(1) //
					.itemCreatedDate(USER_DOB) //
					.itemTrend(generateRandomTrend()) //
					.owner(USER4) //
					.itemStatus(ItemStatus.LISTED) //
					.build()));

		};
	}

	private Integer generateRandomTrend() {

		return ThreadLocalRandom.current().nextInt(0, 2 + 1);

	}
}
