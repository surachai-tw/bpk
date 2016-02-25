@ECHO OFF
ECHO Create iMedFXInterface.jar...
CD /d classes
jar cvf ..\iMedFXInterface.jar com/iMed/iMedApp/iMedInterfaceFXApp/*.class com/iMed/iMedApp/iMedInterfaceFXApp/ibatis/*.class com/iMed/iMedApp/iMedInterfaceFXApp/iMedInterfaceFXVO/*.class
PAUSE