package io.temporal.samples;

import io.temporal.payments.CreditCardInstance;
import io.temporal.payments.CreditCardService;

public class CreditCardCaptureActivityImpl implements CreditCardCaptureActivity {
  @Override
  public void capture(final String creditCardId) {
    final CreditCardInstance creditCard = CreditCardService.getPaymentInstance(creditCardId);
    if (!creditCard.captureStarted()) {
      creditCard.startCapturing();
    }
    if (!creditCard.isCaptured())
      throw new RuntimeException("Capture has not completed for id: " + creditCard.id());
  }
}
