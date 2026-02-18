import socket

HOST = "127.0.0.1"
PORT = 5005

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as server:
    server.bind((HOST,PORT))
    
    while True:
        data, addr = server.recvfrom(1024)
        if not data:
            break
        print("Cliente:", data.decode())
        server.sendto(b"Te escucho", addr)