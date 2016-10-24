package main.java.parkpickup.bo.impl;

import main.java.parkpickup.bo.ParkApi;
import main.java.parkpickup.dao.LocationDaoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkApiImpl implements ParkApi {
    @Autowired
    private LocationDaoApi locationDaoApi;

    @Override
    public void updateLocation(double lat, double lng) {
        org.slf4j.LoggerFactory.getLogger(ParkApiImpl.class).info("updateLocation");
    }
}
