package io.temporal.payments;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FakeCreditCardHandler implements CreditCardHandler {
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private boolean failed;
  private boolean captured;
  private boolean authorized;
  private boolean willFail;
  private boolean captureStarted;
  private boolean authorizationStarted;
  private int secondsToFailure;
  private int timeToAuthorized;
  private int timeToCapture;
  private final String id;

  private static final int MIN_START_TIME = 2;
  private static final int MAX_TIME_TO_NEXT_STATE = 5;

  public FakeCreditCardHandler(final String id) {
    this.id = id;
    failed = false;
    captured = false;
    authorized = false;
    willFail = false;
    captureStarted = false;
    willFail = new Random().nextInt(10) <= 10;

    if (willFail) {
      secondsToFailure = new Random().nextInt(MAX_TIME_TO_NEXT_STATE);
    } else {
      timeToAuthorized = MIN_START_TIME + new Random().nextInt(MAX_TIME_TO_NEXT_STATE);
      timeToCapture = timeToAuthorized + new Random().nextInt(MAX_TIME_TO_NEXT_STATE);
    }
  }

  @Override
  public boolean hasFailed() {
    return failed;
  }

  @Override
  public boolean isStillProcessing() {
    return !(failed ^ captured);
  }

  @Override
  public boolean isAuthorized() {
    return authorized;
  }

  @Override
  public boolean isCaptured() {
    return captured;
  }

  @Override
  public void startAuthorizing() {
    authorizationStarted = true;
    if (willFail) {
      ScheduledFuture future =
          scheduler.schedule(
              new Runnable() {
                @Override
                public void run() {
                  failed = true;
                }
              },
              secondsToFailure,
              TimeUnit.SECONDS);
    } else {
      ScheduledFuture future =
          scheduler.schedule(
              new Runnable() {
                @Override
                public void run() {
                  authorized = true;
                }
              },
              timeToAuthorized,
              TimeUnit.SECONDS);
    }
  }

  @Override
  public void startCapturing() {
    captureStarted = true;
    scheduler.schedule(
        new Runnable() {
          public void run() {
            captured = true;
          }
        },
        timeToCapture,
        TimeUnit.SECONDS);
  }

  @Override
  public boolean captureStarted() {
    return captureStarted;
  }

  @Override
  public boolean authorizationStarted() {
    return authorizationStarted;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Handler (");
    builder.append(id);
    builder.append(") Processing: ");
    builder.append(isStillProcessing());
    builder.append(" Failed: ");
    builder.append(hasFailed());
    builder.append(" Authorized: ");
    builder.append(isAuthorized());
    builder.append(" Captured: ");
    builder.append(isCaptured());
    return builder.toString();
  }
}
