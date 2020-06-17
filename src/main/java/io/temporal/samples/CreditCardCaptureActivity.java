package io.temporal.samples;

import io.temporal.activity.ActivityInterface;
import io.temporal.payments.CreditCardHandler;

@ActivityInterface
public interface CreditCardCaptureActivity {
  void capture(CreditCardHandler handler);
}
