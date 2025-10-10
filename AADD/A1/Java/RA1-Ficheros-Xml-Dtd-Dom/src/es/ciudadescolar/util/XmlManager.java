package es.ciudadescolar.util;

// Importaciones necesarias para el manejo de archivos
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Importaciones para el trabajo con XML y validación XSD
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// Importaciones para la transformación y escritura de XML
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// Importaciones para validación XSD
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

// Importaciones para el manejo del DOM (Document Object Model)
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Clase XmlManager
 * Esta clase se encarga de procesar archivos XML con información de alumnos
 * y de generar nuevos archivos XML a partir de listas de objetos Alumno.
 * Utiliza DOM (Document Object Model) para el parsing y manipulación del XML.
 */
public class XmlManager 
{

    /**
     * Método para procesar un archivo XML de alumnos y validarlo contra un XSD
     * 
     * @param ficheroXml El archivo XML que contiene los datos de los alumnos
     * @param ficheroXsd El archivo XSD que contiene el esquema de validación
     * @return Lista de objetos Alumno extraídos del XML
     */
    public static List<Alumno> procesarXMLAlumnos (File ficheroXml, File ficheroXsd)
    {
        // Creamos una lista para almacenar los alumnos que extraigamos del XML
        List<Alumno> alumnos = new ArrayList<Alumno> ();

        // Variable temporal para crear cada objeto Alumno
        Alumno alumno = null;

        // 1. CONFIGURACIÓN DEL PARSER XML
        // DocumentBuilderFactory es una factory que nos permite crear DocumentBuilders
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Configuramos que NO valide contra DTD (porque vamos a validar contra XSD)
        dbf.setValidating(false); // (XSD) Cuando el xml a procesar se valide contra un DTD he de fijar el parámetro TRUE

        // 2. CONFIGURACIÓN PARA VALIDACIÓN XSD
        // Para validar contra un xsd, necesito que se consideren los white spaces
        // disponemos de los siguentes atributos en el elemento XML
        // https://www.w3.org/2001/XMLSchema-instance
        
        // Habilitamos el reconocimiento de namespaces (necesario para XSD)
        dbf.setNamespaceAware(true);
        
        // Ignoramos nodos con espacios en blanco sin información útil
        dbf.setIgnoringElementContentWhitespace(true); // ignorar nodos con espacios en blanco (sin información útil)
        
        // Creamos una factory para esquemas XML Schema
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
        // Variable para almacenar el esquema XSD
        Schema schema = null;
        
        try 
        {
            // 3. CARGAMOS EL ESQUEMA XSD
            // Parseamos el archivo XSD para crear el objeto Schema
            schema = sf.newSchema(ficheroXsd);

            // Asignamos el esquema al DocumentBuilderFactory para que valide
            dbf.setSchema(schema);

            // 4. CREAMOS EL PARSER XML
            // Creamos el DocumentBuilder que parsea el XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Asignamos un manejador de errores personalizado para la validación
            db.setErrorHandler(new AlumnoErrorHandler()); // Para personalizar el control de los errores de validación

            // 5. PARSEAMOS EL ARCHIVO XML
            // Parseamos el archivo XML y creamos el árbol DOM en memoria
            Document documento = db.parse(ficheroXml);

            // 6. NAVEGAMOS POR EL ÁRBOL DOM PARA EXTRAER LOS DATOS
            // Obtenemos el elemento raíz del XML (en este caso "alumnos")
            Element elementoRaiz = documento.getDocumentElement(); // obtenemos el elemento Raiz del xml alumnos: "alumnos"

            //System.out.println("Se disponen de ["+elementoRaiz.getChildNodes().getLength()+"] alumnos");

            // Obtenemos todos los nodos hijo del elemento raíz (los elementos "alumno")
            NodeList listaNodosAlumno = elementoRaiz.getChildNodes();

            // 7. ITERAMOS SOBRE CADA NODO ALUMNO
            // Recorremos cada nodo hijo para procesar cada alumno
            for (int i=0; i<listaNodosAlumno.getLength();i++)
            {
                // Obtenemos el nodo actual
                Node nodoAlumno = listaNodosAlumno.item(i);

                // Verificamos que sea un nodo de tipo ELEMENT (no un nodo de texto o comentario)
                // sabemos que los nodos alumno son elementos (Element)
                if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE)
                {
                    // Convertimos el nodo a Element para poder trabajar con él
                    Element elementoAlumno = (Element) nodoAlumno;

                    // 8. EXTRAEMOS LOS DATOS DE CADA ALUMNO
                    // Navegamos por los nodos hijo para extraer expediente, nombre y edad
                    // Estas líneas usan diferentes formas de navegar por el árbol DOM:
                    Node nodoExpediente = elementoAlumno.getFirstChild();                    // Primer hijo
                    //Node nodoNombre = nodoExpediente.getNextSibling();                    // Hermano siguiente
                    Node nodoNombre = nodoAlumno.getLastChild().getPreviousSibling();       // Penúltimo hijo
                    //Node nodoEdad = nodoAlumno.getLastChild();                           // Último hijo
                    Node nodoEdad = nodoExpediente.getNextSibling().getNextSibling();       // Tercer hijo

                    // 9. CREAMOS EL OBJETO ALUMNO Y LO AÑADIMOS A LA LISTA
                    // Extraemos el contenido de texto de cada nodo y creamos el objeto Alumno
                    //alumno = new Alumno(nodoNombre.getTextContent(), nodoExpediente.getFirstChild().getNodeValue(), Integer.parseInt(nodoEdad.getTextContent()));
                    alumno = new Alumno(nodoNombre.getTextContent(), nodoExpediente.getTextContent(), Integer.parseInt(nodoEdad.getTextContent()));
                    alumnos.add(alumno);
                }
            }

        } catch (ParserConfigurationException e) 
        {
            // Error en la configuración del parser
            System.err.println("Error durante el parseo de XML: "+ e.getMessage());
        } catch (SAXException e) {
            // Error durante el parsing del XML (estructura inválida, no válido contra XSD, etc.)
             System.err.println("Error durante el parseo de XML: "+ e.getMessage());
        } catch (IOException e) {
            // Error de entrada/salida (archivo no encontrado, permisos, etc.)
             System.err.println("Error durante el parseo de XML: "+ e.getMessage());
        }
        
