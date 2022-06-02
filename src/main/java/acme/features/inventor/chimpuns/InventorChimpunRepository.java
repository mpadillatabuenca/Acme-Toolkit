/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.chimpuns;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpuns.Chimpun;
import acme.entities.items.Item;
import acme.entities.system_configurations.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorChimpunRepository extends AbstractRepository {

	@Query("select c from Chimpun c where c.id = :id")
	Chimpun findChimpunById(int id);
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	
	@Query("select c from Chimpun c where c.item.inventor.id = :inventorId")
	Collection<Chimpun> findAllChimpunsFromInventor(int inventorId);
	
	@Query("select i from Item i where i not in (select c.item from Chimpun c where c.item.inventor.id = :inventorId) and i.inventor.id = :inventorId and i.itemType = acme.entities.items.ItemType.TOOL")
	Collection<Item> findAllToolsFromInventorWithoutChimpun(int inventorId);
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);

	@Query("select sc from SystemConfiguration sc")
	Collection<SystemConfiguration> findAllConfigurations();
}
