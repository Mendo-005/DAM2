package es.ciudadescolar.util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlManager 
{
    /**
     * @param ficheroXml
     * @return
     */
    public static List<Alumno> procesarXmlAlumnos(File ficheroXml)
    {
        List<Alumno> alumnos = new ArrayList<>();

        Alumno alumno = null;

        DocumentBuilderFactory obt = DocumentBuilderFactory.newInstance();

        obt.setValidating(true); // Cuando el XML se valide se fija el parametro TRUE
        obt.setIgnoringElementContentWhitespace(true); // Ignorar espacios sin informacion
        
        try 
        {
            DocumentBuilder db = obt.newDocumentBuilder();
            
            db.setErrorHandler(new AlumnoErrorHandler()); // Para personalizar el control de error

            Document documento = db.parse(ficheroXml);
            
            Element elementoRaiz = documento.getDocumentElement();
            //System.out.println("Se didsponen de ["+ elementoRaiz.getChildNodes().getLength()+"]");
            
            NodeList listaNodosAlumno = elementoRaiz.getChildNodes();

            for (int i = 0; i < listaNodosAlumno.getLength(); i++) 
            {
                Node nodoAlumno = listaNodosAlumno.item(i);
                // Sabemos que los noods alumnos son elementos
                if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element elementoAlumno = (Element) nodoAlumno;

                    Node nodoExpediente = elementoAlumno.getFirstChild();
                    //Node nodoNombre = nodoExpediente.getNextSibling();
                    Node nodoNombre = nodoAlumno.getLastChild().getPreviousSibling();
                    //Node nodoEdad = nodoAlumno.getLastChild();
                    Node nodoEdad = nodoExpediente.getNextSibling().getNextSibling();

                    // Aquí deberías extraer los datos de los nodos y crear el objeto Alumno
                    // Por ejemplo:
                    alumno = new Alumno(
                        nodoNombre.getTextContent().trim());
                    alumnos.add(alumno);
                    /*String expediente = nodoExpediente.getTextContent().trim();
                    String nombre = nodoNombre.getTextContent().trim();
                    int edad = Integer.parseInt(nodoEdad.getTextContent().trim());
                    alumno = new Alumno(expediente, nombre, edad);
                    alumnos.add(alumno);
                    */
                }
                
            }

        } catch (ParserConfigurationException | SAXException | IOException e) 
        {
            System.err.println("Error durante el parseo de XML: "+ e.getMessage());
        }
        
        return alumnos;
    }  
    
    public static void generarXmlAlumnos(List<Alumno> alumnos, File outputXmlFile, File dtdoutputFile)
    {
        /*
         * 1º - Creo la estructura DOM en la memoria RAM
         * 2º - Vuelco la estructura DOM a fichero XML
         */

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document documento = null;

        Element elementoAlumno = null;
        Element elementoEdad = null;

        try 
        {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new AlumnoErrorHandler());

            documento = db.newDocument();

            // Siempre creamos elemento y lo añadimos al arbol
            Element raiz = documento.createElement("estudiates");
            documento.appendChild(raiz);

            for(Alumno al: alumnos)
            {
                elementoAlumno = documento.createElement("alumno");
                elementoAlumno.setAttribute("nom", al.getExpediente());
                elementoAlumno.setAttribute("exp", al.getExpediente());
                elementoAlumno.setAttribute("edad", String.valueOf(al.getEdad()));

                elementoEdad = documento.createElement("edad");
                elementoEdad.setTextContent(String.valueOf(al.getEdad()));
                elementoAlumno.appendChild(elementoEdad);
                raiz.appendChild(elementoAlumno);
            }


        } catch (ParserConfigurationException e) {
            // TODO: handle exception
            System.err.println("Error durante la generacion del XML: " +e.getMessage());
        }
    }

    public static List<Alumno> devolverXmlExpediente(File ficheroXml) throws IOException
    {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alumno = null;

        DocumentBuilderFactory obt = DocumentBuilderFactory.newInstance();

        obt.setValidating(true); // Cuando el XML se valide se fija el parametro TRUE
        obt.setIgnoringElementContentWhitespace(true); // Ignorar espacios sin informacion
        
        try 
        {
            DocumentBuilder db = obt.newDocumentBuilder();
            
            db.setErrorHandler(new AlumnoErrorHandler()); // Para personalizar el control de error

            Document documento = db.parse(ficheroXml);
            
            Element elementoRaiz = documento.getDocumentElement();
            //System.out.println("Se didsponen de ["+ elementoRaiz.getChildNodes().getLength()+"]");
            
            NodeList listaNodosAlumno = elementoRaiz.getChildNodes();

            for (int i = 0; i < listaNodosAlumno.getLength(); i++) 
            {
                Node nodoAlumno = listaNodosAlumno.item(i);
                // Sabemos que los noods alumnos son elementos
                if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element elementoAlumno = (Element) nodoAlumno;

                    Node nodoExpediente = elementoAlumno.getFirstChild();

                    // Aquí deberías extraer los datos de los nodos y crear el objeto Alumno
                    // Por ejemplo:
                    alumno = new Alumno(
                        nodoExpediente.getTextContent().trim()
                    );
                    alumnos.add(alumno);  
                }
                
            }

        } catch (ParserConfigurationException | SAXException  e) 
        {
            System.err.println("Error durante el parseo de XML: "+ e.getMessage());
        }
        return alumnos;
    }
}

/*
 * 1º - Crear estructura dom
 * 2º - Sacar los datos
 */