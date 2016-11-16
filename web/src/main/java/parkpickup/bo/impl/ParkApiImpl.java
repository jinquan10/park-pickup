package parkpickup.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import parkpickup.dao.LocationDaoApi;

@Component
public class ParkApiImpl implements ParkApi {
    @Autowired
    private LocationDaoApi locationDaoApi;

    @Override
    public void updateLocation(double lat, double lng) {
        org.slf4j.LoggerFactory.getLogger(ParkApiImpl.class).info("updateLocation");
    }
}
