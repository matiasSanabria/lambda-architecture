#!/usr/bin/env bash
rm -rf gen-javabean src/test/java/tp/bigdata/schema
#thrift -r --gen java:beans,hashcode,nocamel src/main/schema.thrift
thrift -r --gen java:beans,nocamel src/schema.thrift
mv gen-javabean/ src/test/java/tp/bigdata/schema
rm -rf gen-javabean