package io.temporal.samples;

import com.google.common.collect.Lists;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.Workflow;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;

public class BulkCreditCardProcessingWorker {

  private static Logger logger = Workflow.getLogger(BulkCreditCardProcessingWorker.class);
  private static final String TASK_LIST = "CreditCardPayment";

  public static void main(String[] args) throws InterruptedException {
    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
    WorkflowClient client = WorkflowClient.newInstance(service);
    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskList(TASK_LIST).build();

    WorkerFactory factory = WorkerFactory.newInstance(client);
    Worker worker = factory.newWorker(TASK_LIST);
    worker.registerWorkflowImplementationTypes(ProcessCreditCardWorkflowImpl.class);
    worker.registerActivitiesImplementations(
        new CreditCardCaptureActivityImpl(), new CreditCardAuthorizeActivityImpl());

    List<Thread> parallelWorkloads = Lists.newArrayList();
    for (int i = 0; i < 10; i++) {
      final Thread thread =
          new Thread(
              () -> {
                ProcessCreditCardWorkflow workflow =
                    client.newWorkflowStub(ProcessCreditCardWorkflow.class, options);
                factory.start();
                try {
                  workflow.processCreditCard(UUID.randomUUID().toString());
                } catch (WorkflowException e) {
                  System.out.println(e);
                }
              });
      thread.start();
      parallelWorkloads.add(thread);
    }

    for (final Thread thread : parallelWorkloads) {
      thread.join(); // Wait until all the threads are done
    }
    System.exit(0);
  }
}
