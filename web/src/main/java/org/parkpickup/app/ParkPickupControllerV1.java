package org.parkpickup.app;

import org.parkpickup.db.ParkPickupDao;
import org.parkpickup.http.Location;
import org.parkpickup.http.ParkPickupV1;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Component
@Controller
public class ParkPickupControllerV1 implements ParkPickupV1 {
    @Inject
    private ParkPickupDao parkPickupDao;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = PUT, path = updateLocationPath)
    public void updateLocation(@PathVariable String deviceId, @RequestBody @Valid Location location) {
        parkPickupDao.updateLocation(deviceId, location.lat, location.lng);
    }
}
