import asyncio
import json
import websockets
IP = "127.0.0.1"
PORT = 5000

async def clienteWS():
    uri = "ws://"+IP+":"+str(PORT)
    while True:
    # Proceso una información y decido enviar al WS
        id = input("ID: ")
        apuesta = int(input("APUESTA: "))
        
        datos_diccionario = {
            "id": id,
            "apuesta":apuesta
        }
        mensaje_json = json.dumps(datos_diccionario)
        
        # Conecto al WS
        async with websockets.connect(uri) as websocket:
            # Envío la información
            await websocket.send(mensaje_json)
        
            # Espero respuesta (opcional)
            devuelto = await websocket.recv()
            print("Recibido: {}".format(devuelto))
            
asyncio.run(clienteWS())