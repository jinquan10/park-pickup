package integrationtest;

import org.junit.After;
import org.junit.Test;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.FailedReason;
import org.parkpickup.api.exception.FailedRequest;
import org.parkpickup.api.exception.UserException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.parkpickup.api.exception.FailedReason.VALIDATION;

public class ExceptionsIntegrationTest extends BaseIntegrationTest {
	@MockBean
	private JdbcTemplate mockJdbcTemplate;

	@After
	public void after() {
		verifyNoMoreInteractions(this.mockJdbcTemplate);
	}

	@Test
	public void testUserException() {
		doThrow(new UserException(new FailedRequest(VALIDATION)))
				.when(this.mockJdbcTemplate).query(anyString(), any(RowMapper.class));

		boolean hasCaughtException = false;
		try {
			client.getParks(1.d, 1.d, 1, null);
		} catch (UserException e) {
			hasCaughtException = true;
		} catch (ApplicationException e) {
		} finally {
			assertTrue(hasCaughtException);
		}
	}

	@Test
	public void testApplicationException() {
		doThrow(new RuntimeException())
				.when(this.mockJdbcTemplate).query(anyString(), any(RowMapper.class));

		boolean hasCaughtException = false;
		try {
			client.getParks(1.d, 1.d, 1, null);
		} catch (UserException e) {
		} catch (ApplicationException e) {
			hasCaughtException = true;
		} finally {
			assertTrue(hasCaughtException);
		}
	}
}
