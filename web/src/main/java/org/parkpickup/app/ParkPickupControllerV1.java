package org.parkpickup.app;

import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.ParkPickupV1;
import org.parkpickup.db.ParkPickupDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Component
@Controller
public class ParkPickupControllerV1 implements ParkPickupV1 {
    @Inject
    private ParkPickupDao parkPickupDao;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = PUT, path = updateLocationPath, consumes = "application/json")
    public void updateLocation(@PathVariable String deviceId, @RequestBody @Valid Location location) {
        parkPickupDao.updateLocation(deviceId, location.lat, location.lng);
    }

    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = GET, path = getPopulatedParksPath, produces = "application/json")
    public Collection<Park> getPopulatedParks(@RequestParam double lat, @RequestParam double lng, @RequestParam int radiusMeters) {
        return parkPickupDao.getPopulatedParks(lat, lng, radiusMeters);
    }
}
