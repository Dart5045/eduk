/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.eduk.kafka.confirmation.avro.model;
@org.apache.avro.specific.AvroGenerated
public enum PaymentConfirmationStatus implements org.apache.avro.generic.GenericEnumSymbol<PaymentConfirmationStatus> {
  PENDING, CANCELLED  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"PaymentConfirmationStatus\",\"namespace\":\"com.eduk.kafka.confirmation.avro.model\",\"symbols\":[\"PENDING\",\"CANCELLED\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
