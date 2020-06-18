package io.temporal.samples;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreditCardCaptureActivity {
  void capture(String creditCardId);
}
