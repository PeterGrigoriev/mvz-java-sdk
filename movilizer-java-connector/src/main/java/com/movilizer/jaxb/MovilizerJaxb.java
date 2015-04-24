package com.movilizer.jaxb;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerJaxb {
    public static final String MOVILIZER_WSDL_V12 = "com.movilitas.movilizer.v12";
    protected static ILogger logger = ComponentLogger.getInstance("JaxbMarshalling");
    protected final JAXBContext jaxbContext;


    public MovilizerJaxb() {
        jaxbContext = loadContext();
    }

    private JAXBContext loadContext() {
        try {
            return JAXBContext.newInstance(MOVILIZER_WSDL_V12);
        } catch (JAXBException e) {
            logger.fatal(e);
            return null;
        }
    }


}
