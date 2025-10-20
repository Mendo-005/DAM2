import threading
import queue
import time

def escribo(q):
  global Terminamos
  while not Terminamos:
    texto = input("Introduce una palabra (-1 para fin): ")
    if texto != "-1":
      q.put(texto)
    else:
      q.put("0x01")
      Terminamos = True
  exit(0)
  
def leo(q):
  global Terminamos
  texto = ''
  while not Terminamos:
      myt = q.get()
      if myt != "0x01":
        texto += myt + ' '
      else:
        Terminamos = True
      print("\nEscrito: {}\n".format(myt))
  exit(0)
      
Terminamos = False
myqueue = queue.Queue()
t1 = threading.Thread(target=escribo, kwargs={'q':myqueue})
t2 = threading.Thread(target=leo, kwargs={'q':myqueue})
t1.start()
t2.start()



