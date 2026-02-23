import asyncio
import websockets
import json

IP = "127.0.0.1"
PORT = 5005

async def handler(websocket) -> None: 
    respuesta = await websocket.recv()
    data = json.loads(respuesta)
    
    print(data)
    
    if "categoria" and "premios" in data:
        await websocket.send("ok")
    else:
        await websocket.send("KO")

async def main():
    async with websockets.serve(handler, IP, PORT):
        await asyncio.Future() # run forever

asyncio.run(main())