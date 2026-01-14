import asyncio
import time

timer_run = False

async def main():
    global timer_run

    delay = int(input("Cuantos segundos debe esperar: "))
    timer_run = True

    task1 = asyncio.create_task(timer(delay))

    print(f"Arranco: {time.strftime('%X')}")

    while timer_run:
        print(".")
        await asyncio.sleep(0.5)

    await task1
    print(f"Acabo: {time.strftime('%X')}")

async def timer(delay):
    global timer_run
    await asyncio.sleep(delay)
    timer_run = False

asyncio.run(main())
