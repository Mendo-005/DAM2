import asyncio
import websockets
import json
import random

IP = "127.0.0.1"
PORT = 5000
numero_secreto = random.randint(1, 20)
juego_activo = True

async def servidorWS(websocket):
    global numero_secreto, juego_activo
    
    async for mensaje in websocket:
        if not juego_activo:
            await websocket.send(json.dumps({"exito": False, "mensaje": "Juego terminado"}))
            continue
        
        datos = json.loads(mensaje)
        print(datos)
        apuesta = datos["apuesta"]
        
        if apuesta == numero_secreto:
            juego_activo = False
            await websocket.send(json.dumps({"exito": True, "mensaje": f"Ganaste Numero: {numero_secreto}"}))
        elif apuesta < numero_secreto:
            await websocket.send(json.dumps({"exito": False, "mensaje": "Mayor"}))
        else:
            await websocket.send(json.dumps({"exito": False, "mensaje": "Menor"}))

async def main():
    print(f"Servidor en ws://{IP}:{PORT}")
    async with websockets.serve(servidorWS, IP, PORT):
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())