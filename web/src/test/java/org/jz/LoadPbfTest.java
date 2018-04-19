package org.jz;

import org.junit.Ignore;
import org.junit.Test;
import org.parkpickup.etl.PbfToDbEtl;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadPbfTest extends BaseTest {
    @Autowired
    private PbfToDbEtl pbfToDbEtl;

    @Ignore
    @Test
    public void loadSinglePbf() {
        this.pbfToDbEtl.loadSinglePbf("C:\\Users\\jzjz\\Downloads\\washington-latest.osm.pbf");
    }
}
