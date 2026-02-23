import requests
import json
from requests.auth import HTTPBasicAuth
import uvicorn
import fastapi

app = fastapi.FastAPI()

# Constantes para la petición
URL_BASE = "https://swapi.dev/api/people/" 

# Petición a una API
def request(id_personaje):
    url_inicial = f"{URL_BASE}{id_personaje}"
    print(url_inicial)
    try:
        respuesta = requests.get(url_inicial)
        print(respuesta)
        return respuesta.json()
    except Exception:
        return
    
def respuesta(respuesta):
    print(respuesta["homeworld"])
    ip_planeta = respuesta["homeworld"]
    try:
        respuesta_p = requests.get(ip_planeta)
        rep =respuesta_p.json()
        
        mensaje_de_vuelta = {
            "nombre completo": respuesta["name"],
            "color del pelo": respuesta["hair_color"],
            "color de ojos": respuesta["eye_color"],
            "nombre planeta": rep["name"],
            "clima del planeta": rep["climate"]
        }
        mensaje_de_vuelta = json.dumps(mensaje_de_vuelta)
        return mensaje_de_vuelta
    except Exception:
        return    

# Endpoint con FastAPI
@app.get("/examen_ra4")
def endpoint(id_personaje: int):
    if not id_personaje:
        raise fastapi.HTTPException(status_code=404, detail="Personaje no encontrado")
    
    print(id_personaje)
    requ = request(id_personaje)
    print(requ)
    
    mensaje_de_vuelta = respuesta(requ)
    return mensaje_de_vuelta

# Servidor Uvicorn
if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)
    #URLBASE/saludame?nombre=Maite&edad=17