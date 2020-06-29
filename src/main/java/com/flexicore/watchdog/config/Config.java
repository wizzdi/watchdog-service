package com.flexicore.watchdog.config;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.InitPlugin;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import com.flexicore.interfaces.ServicePlugin;
import org.pf4j.Extension;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@PluginInfo(version = 1)
@Extension
@Component
public class Config implements ServicePlugin {

	private static AtomicBoolean init = new AtomicBoolean(false);

	@Autowired
	private Environment properties;

	private static long defaultFailThreshold = 60000;
	@EventListener
	public void init(ContextRefreshedEvent e) {
		if (init.compareAndSet(false, true)) {
			defaultFailThreshold = Long.parseLong(properties.getProperty(
					"defaultFailThreshold", defaultFailThreshold + ""));
		}
	}

	public static long getDefaultFailThreshold() {
		return defaultFailThreshold;
	}
}
