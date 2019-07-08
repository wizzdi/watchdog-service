package com.flexicore.watchdog.request;

public class WatchDogKeepAlive {

    private String serviceUniqueId;


    public String getServiceUniqueId() {
        return serviceUniqueId;
    }

    public <T extends WatchDogKeepAlive> T setServiceUniqueId(String serviceUniqueId) {
        this.serviceUniqueId = serviceUniqueId;
        return (T) this;
    }
}
