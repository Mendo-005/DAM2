import socket

HOST = "127.0.0.1"
PORT = 5005

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as client:
    client.sendto(b"Me escuchas?", (HOST,PORT))
    data, _ = client.recvfrom(1024)
    
    print("Servidor: ", data.decode())