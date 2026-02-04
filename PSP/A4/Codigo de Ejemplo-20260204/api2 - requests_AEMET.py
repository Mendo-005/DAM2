import requests
import json
import os
from dotenv import load_dotenv

## URLs de partida de AEMET
# Referencia: https://opendata.aemet.es/dist/index.html#/predicciones-normalizadas-texto/Predicci%C3%B3n%20CCAA%20ma%C3%B1ana.%20Tiempo%20actual.
URLBase = 'https://opendata.aemet.es/opendata'
endpoint = '/api/prediccion/ccaa/manana/'
ccaa = 'mad'

load_dotenv()
## Autenticación
# 
# En cada caso hay que averiguar el método del API. En el caso de AEMET es API_KEY
# Obtenemos la clave en la web de AEMET
key = os.getenv("API_KEY")

# Al ser API Key la autenticación forma parte de los parámetros de la llamada
authString = {"api_key": key}

# Construimos la URL para la llamada 
url = URLBase + endpoint + ccaa
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