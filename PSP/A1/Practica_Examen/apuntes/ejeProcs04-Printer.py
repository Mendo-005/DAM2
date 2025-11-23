import time
from multiprocessing import Process
import os

def imprime(file):
  time.sleep(5)
  f = open(file, "r")
  texto = f.read()
  f.close()
  print("---------")
  print(texto)
  print("---------")
  print("Salgo del proceso hijo {}".format(os.getpid()))
  os._exit(0)

def main():
  #os.system('cls')
  fi = "fichero"
  counter = 1
  file = ""
  linea1 = input("Escribe la primera línea: ")
  while linea1 != '-1':
    if linea1 != '-P':
      file = fi + str(counter) + ".txt"
      with open(file, "a") as f:
        f.write(linea1)
    else:
      #counter += 1
      wp = Process(target=imprime, args=(file,))
      wp.start()
      #wp.join()
    linea1 = input("Escribe otra línea: ")
  print("Salgo del proceso padre {}".format(os.getpid()))

if __name__ == '__main__':
  main()

