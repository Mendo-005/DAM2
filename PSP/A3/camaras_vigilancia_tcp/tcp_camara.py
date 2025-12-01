import random
import socket
import time

HOST = "127.0.0.1"
PORT = 5000

objetos = ['-', 'persona', 'coche', 'moto', 'bicicleta']

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
    client.connect((HOST, PORT))

    for i in range(10):

        # Generar índice seguro
        obj = random.randint(-10, 4)
        if obj <= 0:
            obj = 0

        objetoDetectado = objetos[obj]

        # Enviar mensaje como bytes
        client.sendall(objetoDetectado.encode())
        print(f"Mensaje ({objetoDetectado}) enviado")

        time.sleep(0.5)
    
    print("Conexion cerrada")
        
