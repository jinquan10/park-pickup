package endtoendtest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parkpickup.Application;
import org.parkpickup.etl.PbfToDbEtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class LoadPbfTest {

    static {
        System.setProperty("env", "test");
    }

    @Autowired
    private PbfToDbEtl pbfToDbEtl;

    @Ignore
    @Test
    public void loadSinglePbf() {
        this.pbfToDbEtl.loadSinglePbf("C:\\Users\\jzjz\\Downloads\\washington-latest.osm.pbf");
    }
}
