import json
import random
import socket
import time

HOST = "192.168.202.64"   
PORT = 5005               
NOMBRE = "Mario Mendoza"

objetos = ['-', 'persona', 'coche', 'moto', 'bicicleta', 'Jose Salas']

# Crear socket UDP
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Evitar bloqueos si no hay respuesta
client.settimeout(1)

for i in range(10):

    # Índice seguro
    obj = random.randint(-10, 4)
    if obj <= 0:
        obj = 0

    datos_diccionario = {
        "nombre": NOMBRE,
        "info": objetos[obj]
    }

    mensaje_json = json.dumps(datos_diccionario)

    # Enviar mensaje por UDP
    client.sendto(mensaje_json.encode(), (HOST, PORT))
    print(f"Enviado a {HOST}:{PORT} → {mensaje_json}")

    # Recibir respuesta del servidor
    try:
        respuesta, addr = client.recvfrom(1024)
        print(f"Servidor respondió desde {addr}: {respuesta.decode()}")
    except socket.timeout:
        print("No se recibió respuesta (timeout)")

    time.sleep(0.5)

print("Cliente finalizado")
client.close()
