import asyncio
import websockets
import time 

IP = "127.0.0.1"
PORT = 5005

async def clienteWS() -> None: 
    uri = "ws://"+IP+":"+str(PORT)    
    async with websockets.connect(uri) as websocket:
        await asyncio.Future()
        await websocket.send("Me escuchas WS")
        
        data = await websocket.recv()
        print(f"Servidor: {format(data)}")
        
asyncio.run(clienteWS())