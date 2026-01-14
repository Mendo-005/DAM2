import asyncio
import websockets
IP = "192.168.1.38"
PORT = 9080

async def clienteWS():
    uri = "ws://"+IP+":"+str(PORT)
    while True:
    # Proceso una información y decido enviar al WS
        cad = input("Envío: ")
        # Conecto al WS
        async with websockets.connect(uri) as websocket:
            # Envío la información
            await websocket.send(cad)
        
            # Espero respuesta (opcional)
            devuelto = await websocket.recv()
            print("Recibido: {}".format(devuelto))
            
asyncio.run(clienteWS())