package com.flexicore.watchdog.health;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.HealthCheckPlugin;
import com.flexicore.watchdog.config.Config;
import com.flexicore.watchdog.service.WatchDogService;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@Health
@ApplicationScoped
@PluginInfo(version = 1, autoInstansiate = true)
public class WatchDogHealth implements HealthCheckPlugin {


    @Override
    public HealthCheckResponse call() {
        Map<String,Long> services=WatchDogService.getServices();
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("WatchDog");
        boolean up=services.values().stream().noneMatch(f->System.currentTimeMillis()-f > Config.getDefaultFailThreshold());
        for (Map.Entry<String, Long> stringLongEntry : services.entrySet()) {
            responseBuilder.withData(stringLongEntry.getKey(),stringLongEntry.getValue());
        }
        return responseBuilder.state(up).build();
    }
}
