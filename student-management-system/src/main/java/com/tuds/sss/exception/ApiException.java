package com.tuds.sss.exception;

import java.time.ZonedDateTime;

public record ApiException(String message,  ZonedDateTime timestamp) {
}
