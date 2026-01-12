import asyncio
import time

timer_run = True

async def main():
    global timer_run
    delay = input("Cuantos segundos debe esperar: ")
    task1 = asyncio.create_task(timer(delay))
    
    print(f"Arranco: {time.strftime('%X')}")
    while timer_run:
        print(".")
        time.sleep(1)
        
    print(f"Acabo: {time.strftime('%X')}")
    await asyncio.Future() # run forever

async def timer(delay):
    global timer_run
    await asyncio.sleep(delay)
    timer_run = False
    

asyncio.run(main())