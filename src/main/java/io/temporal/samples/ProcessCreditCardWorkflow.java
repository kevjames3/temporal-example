package io.temporal.samples;

import io.temporal.payments.CreditCardHandler;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ProcessCreditCardWorkflow {
  @WorkflowMethod
  void processCreditCard(CreditCardHandler handler);
}
