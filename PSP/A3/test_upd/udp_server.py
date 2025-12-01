# udp_server.py
import socket

HOST = "127.0.0.1"
PORT = 6000

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as server:
    server.bind((HOST, PORT))
    print(f"Servidor UDP en {HOST}:{PORT}")

    while True:
        data, addr = server.recvfrom(1024)
        print("Desde", addr, ":", data.decode())
        server.sendto(b"Datagrama recibido", addr)
