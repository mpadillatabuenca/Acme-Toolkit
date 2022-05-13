/*
 * EmployerJobCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.items;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.items.ItemType;
import acme.entities.system_configurations.SystemConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.util.SpamFilterService;

@Service
public class InventorComponentCreateService implements AbstractCreateService<Inventor, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemRepository repository;
	
	@Autowired
	protected SpamFilterService spamFilterService;

	// AbstractCreateService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final List<SystemConfiguration> configurationColl = new ArrayList<>(this.repository.findAllConfigurations());
		final String acceptedCurrencies = configurationColl.get(0).getAcceptedCurrencies();

		if (!errors.hasErrors("code")) {
			Item existing;

			existing = this.repository.findItemByCode(entity.getCode());
			errors.state(request, existing == null, "code", "inventor.item.form.error.duplicated");
		}
		
		if(!errors.hasErrors("retailPrice")) {
			errors.state(request, !(!acceptedCurrencies.contains(entity.getRetailPrice().getCurrency()) || entity.getRetailPrice().getCurrency() == null ||
				entity.getRetailPrice().getCurrency().length() == 0),
				"retailPrice", "inventor.item.form.error.incorrectCurrency");
			errors.state(request, !(entity.getRetailPrice().getAmount() < 0.0 || entity.getRetailPrice().getAmount() == null),
				"retailPrice", "inventor.item.form.error.incorrectQuantity");
		}
		
		if(!errors.hasErrors("description")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getDescription()), "description", "inventor.item.form.error.spam");
		}
		
		if(!errors.hasErrors("name")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getName()), "name", "inventor.item.form.error.spam");
		}

	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code");
		request.bind(entity, errors, "name", "code", "technology", "description", "retailPrice", "link");
		
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "technology", "description", "retailPrice", "link", "itemType", "isPublished");
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		assert request != null;

		Item result;
		final Inventor inventor;

		inventor = this.repository.findOneInventorById(request.getPrincipal().getActiveRoleId());
		result = new Item();
		result.setPublished(false);
		result.setInventor(inventor);
		result.setItemType(ItemType.COMPONENT);

		return result;
	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
