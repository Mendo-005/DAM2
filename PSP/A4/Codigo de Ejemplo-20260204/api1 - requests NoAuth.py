import requests
import json
from requests.auth import HTTPBasicAuth

def procesa(response):
    print(response.status_code)
    # Procesamos respuesta
    if response.status_code == 200:
        #print(response.content)
        # CUIDADO: Si la respuesta no es JSON dará error. Se recomienda incluir en un try
        try:
            jsData = response.json()
            print(jsData)
        except:
            print("Respuesta no es JSON")
            print(response.text)
    else:
        print(response.text)
        print(response.reason)


def main():
## URLs de partida 
    URLBase = 'http://192.168.1.39:8090'

    # Llamada sencilla sin parámetros de entrada    
    response = requests.get(URLBase+"/info")
    procesa(response)    

    # Parámetros tipo query
    # Formato de la llamada URLBASE/saludame?nombre=Eva&edad=15
    parametros= {"nombre":"Eva", "edad": 15}
    response = requests.get(URLBase+"/saludame",params=parametros)
    procesa(response)    

    # Parámetros tipo URL
    # Formato de la llamada URLBASE/saludamemas/Maite/17
    nombre = "Maite"
    edad = 17
    response = requests.get(URLBase+"/saludamemas/"+nombre+"/"+str(edad))
    procesa(response)    

    # Formato de la llamada URLBASE/addint?item=34
    parametros = {"item":"34"}
    response = requests.put(URLBase+"/addint",params=parametros)
    procesa(response)    

    # Parámetros en el Body
    # Formato de la llamada URLBASE/addjson/ y body con el siguiente JSON {"nombre":"Maite", "edad":56}
    body = json.dumps({"nombre":"Maite", "edad":56})
    response = requests.post(URLBase+"/addjson", data=body)
    procesa(response)

    # Llamada a get sin parámetros, devuelve una lista.
    response = requests.get(URLBase+"/getlist")
    procesa(response)    

    print("API completamente procesado")

if __name__ == "__main__":
    main()