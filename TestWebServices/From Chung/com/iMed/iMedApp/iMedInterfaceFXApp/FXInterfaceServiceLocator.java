/**
 * FXInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iMed.iMedApp.iMedInterfaceFXApp;

public class FXInterfaceServiceLocator extends org.apache.axis.client.Service implements com.iMed.iMedApp.iMedInterfaceFXApp.FXInterfaceService {

    public FXInterfaceServiceLocator() {
    }


    public FXInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FXInterfaceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FXInterface
    private java.lang.String FXInterface_address = "http://127.0.0.1:8080/axis/services/FXInterface";

    public java.lang.String getFXInterfaceAddress() {
        return FXInterface_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FXInterfaceWSDDServiceName = "FXInterface";

    public java.lang.String getFXInterfaceWSDDServiceName() {
        return FXInterfaceWSDDServiceName;
    }

    public void setFXInterfaceWSDDServiceName(java.lang.String name) {
        FXInterfaceWSDDServiceName = name;
    }

    public com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface getFXInterface() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FXInterface_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFXInterface(endpoint);
    }

    public com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface getFXInterface(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.iMed.iMedApp.iMedInterfaceFXApp.FXInterfaceSoapBindingStub _stub = new com.iMed.iMedApp.iMedInterfaceFXApp.FXInterfaceSoapBindingStub(portAddress, this);
            _stub.setPortName(getFXInterfaceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFXInterfaceEndpointAddress(java.lang.String address) {
        FXInterface_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                com.iMed.iMedApp.iMedInterfaceFXApp.FXInterfaceSoapBindingStub _stub = new com.iMed.iMedApp.iMedInterfaceFXApp.FXInterfaceSoapBindingStub(new java.net.URL(FXInterface_address), this);
                _stub.setPortName(getFXInterfaceWSDDServiceName());
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
        if ("FXInterface".equals(inputPortName)) {
            return getFXInterface();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.apache.org", "FXInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.apache.org", "FXInterface"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FXInterface".equals(portName)) {
            setFXInterfaceEndpointAddress(address);
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
