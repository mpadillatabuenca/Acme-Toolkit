/*
 * AuthenticatedAnnouncementController.java
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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.chimpuns.Chimpun;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
@RequestMapping("/inventor/chimpun/")
public class InventorChimpunController extends AbstractController<Inventor, Chimpun> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorChimpunListService	listService;

	@Autowired
	protected InventorChimpunShowService	showService;
	
	@Autowired
	protected InventorChimpunDeleteService	deleteService;
	
	@Autowired
	protected InventorChimpunCreateService	createService;

	@Autowired
	protected InventorChimpunUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		
		super.addCommand("delete", this.deleteService);
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
	}

}
