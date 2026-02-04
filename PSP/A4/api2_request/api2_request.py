import pandas as pd
import matplotlib as mp
import seaborn as sb
import fastapi
import requests
import os
from dotenv import load_dotenv

# Instanciamos el lector de .env
load_dotenv()

# Cargamos la clave de la api de AEMET
key = os.getenv("API_KEY")
URLBase = os.getenv("URLBase")
endpoint = os.getenv("ENDPOINT")

# Instanciamos la FastAPI
app = fastapi.FastAPI()

# Lectura de muni
data_path = os.getenv("DATA")
if not data_path:
        raise RuntimeError("Environment variable DATA not set; set DATA to the path of the CSV file.")
data = pd.read_csv(data_path)

# Solicitud de muni
@app.get("/solicitud_muni")
def solicitud_muni(municipio: str):
        return buscar_muni_csv(municipio)

# Devolver id_ccaa
def buscar_muni_csv(municipio: str) -> str:
        rows = data[data['NOMBRE'].str.lower() == municipio.lower()]
        if rows.empty:
                raise fastapi.HTTPException(status_code=404, detail="Municipio no encontrado")
        row = rows.iloc[0]
        cpro = str(row['CPRO']).zfill(2)
        cmun = str(row['CMUN']).zfill(3)
        id_ccaa = cpro + cmun
        return recuperar_datos_aemet(id_ccaa)
    
def recuperar_datos_aemet(id_ccaa):
        authString = {"api_key": key}
        # Construimos la URL para la llamada 
        url = URLBase + endpoint + id_ccaa
        print(url)

        # Llamada
        response = requests.get(url, headers=authString)
        print(response.status_code)

        # PRocesamos respuesta
        if response.status_code == 200:
            # CUIDADO: Si la respuesta no es JSON dará error. Se recomienda incluir en un try
            try:
                jsData = response.json()
                urlToCall = jsData['datos']
                prediccion = requests.get(urlToCall, headers=authString)
                print(prediccion.text)
            except:
                print("Respuesta no es JSON")
                print(response.text)
        else:
            print(response.text)
            print(response.reason)
                
# Enviar id a la Api de AEMET
# Recuperar los datos de la muni
# Limpiar y convertir a json las ultimas 6h
# Devolver JSON 
# Recuperar los datos de la muni
# Limpiar y convertir a json las ultimas 6h
# Devolver JSON 

if __name__ == "__main__":
    print(solicitud_muni("Benejúzar"))