package com.flexicore.watchdog.rest;

import com.flexicore.annotations.OperationsInside;
import com.flexicore.annotations.UnProtectedREST;
import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.RestServicePlugin;
import com.flexicore.watchdog.request.WatchDogKeepAlive;
import com.flexicore.watchdog.service.WatchDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.*;
import org.pf4j.Extension;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@PluginInfo(version = 1)
@OperationsInside
@UnProtectedREST
@Path("plugins/WatchDog")
@Tag(name = "WatchDog")
@Extension
@Component
public class WatchDogRESTService implements RestServicePlugin {

	@PluginInfo(version = 1)
	@Autowired
	private WatchDogService service;

	@POST
	@Produces("application/json")
	@Operation(summary = "serviceKeepAlive", description = "Service Keep Alive")
	@Path("serviceKeepAlive")
	public void serviceKeepAlive(WatchDogKeepAlive watchDogKeepAlive) {
		service.validate(watchDogKeepAlive);
		service.serviceKeepAlive(watchDogKeepAlive);
	}

	@GET
	@Path("/failOnServiceNotAlive/{serviceUnique}/{failThreshold}")
	@Operation(summary = "failOnServiceNotAlive", description = "Creates Customer")
	public boolean failOnServiceNotAlive(
			@PathParam("serviceUnique") String serviceUnique,
			@PathParam("failThreshold") long failThreshold) {

		return service.failOnServiceNotAlive(serviceUnique, failThreshold);
	}

}