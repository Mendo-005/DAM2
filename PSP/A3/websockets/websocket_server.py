import asyncio
import websockets
IP = "192.168.1.38"
PORT = 9080

async def servidorWS(websocket):

    # Se pone a la escucha y recibe una cadena
    cad = await websocket.recv()
    
    # Gestiona los datos recibidos
    print("Recibido {}".format(cad))
    ack = "Has enviado {}".format(cad)
    
    # Devuelve un valor al cliente (no es obligatorio)
    await websocket.send(ack)
    print("--> {}".format(ack))

async def main():
    async with websockets.serve(servidorWS, IP, PORT):
        await asyncio.Future() # run forever

if __name__ == "__main__":
    asyncio.run(main())