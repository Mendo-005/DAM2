import socket as skt

IP = "127.0.0.1"
PORT = 5005

with skt.socket(skt.AF_INET, skt.SOCK_STREAM) as sock:
    sock.bind(IP,PORT)
    sock.listen()
    
    conn, addr = sock.accept()
    with conn:
        print(f"Ciliente conectado desde: {addr[0]}")
        
        while True:
            data = conn.recv(1024)
            midata = data.decode()
            print(midata)
            
            if midata == "-1":
                break    