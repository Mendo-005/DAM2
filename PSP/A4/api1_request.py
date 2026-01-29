import requests
import json
from requests.auth import HTTPBasicAuth

def process(response):
    print("")
    if response.status_code == 200:
        try:
            jsData = response.json
            print(jsData)
        except:
            print("La respuesta no es  JSON")
            print(response.text)
    else:
        print(response.text)
        print(response.reason)

def main():
    URLBase = "http://192.168.202.219:8090"
    
    response = requests.get(URLBase+"/info")
    process(response)
    

    parametros = { "nombre":"Sara la loca", "edad": 37} 
    response = requests.get(URLBase+ "/saludame", params=parametros)
    process(response)