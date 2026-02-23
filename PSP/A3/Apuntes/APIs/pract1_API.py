import requests
import json
from requests.auth import HTTPBasicAuth
import uvicorn
import fastapi

app = fastapi.FastAPI()

# Constantes para la petición
URL_BASE = ""
ENDPOINT = ""
API_KEY = ""

# Petición a una API
def request():
    url_inicial = f"{URL_BASE}{ENDPOINT}/(etc)"
    headers = {"api_key": API_KEY}
    
    try:
        respuesta = requests.get(url_inicial, headers=headers)
        return respuesta.json()
    except Exception:
        return     

# Endpoint con FastAPI
@app.get("/saludame")
def endpoint(saludo: str):
    if not saludo:
        raise fastapi.HTTPException(status_code=404, detail="ERROR")
    return {"MENSAJE"}

# Servidor Uvicorn
if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=5005)
    #URLBASE/saludame?nombre=Maite&edad=17