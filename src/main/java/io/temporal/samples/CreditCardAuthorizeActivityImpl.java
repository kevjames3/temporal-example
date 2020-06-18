package io.temporal.samples;

import io.temporal.payments.CreditCardFailureException;
import io.temporal.payments.CreditCardInstance;
import io.temporal.payments.CreditCardService;

public class CreditCardAuthorizeActivityImpl implements CreditCardAuthorizeActivity {
  @Override
  public void authorize(final String creditCardId) {
    final CreditCardInstance creditCard = CreditCardService.getPaymentInstance(creditCardId);
    if (!creditCard.authorizationStarted()) {
      creditCard.startAuthorizing();
    }

    if (creditCard.hasFailed()) {
      throw new CreditCardFailureException("Credit card with " + creditCard.id() + " has failed");
    }

    if (!creditCard.isAuthorized())
      throw new RuntimeException("Authorization has not completed for id: " + creditCard.id());
  }
}
