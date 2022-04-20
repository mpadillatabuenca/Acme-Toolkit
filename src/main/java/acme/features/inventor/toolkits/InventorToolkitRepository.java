package acme.features.inventor.toolkits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.toolkit.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorToolkitRepository extends AbstractRepository {

	@Query("select t from Toolkit t where t.id = :id")
	Toolkit findToolkitById(int id);
	
	@Query("select distinct q.toolkit from Quantity q where q.item.inventor.id = :inventorId")
	Collection<Toolkit> findAllTookitsByInventorId(int inventorId);
	
	@Query("select sum(q.amount * i.retailPrice.amount) as suma, i.retailPrice.currency as currency from Quantity q, Item i where q.toolkit.id = :id and q.item = i.id group by i.retailPrice.currency")
	Collection<Object[]> retailPriceByCurrency(Integer id);
}
