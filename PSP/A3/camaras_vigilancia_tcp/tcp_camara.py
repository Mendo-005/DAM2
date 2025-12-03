import json
import random
import socket
import time

HOST = "127.0.0.1"
PORT = 5000
NOMBRE = "Mario Mendoza"

objetos = ['-', 'persona', 'coche', 'moto', 'bicicleta']

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
    client.connect((HOST, PORT))

    for i in range(10):

        # Generar índice seguro
        obj = random.randint(-10, 4)
        if obj <= 0:
            obj = 0

        datos_diccionario = {
                "nombre": NOMBRE,
                "info": objetos[obj]
        }

        # Convertir diccionario a String JSON
        mensaje_json = json.dumps(datos_diccionario)

        # Enviar mensaje
        client.sendall(mensaje_json.encode())
        print(f"Enviado: {mensaje_json}")

        # ESCUCHAR RESPUESTA 
        respuesta = client.recv(1024) 
        print(f"Servidor respondió: {respuesta.decode()}")

        time.sleep(0.5)
    
    print("Conexion cerrada correctamente")