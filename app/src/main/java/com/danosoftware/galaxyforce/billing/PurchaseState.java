package com.danosoftware.galaxyforce.billing;

public enum PurchaseState {
  // item has been purchased
  PURCHASED,
  // item has not been purchased
  NOT_PURCHASED,
  // purchase has been initialised but not completed (could succeed or fail)
  PENDING,
  NOT_READY
}
