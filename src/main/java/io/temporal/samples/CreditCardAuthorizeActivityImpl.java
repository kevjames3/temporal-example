package io.temporal.samples;

import io.temporal.payments.CreditCardHandler;

public class CreditCardAuthorizeActivityImpl implements CreditCardAuthorizeActivity {
  @Override
  public void authorize(CreditCardHandler handler) {
    if (!handler.authorizationStarted()) {
      handler.startAuthorizing();
    }
    if (!handler.isAuthorized())
      throw new RuntimeException("Authorization has not completed for id: " + handler.id());
  }
}
