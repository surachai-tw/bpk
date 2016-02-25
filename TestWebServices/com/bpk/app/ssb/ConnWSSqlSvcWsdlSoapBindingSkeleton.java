/**
 * ConnWSSqlSvcWsdlSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bpk.app.ssb;

public class ConnWSSqlSvcWsdlSoapBindingSkeleton implements com.bpk.app.ssb.SsbInterface, org.apache.axis.wsdl.Skeleton {
    private com.bpk.app.ssb.SsbInterface impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getConnectionString", _params, new javax.xml.namespace.QName("http://tempuri.org/", "ns1:GetConnectionStringReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org/", "ns1:GetConnectionString"));
        _oper.setSoapAction("http://tempuri.org/IConnWSSql/GetConnectionString");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getConnectionString") == null) {
            _myOperations.put("getConnectionString", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getConnectionString")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("executeNonQuery", _params, new javax.xml.namespace.QName("", "ExecuteNonQueryReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org", "ExecuteNonQuery"));
        _oper.setSoapAction("http://tempuri.org/IConnWSSql/ExecuteNonQuery");
        _myOperationsList.add(_oper);
        if (_myOperations.get("executeNonQuery") == null) {
            _myOperations.put("executeNonQuery", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("executeNonQuery")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("executeDataTable", _params, new javax.xml.namespace.QName("", "ExecuteDataTableReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org", "ExecuteDataTable"));
        _oper.setSoapAction("http://tempuri.org/IConnWSSql/ExecuteDataTable");
        _myOperationsList.add(_oper);
        if (_myOperations.get("executeDataTable") == null) {
            _myOperations.put("executeDataTable", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("executeDataTable")).add(_oper);
    }

    public ConnWSSqlSvcWsdlSoapBindingSkeleton() {
        this.impl = new com.bpk.app.ssb.ConnWSSqlSvcWsdlSoapBindingImpl();
    }

    public ConnWSSqlSvcWsdlSoapBindingSkeleton(com.bpk.app.ssb.SsbInterface impl) {
        this.impl = impl;
    }
    public java.lang.String getConnectionString() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getConnectionString();
        return ret;
    }

    public java.lang.String executeNonQuery(java.lang.String in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.executeNonQuery(in0);
        return ret;
    }

    public java.lang.String executeDataTable(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.executeDataTable(in0, in1);
        return ret;
    }

}
