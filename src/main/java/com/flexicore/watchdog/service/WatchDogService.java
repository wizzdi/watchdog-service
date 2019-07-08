package com.flexicore.watchdog.service;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.ServicePlugin;
import com.flexicore.watchdog.request.WatchDogKeepAlive;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ServiceUnavailableException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@PluginInfo(version = 1)
public class WatchDogService implements ServicePlugin {


    private static Map<String,Long> services=new ConcurrentHashMap<>();


    public void serviceKeepAlive(WatchDogKeepAlive watchDogKeepAlive) {
        services.put(watchDogKeepAlive.getServiceUniqueId(),System.currentTimeMillis());

    }

    public void validate(WatchDogKeepAlive watchDogKeepAlive) {
        if(watchDogKeepAlive.getServiceUniqueId()==null || watchDogKeepAlive.getServiceUniqueId().isEmpty()){
            throw new BadRequestException("Watch dog keep alive must provide a non empty service unique id");
        }
    }

    public boolean failOnServiceNotAlive(String serviceUnique, long failThreshold) {
        Long lastKeepAlive=services.get(serviceUnique);
        if(lastKeepAlive==null || System.currentTimeMillis()-lastKeepAlive > failThreshold){
            throw new ServiceUnavailableException("Service "+serviceUnique +" last keep alive was at "+lastKeepAlive +" current time is "+System.currentTimeMillis());
        }
        return true;
    }
}
