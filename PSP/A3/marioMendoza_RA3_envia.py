import asyncio
import json
import websockets

IP = "127.0.0.1"
PORT = 5005

async def clienteWS() -> None: 
    
        while True:
            categoria = input("Categoria: ")
            premio = input("Premio: ")
            
            guess = {
                "categoria": categoria,
                "premio": premio
            }            
            guess = json.dumps(guess)
            print(guess)
            
            uri = "ws://"+IP+":"+str(PORT)    
            async with websockets.connect(uri) as websocket:
                await websocket.send(guess,)

                data = await websocket.recv()
                data_string = format(data)
                
                print(data)
                if data_string == "ok" :
                    print(f"Servidor: {format(data)}")
                elif data_string == "KO" :
                    await websocket.send(guess,)  
                
asyncio.run(clienteWS())