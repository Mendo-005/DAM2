# udp_client.py
import socket

HOST = "127.0.0.1"
PORT = 6000

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as client:
    client.sendto(b"Hola desde UDP!", (HOST, PORT))
    data, _ = client.recvfrom(1024)

print("Respuesta:", data.decode())
