package parkpickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import parkpickup.bo.ParkApi;

@RestController
public class LocationController {
    @Autowired
    private ParkApi parkApi;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getTest() {
        parkApi.updateLocation(0.d, 0.d);
        return "ok";
    }
}
