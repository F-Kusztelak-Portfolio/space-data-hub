package com.fkusztel.space.data.hub.spacedatahub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "This Product does not exist in the system.")
public class ProductNotFoundException extends Exception {
}
