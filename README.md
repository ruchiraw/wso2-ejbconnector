wso2-ejbconnector
=================

This contains a sample WSO2 ESB Connector which demonstrate how to write a connector for EJB service invocation.

Initial idea and code was taken from dushan@wso2.com and this refactored code is for the reference of anyone who is interested.


Custom EJB Connector
====================

* ejb-service directory contains a sample ejb service named Hello which has a method named `sayHello()`.

* ejb-connector contains a custom WSO2 ESB Connector which can be used to call `sayHello()` operation of Hello service.

* resources directory contains a `EJBProxy.xml`, which uses the ejbconnector for EJB service invocation and 
  the `glassfish-embedded-all-3.1.2.2.jar` which contains glassfish libraries.


Notes
-----
* WSO2 ESB Connectors are supported only from WSO2 ESB 4.8.0 onwards. Hence, if you are in an older version of ESB, you will have to implement your scenario using a class mediator.

* You will need to run the setup on JDK 1.7

* You can customize the connector as you need. Please find the comments at the EJBConnector.java under ejb-connector directory


Installation
============

1. Go to the resources directory and execute following command.
	- `mvn install:install-file -DgroupId=glassfish-embedded-all -DartifactId=glassfish-embedded-all -Dversion=3.1.2.2 -Dpackaging=jar -Dfile=glassfish-embedded-all-3.1.2.2.jar`

2. Go to the ejb-service directory and build the service using following command
	- `mvn clean install`

3. Upload the file `org.wso2.carbon.custom.ejb-1.0-SNAPSHOT.jar` into glassfish server using the web console.

4. Go to the ejb-connector directory and build the connectory using the following command.
	- `mvn clean install`

5. Go to the WSO2 ESB admin console's Main > Manage > Connectors > Add page and upload `ejb-connector-1.0.0.zip` that you get in ejb-connector/target directory.

6. Once the upload is finished, wait around 30 seconds till your connector get deployed and go to the Main > Manage > Connectors > List page.

7. Click on the 'Disabled' button under 'ejbconnector'. This will enable the connector.

8. Copy the EJBProxy.xml, which can be found in resources directory, into `wso2esb-4.8.1/repository/deployment/server/synapse-configs/default/proxy-services` directory.

9. Wait around 30 seconds till your proxy service get deployed and go to the following url from a browser.
	- `https://localhost:9443/services/EJBProxy?tryit`

10. Click on the 'Send' button that you see in the page

11. You should see the following logs[1] in the ESB console


References
==========

1.
`
[2014-10-10 19:31:44,877]  INFO - EJBConnector Initializing EJBConnector
[2014-10-10 19:31:44,877]  INFO - EJBConnector Initializing EJBConnector InitialContext
[2014-10-10 19:31:45,348] ERROR - SecuritySupportImpl java_security.expired_certificate
[2014-10-10 19:31:45,348] ERROR - SecuritySupportImpl java_security.expired_certificate
[2014-10-10 19:31:45,348] ERROR - SecuritySupportImpl java_security.expired_certificate
[2014-10-10 19:31:45,350] ERROR - SecuritySupportImpl java_security.expired_certificate
[2014-10-10 19:31:45,350] ERROR - SecuritySupportImpl java_security.expired_certificate
[2014-10-10 19:31:45,350] ERROR - SecuritySupportImpl java_security.expired_certificate
[2014-10-10 19:31:46,875]  INFO - EJBConnector EJB Service Response >>>>>>>>>>>>> Hello WSO2 ESB !!`
