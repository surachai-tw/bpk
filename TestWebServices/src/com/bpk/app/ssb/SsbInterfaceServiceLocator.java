/**
 * SsbInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bpk.app.ssb;

public class SsbInterfaceServiceLocator extends org.apache.axis.client.Service implements com.bpk.app.ssb.SsbInterfaceService {

    public SsbInterfaceServiceLocator() {
    }


    public SsbInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SsbInterfaceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConnWSSqlSvcWsdl
    private java.lang.String ConnWSSqlSvcWsdl_address = "http://172.16.2.60:9002/ConnWSSql.svc?wsdl";

    public java.lang.String getConnWSSqlSvcWsdlAddress() {
        return ConnWSSqlSvcWsdl_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConnWSSqlSvcWsdlWSDDServiceName = "ConnWSSql.svc?wsdl";

    public java.lang.String getConnWSSqlSvcWsdlWSDDServiceName() {
        return ConnWSSqlSvcWsdlWSDDServiceName;
    }

    public void setConnWSSqlSvcWsdlWSDDServiceName(java.lang.String name) {
        ConnWSSqlSvcWsdlWSDDServiceName = name;
    }

    public com.bpk.app.ssb.SsbInterface getConnWSSqlSvcWsdl() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConnWSSqlSvcWsdl_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConnWSSqlSvcWsdl(endpoint);
    }

    public com.bpk.app.ssb.SsbInterface getConnWSSqlSvcWsdl(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bpk.app.ssb.ConnWSSqlSvcWsdlSoapBindingStub _stub = new com.bpk.app.ssb.ConnWSSqlSvcWsdlSoapBindingStub(portAddress, this);
            _stub.setPortName(getConnWSSqlSvcWsdlWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConnWSSqlSvcWsdlEndpointAddress(java.lang.String address) {
        ConnWSSqlSvcWsdl_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.bpk.app.ssb.SsbInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bpk.app.ssb.ConnWSSqlSvcWsdlSoapBindingStub _stub = new com.bpk.app.ssb.ConnWSSqlSvcWsdlSoapBindingStub(new java.net.URL(ConnWSSqlSvcWsdl_address), this);
                _stub.setPortName(getConnWSSqlSvcWsdlWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ConnWSSql.svc?wsdl".equals(inputPortName)) {
            return getConnWSSqlSvcWsdl();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org", "SsbInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org", "ConnWSSql.svc?wsdl"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ConnWSSqlSvcWsdl".equals(portName)) {
            setConnWSSqlSvcWsdlEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
