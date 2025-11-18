package es.ciudadescolar.hospital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class HospitalErrorHandler implements ErrorHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(HospitalErrorHandler.class);
    @Override
    public void warning(SAXParseException exception) throws SAXException {
       LOG.warn("Alerta! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");   
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        LOG.error("Error! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");  
        throw  exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        LOG.error("Fatal error! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");  
        throw  exception;
    }
}
