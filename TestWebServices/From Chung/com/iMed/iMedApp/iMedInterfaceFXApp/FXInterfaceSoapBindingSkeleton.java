/**
 * FXInterfaceSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iMed.iMedApp.iMedInterfaceFXApp;

public class FXInterfaceSoapBindingSkeleton implements com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface, org.apache.axis.wsdl.Skeleton {
    private com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("fxLogin", _params, new javax.xml.namespace.QName("", "fxLoginReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org", "fxLogin"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("fxLogin") == null) {
            _myOperations.put("fxLogin", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("fxLogin")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("fxPrintForm", _params, new javax.xml.namespace.QName("", "fxPrintFormReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org", "fxPrintForm"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("fxPrintForm") == null) {
            _myOperations.put("fxPrintForm", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("fxPrintForm")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("fxVerifyHnVnAn", _params, new javax.xml.namespace.QName("", "fxVerifyHnVnAnReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org", "fxVerifyHnVnAn"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("fxVerifyHnVnAn") == null) {
            _myOperations.put("fxVerifyHnVnAn", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("fxVerifyHnVnAn")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("fxListForm", _params, new javax.xml.namespace.QName("", "fxListFormReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org", "fxListForm"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("fxListForm") == null) {
            _myOperations.put("fxListForm", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("fxListForm")).add(_oper);
    }

    public FXInterfaceSoapBindingSkeleton() {
        this.impl = new com.iMed.iMedApp.iMedInterfaceFXApp.FXInterfaceSoapBindingImpl();
    }

    public FXInterfaceSoapBindingSkeleton(com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface impl) {
        this.impl = impl;
    }
    public java.lang.String fxLogin(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.fxLogin(in0, in1);
        return ret;
    }

    public java.lang.String fxPrintForm(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.fxPrintForm(in0, in1, in2, in3);
        return ret;
    }

    public java.lang.String fxVerifyHnVnAn(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.fxVerifyHnVnAn(in0, in1, in2, in3);
        return ret;
    }

    public java.lang.String fxListForm(java.lang.String in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.fxListForm(in0);
        return ret;
    }

}
