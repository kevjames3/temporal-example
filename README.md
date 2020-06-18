# Temporal Example

Goal of this code is to try some sample code against the [temporal-java-sdk](https://github.com/temporalio/temporal-java-sdk) as well as the overall Cadence workflow. For some more examples, I would recommend checking out the [example code](https://github.com/temporalio/temporal-java-samples) as well.

## Installation

1. [Follow these instructions](https://docs.temporal.io/docs/installing-server) and launch the server. Make sure you can go to
1. Clone this repository
1. Use Intellij CE to import this project; I did not have time to test via the CMD line at the time of this writing
1. Go to `BulkCreditCardProcessingWorker` and execute from there.

## Explanation

The goal of this is to demonstrate how Temporal's platform could work with a credit card platform. Credit cards typically have two stages with them - one being authorization and later on capture. Typically, you could do both phases in one go, but sometimes, you will need to do business logic between the Capture and Authorization phase of the credit card processing. Next, through discussion with the maintainers, I identified that Workflows need to occur separately for each credit card. So, I followed this method.

1. I needed to create a `CreditCardService` that would gather a state of a credit card via Singleton pattern
1. I then created a `FakeCreditCardInstance` that would have a series of attributes attached that would follow a series of states, from Authorized to Capture. The time it would take was dictated by `ScheduledFuture` and random amounts of time
1. The `ProcessCreditCardWorkflowImpl` takes the credit card id that needs to be processed. For all intents and purposes, it is a dummy id.
1. The `CreditCardAuthorizeActivityImpl` and `CreditCardAuthorizeCaptureImpl` make sure that the relevant stage transitions happen. If the credit card is not complete at that phase, an exception is thrown and `Temporal` will follow up at a later time as specified in `ProcessCreditCardWorkflowImpl` (see `options` instance variable).
1. Lastly, I kick off `N` number of credit card workflows via threads in the `BulkCreditCardProcessingWorker` and then waiting for all threads to conclude before shutting down

## What I Learned in the Process

1. I originally tried to pass in a `CreditCardInstance` into the `ProcessCreditCardWorkflowImpl`...that is not allowed, as the workflow will need an object that can be deserialized. Instead, passing a reference works, and the `Activities` are the ones that should handle service calls
1. Threads are great for demos and do have downsides, but when you want to have the program wait on them before continuing, `Thread#join()` does wonders
1. With this process, I am wondering how architectures should change when it comes to orchestration. As such, the methods that I put in place here are the "pull" methods, but when they fail, expontential decay is put into place.

## Next Steps/Future

1. I would like to handle the case of stop calling when a credit card fails
1. I would like to inject some more craziness, like when additional fraud is injected into the process
