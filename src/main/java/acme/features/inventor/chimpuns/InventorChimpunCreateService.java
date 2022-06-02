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

package acme.features.inventor.chimpuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpuns.Chimpun;
import acme.entities.system_configurations.SystemConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorChimpunCreateService implements AbstractCreateService<Inventor, Chimpun> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorChimpunRepository repository;
	

	// AbstractCreateService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Chimpun> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Chimpun> request, final Chimpun entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final List<SystemConfiguration> configurationColl = new ArrayList<>(this.repository.findAllConfigurations());
		final String acceptedCurrencies = configurationColl.get(0).getAcceptedCurrencies();
		final List<String> currencies = Arrays.asList(acceptedCurrencies.split(";"));
		
		if(!errors.hasErrors("budget")) {
			errors.state(request, !(!currencies.contains(entity.getBudget().getCurrency()) || entity.getBudget().getCurrency() == null ||
				entity.getBudget().getCurrency().length() == 0),
				"budget", "inventor.chimpun.form.error.incorrectCurrency");
			errors.state(request, !(entity.getBudget().getAmount() <= 0.0 || entity.getBudget().getAmount() == null),
				"budget", "inventor.chimpun.form.error.incorrectQuantity");
		}
		
		if(!errors.hasErrors("startTime")) {
			final Date creationTime = entity.getCreationMoment();
			final Calendar calCreation = Calendar.getInstance();
			calCreation.setTime(creationTime);
			calCreation.add(Calendar.MONTH, 1);
			final Date creationDateAfterMonth = calCreation.getTime();
			final Date startTime = entity.getStartTime();
			
			errors.state(request, (startTime.after(creationDateAfterMonth)),
				"startTime", "inventor.chimpun.form.error.creationDateAfterMonth");
		}
		
		if(!errors.hasErrors("endingTime")) {
			if(entity.getStartTime() != null) {
				final Date startTime = entity.getStartTime();
				final Calendar calStart = Calendar.getInstance();
				calStart.setTime(startTime);
				calStart.add(Calendar.WEEK_OF_YEAR, 1);
				
				final Date startDateAfterMonth = calStart.getTime();
				final Date endingTime = entity.getEndingTime();
				
				errors.state(request, (endingTime.after(startDateAfterMonth)),
					"endingTime", "inventor.chimpun.form.error.startDateAfterMonth");
			}		
		}
		

	}

	@Override
	public void bind(final Request<Chimpun> request, final Chimpun entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Calendar calCreation = Calendar.getInstance();
		calCreation.add(Calendar.MILLISECOND, -100);
		entity.setCreationMoment(calCreation.getTime());
		
		final int año = calCreation.get(Calendar.YEAR)-2000;
		final int mes = calCreation.get(Calendar.MONTH);
		final int dia = calCreation.get(Calendar.DAY_OF_MONTH);
		entity.setCode(año+"/"+String.format("%02d",mes)+"/"+String.format("%02d",dia));
		
		entity.setItem(this.repository.findItemById(request.getModel().getInteger("itemId")));

		request.bind(entity, errors, "title", "description", "startTime", "endingTime", "budget", "optionalLink", "itemId");
	}

	@Override
	public void unbind(final Request<Chimpun> request, final Chimpun entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		if(this.repository.findAllToolsFromInventorWithoutChimpun(request.getPrincipal().getActiveRoleId()).size()==0) {
			model.setAttribute("noToolsError", true);
		}else {
			model.setAttribute("tools", this.repository.findAllToolsFromInventorWithoutChimpun(request.getPrincipal().getActiveRoleId()));
		}
		request.unbind(entity, model, "code", "creationMoment", "title", "description", "startTime", "endingTime", "budget", "optionalLink", "item");
	}

	@Override
	public Chimpun instantiate(final Request<Chimpun> request) {
		assert request != null;

		return new Chimpun();
	}

	@Override
	public void create(final Request<Chimpun> request, final Chimpun entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
