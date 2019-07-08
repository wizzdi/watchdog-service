package com.flexicore.watchdog.rest;

import com.flexicore.annotations.OperationsInside;
import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interceptors.DynamicResourceInjector;
import com.flexicore.interfaces.RestServicePlugin;
import com.flexicore.watchdog.request.WatchDogKeepAlive;
import com.flexicore.watchdog.service.WatchDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

@PluginInfo(version = 1)
@OperationsInside
@Interceptors({DynamicResourceInjector.class})
@Path("plugins/WatchDog")

@Tag(name = "WatchDog")
public class WatchDogRESTService implements RestServicePlugin {

    @Inject
    @PluginInfo(version = 1)
    private WatchDogService service;

    @POST
    @Produces("application/json")
    @Operation(summary = "serviceKeepAlive", description = "Service Keep Alive")
    @Path("serviceKeepAlive")
    public void serviceKeepAlive(
            WatchDogKeepAlive watchDogKeepAlive) {
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