package io.temporal.payments;

public interface CreditCardInstance {
  boolean hasFailed();

  boolean isAuthorized();

  boolean isCaptured();

  void startAuthorizing();

  void startCapturing();

  boolean captureStarted();

  boolean authorizationStarted();

  String id();
}
