import socket as skt

# tcp_server.py
import socket

HOST = "127.0.0.1"
PORT = 5000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
    server.bind((HOST, PORT))
    server.listen()
    print(f"Servidor TCP escuchando en {HOST}:{PORT}")

    conn, addr = server.accept()
    with conn:
        print("Conectado por:", addr)
        while True:
            data = conn.recv(1024)
            if not data:
                break
            print("Cliente dice:", data.decode())
            conn.sendall(b"Mensaje recibido")
