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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2018-10-11")
public class PageProperty implements org.apache.thrift.TBase<PageProperty, PageProperty._Fields>, java.io.Serializable, Cloneable, Comparable<PageProperty> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PageProperty");

  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField PROPERTY_FIELD_DESC = new org.apache.thrift.protocol.TField("property", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PagePropertyStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PagePropertyTupleSchemeFactory());
  }

  private Page url; // required
  private PagePropertyValue property; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    URL((short)1, "url"),
    PROPERTY((short)2, "property");

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
        case 1: // URL
          return URL;
        case 2: // PROPERTY
          return PROPERTY;
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

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Page.class)));
    tmpMap.put(_Fields.PROPERTY, new org.apache.thrift.meta_data.FieldMetaData("property", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PagePropertyValue.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PageProperty.class, metaDataMap);
  }

  public PageProperty() {
  }

  public PageProperty(
    Page url,
    PagePropertyValue property)
  {
    this();
    this.url = url;
    this.property = property;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PageProperty(PageProperty other) {
    if (other.is_set_url()) {
      this.url = new Page(other.url);
    }
    if (other.is_set_property()) {
      this.property = new PagePropertyValue(other.property);
    }
  }

  public PageProperty deepCopy() {
    return new PageProperty(this);
  }

  @Override
  public void clear() {
    this.url = null;
    this.property = null;
  }

  public Page get_url() {
    return this.url;
  }

  public void set_url(Page url) {
    this.url = url;
  }

  public void unset_url() {
    this.url = null;
  }

  /** Returns true if field url is set (has been assigned a value) and false otherwise */
  public boolean is_set_url() {
    return this.url != null;
  }

  public void set_url_isSet(boolean value) {
    if (!value) {
      this.url = null;
    }
  }

  public PagePropertyValue get_property() {
    return this.property;
  }

  public void set_property(PagePropertyValue property) {
    this.property = property;
  }

  public void unset_property() {
    this.property = null;
  }

  /** Returns true if field property is set (has been assigned a value) and false otherwise */
  public boolean is_set_property() {
    return this.property != null;
  }

  public void set_property_isSet(boolean value) {
    if (!value) {
      this.property = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case URL:
      if (value == null) {
        unset_url();
      } else {
        set_url((Page)value);
      }
      break;

    case PROPERTY:
      if (value == null) {
        unset_property();
      } else {
        set_property((PagePropertyValue)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case URL:
      return get_url();

    case PROPERTY:
      return get_property();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case URL:
      return is_set_url();
    case PROPERTY:
      return is_set_property();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PageProperty)
      return this.equals((PageProperty)that);
    return false;
  }

  public boolean equals(PageProperty that) {
    if (that == null)
      return false;

    boolean this_present_url = true && this.is_set_url();
    boolean that_present_url = true && that.is_set_url();
    if (this_present_url || that_present_url) {
      if (!(this_present_url && that_present_url))
        return false;
      if (!this.url.equals(that.url))
        return false;
    }

    boolean this_present_property = true && this.is_set_property();
    boolean that_present_property = true && that.is_set_property();
    if (this_present_property || that_present_property) {
      if (!(this_present_property && that_present_property))
        return false;
      if (!this.property.equals(that.property))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_url = true && (is_set_url());
    list.add(present_url);
    if (present_url)
      list.add(url);

    boolean present_property = true && (is_set_property());
    list.add(present_property);
    if (present_property)
      list.add(property);

    return list.hashCode();
  }

  @Override
  public int compareTo(PageProperty other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_url()).compareTo(other.is_set_url());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_url()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.url, other.url);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_property()).compareTo(other.is_set_property());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_property()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.property, other.property);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PageProperty(");
    boolean first = true;

    sb.append("url:");
    if (this.url == null) {
      sb.append("null");
    } else {
      sb.append(this.url);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("property:");
    if (this.property == null) {
      sb.append("null");
    } else {
      sb.append(this.property);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_url()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'url' is unset! Struct:" + toString());
    }

    if (!is_set_property()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'property' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
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

  private static class PagePropertyStandardSchemeFactory implements SchemeFactory {
    public PagePropertyStandardScheme getScheme() {
      return new PagePropertyStandardScheme();
    }
  }

  private static class PagePropertyStandardScheme extends StandardScheme<PageProperty> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PageProperty struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.url = new Page();
              struct.url.read(iprot);
              struct.set_url_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PROPERTY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.property = new PagePropertyValue();
              struct.property.read(iprot);
              struct.set_property_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PageProperty struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.url != null) {
        oprot.writeFieldBegin(URL_FIELD_DESC);
        struct.url.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.property != null) {
        oprot.writeFieldBegin(PROPERTY_FIELD_DESC);
        struct.property.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PagePropertyTupleSchemeFactory implements SchemeFactory {
    public PagePropertyTupleScheme getScheme() {
      return new PagePropertyTupleScheme();
    }
  }

  private static class PagePropertyTupleScheme extends TupleScheme<PageProperty> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PageProperty struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.url.write(oprot);
      struct.property.write(oprot);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PageProperty struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.url = new Page();
      struct.url.read(iprot);
      struct.set_url_isSet(true);
      struct.property = new PagePropertyValue();
      struct.property.read(iprot);
      struct.set_property_isSet(true);
    }
  }

}

