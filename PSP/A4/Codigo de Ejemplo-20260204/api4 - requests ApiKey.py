import requests
import json
from requests.auth import HTTPBasicAuth

## URLs de partida 
URLBase = 'http://192.168.1.39:8090'

## Autenticación
# En este caso es API KEY. Pasamos el valor como parámetro.
authString = {"api_key": "ClavedelAPI"}
# Llamada incluyendo los datos de autenticación.
resp1 = requests.get(URLBase + "/info_apikey", headers=authString)
# Alternativa: Incluimos los datos de autenticación como tupla. TUPLA, no JSON.
# requests.get(url, auth=('ciudad', 'escolar’))
print(resp1.status_code)
# Procesamos respuesta
if resp1.status_code == 200:
    # CUIDADO: Si la respuesta no es JSON dará error. Se recomienda incluir en un try
    try:
        print(resp1.json())
    except:
        print("Respuesta no es JSON")
        print(resp1.text)
else:
    print(resp1.text)
    print(resp1.reason)

parametros = {"nombre":'Maite', 'edad':17}
resp2 = requests.get(URLBase+"/saluda_apikey", headers=authString, params=parametros)
print(resp2.status_code)
if resp2.status_code == 200:
    try:
        print(resp2.json())
    except:
        print("Respuesta no es JSON")
        print(resp2.text)
else:
    print(resp2.text)
    print(resp2.reason)