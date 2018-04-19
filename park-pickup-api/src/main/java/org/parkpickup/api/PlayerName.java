package org.parkpickup.api;

import org.parkpickup.api.exception.FailedRequest;
import org.parkpickup.api.exception.InvalidFieldDetails;
import org.parkpickup.api.exception.UserException;

import java.util.Arrays;

import static org.parkpickup.api.FieldKeys.PLAYER_NAME;
import static org.parkpickup.api.exception.FailedReason.VALIDATION;

public class PlayerName {
	public static final int minLength = 3;
	public static final int maxLength = 32;
	public static final String errorMessage = "Player name needs to have more than 3 letters, but less than 32.";
	public final String playerName;

	public PlayerName() {
		this.playerName = null;
	}

	public PlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void validate() throws UserException {
		if (this.playerName != null) {
			if (this.playerName.length() < 3 || this.playerName.length() > 32) {
				InvalidFieldDetails invalidFieldDetails = new InvalidFieldDetails(PLAYER_NAME.key, errorMessage);
				throw new UserException(
						new FailedRequest(
								VALIDATION,
								Arrays.asList(new InvalidFieldDetails[]{invalidFieldDetails})));
			}
		}
	}
}
