import asyncio
import websockets
import time

IP = "127.0.0.1"
PORT = 5005

async def handler(websocket) -> None: 
    data = await websocket.recv()
    print("Cliente: {}".format(data))
    await websocket.send("Te escucho WS")
        
async def main():
    async with websockets.serve(handler, IP, PORT):
        await asyncio.Future() # run forever


asyncio.run(main())