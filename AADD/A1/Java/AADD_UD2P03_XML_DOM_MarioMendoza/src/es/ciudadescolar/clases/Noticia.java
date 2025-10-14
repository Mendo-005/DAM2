package es.ciudadescolar.clases;

public class Noticia {
    
    /**
     * Atributos de la clase
     */
    private int secuencial;

    private String title;
    
    private String creator;
    
    private String description; 
    

    /*
     * Getters y Setters
     */
    public int getSecuencial() 
    {
        return secuencial;
    }

    public void setSecuencial(int secuencial) 
    {
        this.secuencial = secuencial;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getCreator() 
    {
        return creator;
    }

    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    /*
     * Salida toString 
     */
    @Override
    public String toString() {
        return "Noticia [secuencial=" + secuencial + ", title=" + title + ", creator=" + creator + ", description="
                + description + "]";
    }
     
}
