package io.temporal.payments;

import com.google.common.collect.Maps;
import java.util.Map;

public class CreditCardService {
  private static Map<String, CreditCardInstance> list = Maps.newHashMap();

  public static CreditCardInstance getPaymentInstance(final String uniqueId) {
    if (!list.containsKey(uniqueId)) {
      list.put(uniqueId, new FakeCreditCardInstance(uniqueId));
    }
    return list.get(uniqueId);
  }
}
