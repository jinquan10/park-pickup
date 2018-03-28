package endtoendtest;

import org.junit.runner.RunWith;
import org.parkpickup.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class HappyEndToEndTest {

}

