package net.minedof.web.utils;

import java.io.Serializable;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@SuppressWarnings("serial")
@ApplicationScoped
public class UUIDFactory implements Serializable {

	@Produces
	public UUID getUUID() {
		return UUID.randomUUID();
	}

}
