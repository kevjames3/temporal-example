package io.temporal.payments;

public interface CreditCardHandler {
  boolean hasFailed();

  boolean isStillProcessing();

  boolean isAuthorized();

  boolean isCaptured();

  void startAuthorizing();

  void startCapturing();

  boolean captureStarted();

  boolean authorizationStarted();

  String id();

  String toString();
}
