import os
import json
import fastapi
import matplotlib as plt
import requests
import pandas as pd
from datetime import datetime
from dotenv import load_dotenv
import uvicorn

from api2_request import grafica


load_dotenv()

API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZW5kb3phY2hhcGFycm83M0BnbWFpbC5jb20iLCJqdGkiOiIwNDAyY2I2MS0wYWVlLTQxYzQtYTIzOS0zYmU4ZDkzNjE3YjgiLCJpc3MiOiJBRU1FVCIsImlhdCI6MTc3MDIwMjExMSwidXNlcklkIjoiMDQwMmNiNjEtMGFlZS00MWM0LWEyMzktM2JlOGQ5MzYxN2I4Iiwicm9sZSI6IiJ9.JUvFNU0SaHSBOC-mzaVGF7UwdwF3NEDe-Yg5xONw1BA"
DATA_PATH = "./api2_request/data/20codmun.csv"
URL_BASE = 'https://opendata.aemet.es/opendata'
ENDPOINT= "/api/prediccion/especifica/municipio/horaria/"
JSON_FILE="aemt_respond.json"

app = fastapi.FastAPI()

# Pandas-CSV
def obtener_id_municipio(nombre_municipio):
    df = pd.read_csv(DATA_PATH)
    filas = df[df['NOMBRE'].str.lower() == nombre_municipio.lower()]
    
    if filas.empty:
        return None
    
    fila = filas.iloc[0]
    return f"{int(fila['CPRO']):0>2}{int(fila['CMUN']):0>3}"

def descargar_datos_aemet(id_muni):
    url_inicial = f"{URL_BASE}{ENDPOINT}{id_muni}"
    headers = {"api_key": API_KEY}

    try:
        res = requests.get(url_inicial, headers=headers)
        res.raise_for_status()
        
        url_datos = res.json().get('datos')
        if not url_datos:
            return None
            
        res_final = requests.get(url_datos)
        return res_final.json() if res_final.status_code == 200 else None
    except Exception:
        return None

def extraer_valor(lista_datos, hora, clave='value'):
    item = next((x for x in lista_datos if x.get('periodo') == hora), {})
    valor = item.get(clave, "0")
    return valor[0] if isinstance(valor, list) else valor

def procesar_prediccion(raw_data):
    if not raw_data:
        return None

    origen = raw_data[0]
    dias = origen['prediccion']['dia']
    
    resultado = {
        "Municipio": origen['nombre'],
        "Provincia": origen['provincia'],
        "Pronostico": []
    }

    hora_actual = datetime.now().hour

    for i in range(1, 7):
        hora_target = hora_actual + i
        dia_idx = 0 if hora_target < 24 else 1
        hora_str = f"{hora_target if dia_idx == 0 else hora_target - 24:02}"

        try:
            dia_datos = dias[dia_idx]
        except IndexError:
            break

        resultado["Pronostico"].append({
            "Hora": f"{hora_str}:00",
            "Cielo": extraer_valor(dia_datos['estadoCielo'], hora_str, 'descripcion'),
            "Precipitacion": f"{extraer_valor(dia_datos['precipitacion'], hora_str)} mm",
            "Temperatura": f"{extraer_valor(dia_datos['temperatura'], hora_str)} ºC",
            "Dirección del viento": extraer_valor(dia_datos.get('vientoAndRachaMax', []), hora_str, 'direccion'),
            "Viento": f"{extraer_valor(dia_datos['vientoAndRachaMax'], hora_str, 'velocidad')} km/h"
        })

    return resultado

@app.get("/peticion")
#http://192.168.56.1:5005/peticion?municipio=Benejúzar
def devolver_resultado(municipio: str):
    id_muni = obtener_id_municipio(municipio)
    if not id_muni:
        raise fastapi.HTTPException(status_code=404, detail="Municipio no encontrado")

    raw_data = descargar_datos_aemet(id_muni)
    datos_limpios = procesar_prediccion(raw_data)
    if not datos_limpios:
        raise fastapi.HTTPException(status_code=502, detail="Error al obtener o procesar datos de AEMET")
    
    grafica.grafica(datos_limpios)
    return datos_limpios
    
if __name__ == "__main__":
    uvicorn.run(app, host="192.168.56.1", port=5005)
    
