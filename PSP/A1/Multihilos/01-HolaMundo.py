import threading
import time

def hilo(i):
    print ('Soy el hilo: ', i)
    time.sleep(10)

l = list()
for i in range(5):
  t = threading.Thread(target=hilo, args=(i,))
  l.append(t)
  t.start()
print(threading.current_thread())
for t in threading.enumerate():
  print(t.name)
