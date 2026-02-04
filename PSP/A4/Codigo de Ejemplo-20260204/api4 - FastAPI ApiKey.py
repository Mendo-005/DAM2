from fastapi import FastAPI, Security, HTTPException, status, Depends
from fastapi.security import APIKeyHeader
import uvicorn
import json

api_key = APIKeyHeader(name="api_key")
def comprueba(api_key_header: str = Security(api_key)):
    if api_key_header == "ClavedelAPI":
        return True
    else:
        # From FastAPI 
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Usuario no autenticado correctamente",
            headers={"WWW-Authenticate": "Basic"},
        )
    
app = FastAPI(dependencies=[Depends(comprueba)])

# Endpoints 
# Formato de la llamada URLBASE/info_apikey
@app.get("/info_apikey")
def read_root(ok = Depends(comprueba)):
    return({"contenido": "API autenticado ", "autor": "DAM2", "recibo": api_key})

@app.get("/saluda_apikey")
# Formato de la llamada URLBASE/saluda_apikey?nombre=Maite&edad=17
def saludo(nombre: str, edad: int, ok = Depends(comprueba)):
    return {"contenido": "API Validado {}. Tienes {} años".format(nombre, edad), "autor": "Agustin"}


if __name__ == "__main__":
    uvicorn.run(app, host="192.168.1.39", port=8090)

