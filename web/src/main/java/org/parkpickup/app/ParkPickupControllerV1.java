package org.parkpickup.app;

import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.ParkPickupV1;
import org.parkpickup.db.ParkPickupDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
public class ParkPickupControllerV1 implements ParkPickupV1 {
    @Inject
    private ParkPickupDao parkPickupDao;

    @Override
    @ResponseStatus(OK)
    @RequestMapping(method = PUT, path = updateLocationPath, consumes = APPLICATION_JSON_VALUE)
    public void updateLocation(@PathVariable String deviceId, @RequestBody @Valid Location location) {
        parkPickupDao.updateLocation(deviceId, location.lat, location.lng);
    }

    @Override
    @ResponseBody
    @ResponseStatus(OK)
    @RequestMapping(method = GET, path = getParksPath, produces = APPLICATION_JSON_VALUE)
    public Collection<Park> getParks(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam int radiusMeters,
            @RequestParam Set<ActivityEnum> activities) {
        return parkPickupDao.getParks(lat, lng, radiusMeters, activities);
    }

    @Override
    @ResponseStatus(OK)
    @RequestMapping(method = PUT, path = setActivitiesPath, consumes = APPLICATION_JSON_VALUE)
    public void setActivities(@PathVariable String deviceId, @RequestBody Set<ActivityEnum> activities) {
        if (deviceId == null || activities == null) {
            throw new RuntimeException();
        }

        this.parkPickupDao.setActivities(deviceId, activities);
    }
}
