package es.ciudadescolar.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AlumnoErrorHandler implements ErrorHandler {

    /*
     * El ErrorHandler no detiene por defecto el parseo del XML
     * 
     * warning -> avisa de problemas no críticos.
     * error -> error que podría ser recuperable.
     * fatalError -> error no recuperable.
     */
    @Override
    public void warning(SAXParseException exception) throws SAXException {
        // TODO Auto-generated method stub
        System.out.println("Alerta! ["+exception.getLineNumber()+"][" +exception.getMessage()+"]");
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        // TODO Auto-generated method stub
        System.out.println("Error! ["+exception.getLineNumber()+"][" +exception.getMessage()+"]");
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        // TODO Auto-generated method stub
        System.out.println("Fatal error! ["+exception.getLineNumber()+"][" +exception.getMessage()+"]");
        throw exception;
    }

    
}