package com.fkusztel.space.data.hub.spacedatahub.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Filip.Kusztelak
 */
public class Constants {

  private Constants() {}

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ftp {

    public static final String FTP_URL = "ftp://username:password@hostname/product";
    public static final String FTP_PURCHASE = "PURCHASE PRODUCT";
  }
}
