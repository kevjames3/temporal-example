package io.temporal.samples;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.payments.CreditCardHandler;
import io.temporal.payments.CreditCardHandlerFactory;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

public class BulkCreditCardProcessing {

  private static Logger logger = Workflow.getLogger(BulkCreditCardProcessing.class);
  private static final String TASK_LIST = "CreditcardPaymnet";

  public static void main(String[] args) throws InterruptedException {
    // gRPC stubs wrapper that talks to the local docker instance of temporal service.
    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
    // client that can be used to start and signal workflows
    WorkflowClient client = WorkflowClient.newInstance(service);
    //    // worker factory that can be used to create workers for specific task lists
    WorkerFactory factory = WorkerFactory.newInstance(client);
    Worker worker = factory.newWorker(TASK_LIST);
    worker.registerWorkflowImplementationTypes(ProcessCreditCardWorkflowImpl.class);

    factory.start();
    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskList(TASK_LIST).build();
    final CreditCardHandler handler = CreditCardHandlerFactory.initPayment("foo");
    ProcessCreditCardWorkflow workflow =
        client.newWorkflowStub(ProcessCreditCardWorkflow.class, options);
    try {
      workflow.processCreditCard(handler);
    } catch (WorkflowException e) {
      System.out.println(e);
    } catch (StackOverflowError ex) {
      System.out.println(ex);
    }

    //    List<CreditCardHandler> handlerList = Lists.newArrayList();
    //    final Integer numOfCards = 1;
    //    for (int i = 0; i < numOfCards; i++) {
    //      CreditCardHandler handler = CreditCardHandlerFactory.initPayment("foo" + i);
    //      handler.startAuthorizing();
    //      handlerList.add(handler);
    //    }
    //
    //    while (true) {
    //      Thread.sleep(1000);
    //      for (CreditCardHandler handler : handlerList) {
    //        if (handler.isAuthorized() && !handler.captureStarted()) {
    //          System.out.println("Authorized for " + handler.id() + ", Capturing now");
    //          handler.startCapturing();
    //        }
    //        System.out.println(handler.toString());
    //      }
    //    }
    System.exit(0);
  }
}
