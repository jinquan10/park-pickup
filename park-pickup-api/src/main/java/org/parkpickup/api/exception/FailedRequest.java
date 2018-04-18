package org.parkpickup.api.exception;

import java.util.List;

public class FailedRequest {
    public final FailedReason failedReason;
    public final List<InvalidFieldDetails> invalidFieldDetails;

    public FailedRequest(FailedReason failedReason, List<InvalidFieldDetails> invalidFieldDetails) {
        this.failedReason = failedReason;
        this.invalidFieldDetails = invalidFieldDetails;
    }

    public FailedRequest(FailedReason failedReason) {
        this.failedReason = failedReason;
        this.invalidFieldDetails = null;
    }
}
