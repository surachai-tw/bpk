/**
 * SsbInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bpk.app.ssb;

public interface SsbInterface extends java.rmi.Remote {
    public java.lang.String getConnectionString() throws java.rmi.RemoteException;
    public java.lang.String executeNonQuery(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String executeDataTable(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
}
