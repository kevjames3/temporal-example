package io.temporal.samples;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class ProcessCreditCardWorkflowImpl implements ProcessCreditCardWorkflow {
  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setScheduleToCloseTimeout(Duration.ofMinutes(3))
          .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(10).build())
          .build();

  private final CreditCardAuthorizeActivity authorizeActivity =
      Workflow.newActivityStub(CreditCardAuthorizeActivity.class, options);

  private final CreditCardCaptureActivity captureActivity =
      Workflow.newActivityStub(CreditCardCaptureActivity.class, options);

  @Override
  public void processCreditCard(final String creditCardId) {
    authorizeActivity.authorize(creditCardId);
    captureActivity.capture(creditCardId);
  }
}
