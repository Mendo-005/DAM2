import asyncio
import time

async def saluda(delay, mensaje):
    
    print("{} 1 at {}".format(mensaje, time.strftime('%X')))
    
    await asyncio.sleep(delay) # cede control a otro awaitable
    
    #time.sleep(delay) # pararía todo el proceso pero el awaitable mantiene control
    #i = input("introduce Valor i para delay:"+str(what)) # No cede control
    
    print("{} 2 at {}".format(mensaje, time.strftime('%X')))
    
async def main():
    
    task1 = asyncio.create_task(saluda(3, 'hello'))
    
    task2 = asyncio.create_task(saluda(1, 'world'))
    
    print(f"Arranco: {time.strftime('%X')}")
    
    # Versión 1: Espero la terminación de las 2 tareas.
    await task2
    await task1
    
    # # Versión 2: Espero que acaben todas las tareas (similar a la 1)
    # #await asyncio.gather(task1, task2)
    # # Versión 3: Ejecuta las tareas y queda en un bucle sin finalización esperando
    # # posibles ejecuciones futuras
    
    
    await asyncio.Future() # run forever
    
asyncio.run(main())