1.Code ��ǹ��� ���������Ѻ Web Service AXIS (���Ш� interface �Ѻ�ҧ FX-Fuji Xerox ���� WebService ����ѡ

2.��������¡�á�˹� method ����������¡��ҹ WebService ������� FXInterface.java ����������ǡѺ������͹
(��¹�� interface ������)

3.�������¹�����ѹ batch ��� 1_compile_FXInterface.bat ���� compile �� .class

4. �ҡ����ѹ batch ��� 2_Generate_WSDL_From_Class.bat �������ҧ��� FXInterface.wsdl

5. �ҡ����ѹ��� batch ��� 3_Generate_Java_From_WSDL.bat ������� Generate ����͡��
��������������� com\iMed\iMedApp\iMedInterfaceFXApp ��������ͨҡ���������ա�չ֧

6. �ѹ��� Server ���ŧ Axis ���������Ǣ���� �ҡ����ѹ��� batch 4_deploy_wsdd.bat ���� deploy WebService
(deploy ����ǡ��ԧ �������¡��͹���� error ��������ѧ�������� class ŧ�)

7. copy code �����ҡ��� 5 ����ŧ������� Code ���ͻ�ͧ�ѹ�������ѹ batch ����� 5 ���������� 
��觨з���� Code ��������¹�����١ Generate �Ѻ

8. ������� Code\com\iMed\iMedApp\iMedInterfaceFXApp\FXInterfaceSoapBindingImpl.java ����Ѻ��¹ Code �͡������� method �����ӧҹ����

9. batch ����Ѻ compile  / ���ҧ jar ����������� Code