/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.custom.connector;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.custom.ejb.HelloIF;

public class EJBConnector extends AbstractConnector {

    private static final Log log = LogFactory.getLog(EJBConnector.class);

    @Override
    public void connect(MessageContext ctx) {
        log.info("Initializing EJBConnector");
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        try {
            //switching the classloader to prevent class loading glassfish classloading issues
            currentThread.setContextClassLoader(getClass().getClassLoader());
            callEJB();
        } catch (Exception e) {
            log.error("Error calling EJB Service from EJBConnector", e);
        } finally {
            if (oldClassLoader != null) {
                //resetting the classloader
                currentThread.setContextClassLoader(oldClassLoader);
            }
        }
    }

    /**
     * Calls the EJB Service. Will have to modify as need to fit your requirement.
     *
     * @throws Exception
     */
    public void callEJB() throws Exception {
        InitialContext context = getInitialContext();// new InitialContext();
        String serviceReference = "java:global/org.wso2.carbon.custom.ejb-1.0-SNAPSHOT/HelloBean";
        HelloIF clientBean = (HelloIF) context.lookup(serviceReference);
        log.info("EJB Service Response >>>>>>>>>>>>> " + clientBean.sayHello());
    }

    /**
     * Creates the initial context to be used.
     * TODO: In order to improve the performance, you might need to cache this properly
     *
     * @return context to be used for service lookup
     * @throws NamingException
     */
    private static InitialContext getInitialContext() throws NamingException {
        log.info("Initializing EJBConnector InitialContext");
        Properties prop = new Properties();
        prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        prop.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        prop.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        prop.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        return new InitialContext(prop);
    }
}
