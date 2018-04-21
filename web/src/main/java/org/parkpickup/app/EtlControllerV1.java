package org.parkpickup.app;

import org.parkpickup.etl.PbfToDbEtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class EtlControllerV1 {
    public static final String loadPbfPredefinedPath = "internal/v1/load-pbf-predefined";

    @Autowired
    private PbfToDbEtl pbfToDbEtl;

    @Value("${predefined.pbf.path}")
    private String predefinedPbfPath;

    @ResponseStatus(OK)
    @RequestMapping(method = POST, path = loadPbfPredefinedPath, consumes = APPLICATION_JSON_VALUE)
    public void loadPbfPredefined() {
        throw new RuntimeException();
//        this.pbfToDbEtl.loadSinglePbf(this.predefinedPbfPath);
    }
}