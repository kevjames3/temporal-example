package io.temporal.samples;

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
      // insert logic to stop trying, not realeased yet
      // (https://github.com/temporalio/temporal-java-sdk/blob/master/src/main/java/io/temporal/failure/ApplicationFailure.java)
    }

    if (!creditCard.isAuthorized())
      throw new RuntimeException("Authorization has not completed for id: " + creditCard.id());
  }
}