        // Devolvemos la lista de alumnos extraída del XML
        return alumnos;
        

    }

    /**
     * Método para generar un archivo XML a partir de una lista de objetos Alumno
     * El XML generado se validará contra un esquema XSD
     * 
     * @param alumnos Lista de objetos Alumno a escribir en el XML
     * @param outputXmlFile Archivo XML de salida donde se escribirán los datos
     * @param xsdOutputXml Archivo XSD que define el esquema de validación para el XML de salida
     */
    public static void generarXmlAlumnos(List<Alumno> alumnos, File outputXmlFile, File xsdOutputXml)
    {
            /*
             * PROCESO DE GENERACIÓN XML EN DOS FASES:
             *  1º - Creo la estructura DOM en la memoria RAM
             *  2º - Vuelco la estructura DOM a fichero XML
             */

             // Variables para la construcción del DOM
             DocumentBuilderFactory dbf = null;
             DocumentBuilder db = null;
             Document documento  = null;
            
             // Variables para elementos XML que vamos a crear
             Element elementoAlumno =null;
             Element elementoEdad = null;

             try 
             {
                // 1. CONFIGURACIÓN DEL CONSTRUCTOR DE DOCUMENTOS
                // Creamos la factory para construir documentos XML
                dbf = DocumentBuilderFactory.newInstance();
                // Creamos el constructor de documentos
                db = dbf.newDocumentBuilder();
                // Asignamos nuestro manejador de errores personalizado
                db.setErrorHandler(new AlumnoErrorHandler());

                // 2. CREACIÓN DEL DOCUMENTO XML EN MEMORIA
                // Creamos un nuevo documento XML vacío
                documento = db.newDocument();

                // 3. CREACIÓN DEL ELEMENTO RAÍZ Y CONFIGURACIÓN DE NAMESPACES
                // siempre creamos elemento y lo añadimos al árbol (2 acciones)
                Element raiz = documento.createElement("estudiantes");

                // Añadimos los atributos necesarios para la validación XSD
                // Este namespace es estándar para la validación de esquemas XML
                raiz.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                // Indicamos qué archivo XSD debe usarse para validar este XML
                raiz.setAttribute("xsi:noNamespaceSchemaLocation", xsdOutputXml.getName());
                
                // Añadimos el elemento raíz al documento
                documento.appendChild(raiz);

                // 4. ITERACIÓN SOBRE LA LISTA DE ALUMNOS PARA CREAR ELEMENTOS XML
                // Por cada objeto Alumno, creamos un elemento XML correspondiente
                for (Alumno al: alumnos)
                {
                        // Creamos el elemento "alumno"
                        elementoAlumno= documento.createElement("alumno");
                        
                        // Añadimos los datos del alumno como atributos del elemento
                        elementoAlumno.setAttribute("nombre", al.getNombre());
                        elementoAlumno.setAttribute("exp", al.getExpediente());
                        elementoAlumno.setAttribute("edad", al.getEdad().toString());

                        // También creamos un elemento hijo "edad" con el contenido de texto
                        elementoEdad = documento.createElement("edad");
                        elementoEdad.setTextContent(al.getEdad().toString());
                        
                        // Añadimos el elemento edad como hijo del elemento alumno
                        elementoAlumno.appendChild(elementoEdad);
                        
                        // Añadimos el elemento alumno como hijo del elemento raíz
                        raiz.appendChild(elementoAlumno);
                }

            // 5. TRANSFORMACIÓN DEL DOM A ARCHIVO XML
            // ya tenemos lista nuestra estructura DOM en memoria. Puedo por tanto volcarla al fichero XML

            /*
             * En este ejemplo de clase, el fichero de salida deberá llamarse alumnos2.xml y validarse contra un DTD llamado alumnos2.dtd
             * La forma en la que indicaremos la validación contra DTD será distinta de la que usaremos más adelante cuando validemos contra XSD (schema)
             */
            
            // Variables para el proceso de transformación DOM -> XML
            TransformerFactory tf = null; 
			Transformer t =null;
			DOMSource ds = null;
			StreamResult sr = null;
			//DOMImplementation domImp = null;
			//DocumentType docType = null;
			
			try 
			{
                // 6. PREPARACIÓN DEL ARCHIVO DE SALIDA
                //en cada ejecución quiero recrear de nuevo el fichero.
                if (outputXmlFile.exists())
                {
                    outputXmlFile.delete();
                }

                // 7. CONFIGURACIÓN DEL TRANSFORMADOR
				 // Creamos la factory de transformadores
				 tf= TransformerFactory.newInstance();
				 
				 // Creamos el transformador que convertirá DOM a XML
				 t = tf.newTransformer();
				 
				 // Configuramos la fuente (nuestro documento DOM)
				 ds = new DOMSource(documento);
				 
				 // Configuramos el destino (archivo XML de salida)
				 sr = new StreamResult(new FileWriter(outputXmlFile));
				 
				 // 8. CONFIGURACIÓN DEL DOCTYPE PARA VALIDACIÓN XSD
				 // Como nuestro XML de salida queremos que se valide contra un XSD (alumnos2.xsd)
				 //domImp = documento.getImplementation();
				 
				 // Creamos el DOCTYPE que referencia al XSD
				 //docType = domImp.createDocumentType("doctype", null, xsdOutputXml.getName());
				 
                 
				 // Establecemos la referencia al sistema de validación (XSD)
				 //t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
				 
				 // 9. CONFIGURACIÓN DE PROPIEDADES DE SALIDA XML
				 // Especificamos que queremos generar XML
				 t.setOutputProperty(OutputKeys.METHOD, "xml");
				 
				 // Versión del XML
				 t.setOutputProperty(OutputKeys.VERSION, "1.0");
				 
				 // Codificación de caracteres
				 t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				 
				 // Indentación para que el XML sea legible
				 t.setOutputProperty(OutputKeys.INDENT, "yes");
				 
				 // 10. EJECUCIÓN DE LA TRANSFORMACIÓN
				 // Realizamos la transformación del DOM al archivo XML
				 t.transform(ds, sr);
				 
			} 
			catch (TransformerConfigurationException e) 
			{
				// Error en la configuración del transformador
				System.out.println("Error generando XML");
			} 
			catch (FileNotFoundException e) 
			{
				// Error si no se puede crear o acceder al archivo de salida
				System.out.println("Error en el volcado del árbol DOM sobre el fichero XML");
			} 
			catch (TransformerException e) 
			{
				// Error durante la transformación DOM -> XML
				System.out.println("Error durante en el volcado del árbol DOM sobre el fichero XML");
			} 
			catch (IOException e) 
			{
				// Error de entrada/salida general
				System.out.println("Error durante en el volcado del árbol DOM sobre el fichero XML");
			}

            } catch (ParserConfigurationException e) 
            {
                // Error en la configuración del parser/constructor de documentos
                System.err.println("Error durante la generación del XML: "+e.getMessage());
                
            }
    } // Fin del método generarXmlAlumnos

} // Fin de la clase XmlManager
