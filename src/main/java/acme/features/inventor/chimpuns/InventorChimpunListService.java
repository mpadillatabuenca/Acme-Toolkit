/*
 * AuthenticatedAnnouncementListService.java
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
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorChimpunListService implements AbstractListService<Inventor, Chimpun> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpunRepository	repository;

	// AbstractListService<Administrator, Item> interface --------------


	@Override
	public boolean authorise(final Request<Chimpun> request) {
		assert request != null;

		return true;

	}

	@Override
	public Collection<Chimpun> findMany(final Request<Chimpun> request) {
		assert request != null;
		final Collection<Chimpun> result;
		final Principal principal;
 
		principal = request.getPrincipal();
		result = this.repository.findAllChimpunsFromInventor(principal.getActiveRoleId());
		return result;
		
	}

	@Override
	public void unbind(final Request<Chimpun> request, final Chimpun entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title", "description", "startTime", "endingTime", "budget", "optionalLink");
	}

}
