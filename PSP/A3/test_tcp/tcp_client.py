# tcp_client.py
import socket

HOST = "127.0.0.1"
PORT = 5000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
    client.connect((HOST, PORT))
    client.sendall(b"Hola servidor!")
    data = client.recv(1024)

print("Servidor responde:", data.decode())
