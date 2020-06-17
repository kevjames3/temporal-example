package io.temporal.samples;

import io.temporal.payments.CreditCardHandler;

public class ProcessCreditCardWorkflowImpl implements ProcessCreditCardWorkflow {
  //  private final ActivityOptions options =
  //      ActivityOptions.newBuilder()
  //          .setScheduleToCloseTimeout(Duration.ofMinutes(3))
  //          // disable retries for example to run faster
  //          .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(5).build())
  //          .build();
  //
  //  private final CreditCardAuthorizeActivity authorizeActivity =
  //      Workflow.newActivityStub(CreditCardAuthorizeActivityImpl.class, options);
  //
  //  private final CreditCardCaptureActivity captureActivity =
  //      Workflow.newActivityStub(CreditCardCaptureActivityImpl.class, options);

  @Override
  public void processCreditCard(CreditCardHandler handler) {
    int a = 2;
    //    authorizeActivity.authorize(handler);
    //    captureActivity.capture(handler);
  }
}
