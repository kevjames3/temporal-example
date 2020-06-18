package io.temporal.payments;

public class CreditCardFailureException extends RuntimeException {
  public CreditCardFailureException(final String message) {
    super(message);
  }
}
