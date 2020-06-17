package io.temporal.payments;

public class CreditCardHandlerFactory {
  public static CreditCardHandler initPayment(final String uniqueId) {
    return new FakeCreditCardHandler(uniqueId);
  }
}
