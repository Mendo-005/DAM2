# echo_server.py
import socket

HOST = "127.0.0.1"
PORT = 7000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
    server.bind((HOST, PORT))
    server.listen()
    print("Eco-servidor listo.")

    conn, addr = server.accept()
    print("Conectado:", addr)

    with conn:
        while True:
            msg = conn.recv(1024)
            if not msg:
                break
            conn.sendall(msg)  # eco
