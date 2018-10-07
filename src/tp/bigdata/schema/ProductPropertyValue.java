/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package tp.bigdata.schema;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
public class ProductPropertyValue extends org.apache.thrift.TUnion<ProductPropertyValue, ProductPropertyValue._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProductPropertyValue");
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField STOCK_FIELD_DESC = new org.apache.thrift.protocol.TField("stock", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField SALE_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("salePrice", org.apache.thrift.protocol.TType.DOUBLE, (short)3);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DESCRIPTION((short)1, "description"),
    STOCK((short)2, "stock"),
    SALE_PRICE((short)3, "salePrice");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DESCRIPTION
          return DESCRIPTION;
        case 2: // STOCK
          return STOCK;
        case 3: // SALE_PRICE
          return SALE_PRICE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STOCK, new org.apache.thrift.meta_data.FieldMetaData("stock", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.SALE_PRICE, new org.apache.thrift.meta_data.FieldMetaData("salePrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProductPropertyValue.class, metaDataMap);
  }

  public ProductPropertyValue() {
    super();
  }

  public ProductPropertyValue(_Fields setField, Object value) {
    super(setField, value);
  }

  public ProductPropertyValue(ProductPropertyValue other) {
    super(other);
  }
  public ProductPropertyValue deepCopy() {
    return new ProductPropertyValue(this);
  }

  public static ProductPropertyValue description(String value) {
    ProductPropertyValue x = new ProductPropertyValue();
    x.set_description(value);
    return x;
  }

  public static ProductPropertyValue stock(double value) {
    ProductPropertyValue x = new ProductPropertyValue();
    x.set_stock(value);
    return x;
  }

  public static ProductPropertyValue salePrice(double value) {
    ProductPropertyValue x = new ProductPropertyValue();
    x.set_salePrice(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case DESCRIPTION:
        if (value instanceof String) {
          break;
        }
        throw new ClassCastException("Was expecting value of type String for field 'description', but got " + value.getClass().getSimpleName());
      case STOCK:
        if (value instanceof Double) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Double for field 'stock', but got " + value.getClass().getSimpleName());
      case SALE_PRICE:
        if (value instanceof Double) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Double for field 'salePrice', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case DESCRIPTION:
          if (field.type == DESCRIPTION_FIELD_DESC.type) {
            String description;
            description = iprot.readString();
            return description;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case STOCK:
          if (field.type == STOCK_FIELD_DESC.type) {
            Double stock;
            stock = iprot.readDouble();
            return stock;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case SALE_PRICE:
          if (field.type == SALE_PRICE_FIELD_DESC.type) {
            Double salePrice;
            salePrice = iprot.readDouble();
            return salePrice;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case DESCRIPTION:
        String description = (String)value_;
        oprot.writeString(description);
        return;
      case STOCK:
        Double stock = (Double)value_;
        oprot.writeDouble(stock);
        return;
      case SALE_PRICE:
        Double salePrice = (Double)value_;
        oprot.writeDouble(salePrice);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case DESCRIPTION:
          String description;
          description = iprot.readString();
          return description;
        case STOCK:
          Double stock;
          stock = iprot.readDouble();
          return stock;
        case SALE_PRICE:
          Double salePrice;
          salePrice = iprot.readDouble();
          return salePrice;
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case DESCRIPTION:
        String description = (String)value_;
        oprot.writeString(description);
        return;
      case STOCK:
        Double stock = (Double)value_;
        oprot.writeDouble(stock);
        return;
      case SALE_PRICE:
        Double salePrice = (Double)value_;
        oprot.writeDouble(salePrice);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case DESCRIPTION:
        return DESCRIPTION_FIELD_DESC;
      case STOCK:
        return STOCK_FIELD_DESC;
      case SALE_PRICE:
        return SALE_PRICE_FIELD_DESC;
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public String get_description() {
    if (getSetField() == _Fields.DESCRIPTION) {
      return (String)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'description' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void set_description(String value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.DESCRIPTION;
    value_ = value;
  }

  public double get_stock() {
    if (getSetField() == _Fields.STOCK) {
      return (Double)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'stock' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void set_stock(double value) {
    setField_ = _Fields.STOCK;
    value_ = value;
  }

  public double get_salePrice() {
    if (getSetField() == _Fields.SALE_PRICE) {
      return (Double)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'salePrice' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void set_salePrice(double value) {
    setField_ = _Fields.SALE_PRICE;
    value_ = value;
  }

  public boolean is_set_description() {
    return setField_ == _Fields.DESCRIPTION;
  }


  public boolean is_set_stock() {
    return setField_ == _Fields.STOCK;
  }


  public boolean is_set_salePrice() {
    return setField_ == _Fields.SALE_PRICE;
  }


  public boolean equals(Object other) {
    if (other instanceof ProductPropertyValue) {
      return equals((ProductPropertyValue)other);
    } else {
      return false;
    }
  }

  public boolean equals(ProductPropertyValue other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(ProductPropertyValue other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();
    list.add(this.getClass().getName());
    org.apache.thrift.TFieldIdEnum setField = getSetField();
    if (setField != null) {
      list.add(setField.getThriftFieldId());
      Object value = getFieldValue();
      if (value instanceof org.apache.thrift.TEnum) {
        list.add(((org.apache.thrift.TEnum)getFieldValue()).getValue());
      } else {
        list.add(value);
      }
    }
    return list.hashCode();
  }
  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }


  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }


}