package io.temporal.samples;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreditCardAuthorizeActivity {
  void authorize(String creditCardId);
}
