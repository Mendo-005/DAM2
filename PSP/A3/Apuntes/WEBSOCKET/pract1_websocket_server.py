import asyncio
import json
import random
import websockets
import time

IP = "127.0.0.1"
PORT = 5005

num_random = random.randint(1, 10)

async def handler(websocket) -> None: 
    global num_random
    
    respuesta = await websocket.recv()
    data = json.loads(respuesta)
    
    print(data)
    jugador = data["nombre"]
    apuesta = data["numero"]
    
    if apuesta > num_random:
        apuesta = num_random
        await websocket.send(f"{jugador}, tu numero es el mayor hasta ahora")
    elif apuesta < num_random:
        await websocket.send(f"{jugador}, tu numero no es menor que el que tengo. Lo rechazo")
    
    
    
async def main():
    async with websockets.serve(handler, IP, PORT):
        await asyncio.Future() # run forever


asyncio.run(main())