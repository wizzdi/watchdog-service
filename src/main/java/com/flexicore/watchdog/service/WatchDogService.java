package com.flexicore.watchdog.service;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.ServicePlugin;
import com.flexicore.watchdog.request.WatchDogKeepAlive;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ServiceUnavailableException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.pf4j.Extension;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@PluginInfo(version = 1)
@Extension
@Component
public class WatchDogService implements ServicePlugin {

	@Autowired
	private Logger logger;

	private static Map<String, Long> services = new ConcurrentHashMap<>();

	public void serviceKeepAlive(WatchDogKeepAlive watchDogKeepAlive) {
		services.put(watchDogKeepAlive.getServiceUniqueId(),
				System.currentTimeMillis());

	}

	public void validate(WatchDogKeepAlive watchDogKeepAlive) {
		if (watchDogKeepAlive.getServiceUniqueId() == null
				|| watchDogKeepAlive.getServiceUniqueId().isEmpty()) {
			throw new BadRequestException(
					"Watch dog keep alive must provide a non empty service unique id");
		}
	}

	public static Map<String, Long> getServices() {
		return services;
	}

	public boolean failOnServiceNotAlive(String serviceUnique,
			long failThreshold) {
		Long lastKeepAlive = services.get(serviceUnique);
		if (lastKeepAlive == null
				|| System.currentTimeMillis() - lastKeepAlive > failThreshold) {
			throw new ServiceUnavailableException("Service " + serviceUnique
					+ " last keep alive was at " + lastKeepAlive
					+ " current time is " + System.currentTimeMillis());
		} else {
			logger.info("service " + serviceUnique + " last alive at "
					+ lastKeepAlive + " ");
		}
		return true;
	}
}
