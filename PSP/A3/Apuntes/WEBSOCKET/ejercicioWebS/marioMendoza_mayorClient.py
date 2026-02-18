import asyncio
import json
import websockets
import time 

IP = "127.0.0.1"
PORT = 5005

async def clienteWS() -> None:
    
    nombre = input("Nombre: ")
    numero = int(input("Numero : "))
    
    guess = {
        "nombre": nombre,
        "numero": numero
    }
    guess = json.dumps(guess,)
    
    uri = "ws://"+IP+":"+str(PORT)    
    async with websockets.connect(uri) as websocket:
        await websocket.send(guess)
        
        respuesta = await websocket.recv()
        print("Servidor: {}".format(respuesta))
        
asyncio.run(clienteWS())