package com.flexicore.watchdog.health;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.HealthCheckPlugin;
import com.flexicore.watchdog.config.Config;
import com.flexicore.watchdog.service.WatchDogService;
import org.pf4j.Extension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Map;

@PluginInfo(version = 1)
@Extension
@Component
public class WatchDogHealth implements HealthCheckPlugin {

	@Override
	public Health health() {
		Map<String, Long> services = WatchDogService.getServices();
		Health.Builder responseBuilder = new Health.Builder();
		boolean up = services.values().stream().noneMatch(f -> System.currentTimeMillis() - f > Config.getDefaultFailThreshold());
		for (Map.Entry<String, Long> stringLongEntry : services.entrySet()) {
			responseBuilder.withDetail(stringLongEntry.getKey(),
					stringLongEntry.getValue());
		}
		return responseBuilder.status(up? Status.UP:Status.DOWN).build();
	}
}
