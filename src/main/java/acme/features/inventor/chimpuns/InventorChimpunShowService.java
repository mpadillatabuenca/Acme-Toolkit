/*
 * AuthenticatedAnnouncementShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpuns.Chimpun;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorChimpunShowService implements AbstractShowService<Inventor, Chimpun> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpunRepository repository;

	// AbstractShowService<Administrator, Announcement> interface --------------


	@Override
	public boolean authorise(final Request<Chimpun> request) {
		assert request != null;
		final int chimpunId = request.getModel().getInteger("id");
		if(this.repository.findChimpunById(chimpunId) != null) {
			final Chimpun chimpun = this.repository.findChimpunById(chimpunId);
			final Inventor inventor = chimpun.getItem().getInventor();
			
			return request.isPrincipal(inventor);
		}
		return false;
		
	}

	@Override
	public void unbind(final Request<Chimpun> request, final Chimpun entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		final Collection<Item> tools = this.repository.findAllToolsFromInventorWithoutChimpun(request.getPrincipal().getActiveRoleId());
		tools.add(entity.getItem());
		
		
		model.setAttribute("itemId", entity.getItem().getId());
		if(this.repository.findAllToolsFromInventorWithoutChimpun(request.getPrincipal().getActiveRoleId()).size()==0) {
			model.setAttribute("noToolsError", true);
		}else {
			model.setAttribute("tools", tools);
		}
		request.unbind(entity, model, "code", "creationMoment", "title", "description", "startTime", "endingTime", "budget", "optionalLink");
	}

	@Override
	public Chimpun findOne(final Request<Chimpun> request) {
		assert request != null;

		Chimpun result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findChimpunById(id);

		return result;
	}

}
