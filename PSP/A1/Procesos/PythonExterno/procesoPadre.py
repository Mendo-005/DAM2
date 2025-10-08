import subprocess
import sys
import time

procesoExterno = "procesoExterno.py"
intentos = 1

# Ejecutamos el proceso externo (como sub proceso)
def main():
    global intentos
    try:
        while True:
            comando = [sys.executable, procesoExterno]
            resultado = subprocess.run(comando, capture_output=True, text=True, check=True)
            
            # Convertir la salida a entero
            numero_obtenido = int(resultado.stdout.strip())
            print(f"El proceso hijo devolvió: {numero_obtenido}")
            
            # Si el número es 7, terminar el programa
            if numero_obtenido == 7:
                print("Se obtuvo el número 7, terminando programa padre")
                print(f"Se han realizado {intentos} intentos")
                break

            intentos = intentos + 1
            time.sleep(0.5)

    except FileNotFoundError:
        print(f"Error: El archivo '{procesoExterno}' no fue encontrado.")
    except subprocess.CalledProcessError as e:
        print(f"Error al ejecutar el subproceso:")
        print(f"Código de retorno: {e.returncode}")
        print(f"Salida de error: {e.stderr}")
    except ValueError as e:
        print(f"Error al convertir la salida a número: {e}")
    except Exception as e:
        print(f"Ocurrió un error inesperado: {e}")


if __name__ == "__main__":
    main()
