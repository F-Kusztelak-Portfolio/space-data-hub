package com.fkusztel.space.data.hub.spacedatahub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Filip.Kusztelak
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "This Mission does not exist in the system.")
public class MissionNotFoundException extends Exception {
}
