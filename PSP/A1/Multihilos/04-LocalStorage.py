import threading
import random

def mostrar(d):
  try:
    val = d.valor
  except AttributeError:
    print(f"{threading.current_thread().name}: Aún no inicializado")
  else:
   print(f"{threading.current_thread().name}: {val}")

def hilo(dato):
  mostrar(dato)
  dato.valor = random.randint(1, 100)
  mostrar(dato)

def main():   
  for i in range(3):
    t = threading.Thread(target=hilo, args=(dato,))
    t.start()

dato = threading.local() #variable con instancia local en cada hilo

if __name__ == '__main__':
  main()