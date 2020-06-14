package com.flexicore.watchdog.config;

import com.flexicore.annotations.InjectProperties;
import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.InitPlugin;

import javax.inject.Inject;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@PluginInfo(version = 1, autoInstansiate = true, order = 90)
public class Config implements InitPlugin {

    private static AtomicBoolean init = new AtomicBoolean(false);

    @Inject
    @InjectProperties
    private Properties properties;

    private static long defaultFailThreshold=60000;
    @Override
    public void init() {
        if (init.compareAndSet(false, true)) {
            defaultFailThreshold=Long.parseLong(properties.getProperty("defaultFailThreshold",defaultFailThreshold+""));
        }
    }

    public static long getDefaultFailThreshold() {
        return defaultFailThreshold;
    }
}
