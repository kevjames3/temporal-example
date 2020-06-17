package io.temporal.samples;

import io.temporal.payments.CreditCardHandler;

public class CreditCardCaptureActivityImpl implements CreditCardCaptureActivity {
  @Override
  public void capture(CreditCardHandler handler) {
    if (!handler.captureStarted()) {
      handler.startCapturing();
    }
    if (!handler.isCaptured())
      throw new RuntimeException("Capture has not completed for id: " + handler.id());
  }
}
