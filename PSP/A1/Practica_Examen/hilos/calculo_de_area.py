import threading
import time

# Recurso compartido (donde guardamos el total)
area_total = 0
candado = threading.Lock()

def calcular_figura(nombre, tipo, d1, d2):
    """
    Función genérica que ejecutará cada hilo.
    tipo: 'tri' (triángulo) o 'rect' (rectángulo)
    """
    time.sleep(0.1) # Simulamos que es un cálculo difícil
    
    # 1. Cálculo local (sin bloquear)
    if tipo == 'tri':
        area = (d1 * d2) / 2
    else: # rect
        area = d1 * d2
        
    print(f"Calculado {nombre}: {area} m²")

    # 2. Sección Crítica (Bloqueamos para escribir en la variable compartida)
    global area_total
    with candado:
        area_total += area

# Definimos los trabajos (hilos)
hilos = [
    threading.Thread(target=calcular_figura, args=("Tri. Izq", 'tri', 10, 12)),
    threading.Thread(target=calcular_figura, args=("Rect. Sup", 'rect', 8, 12)),
    threading.Thread(target=calcular_figura, args=("Rect. Der", 'rect', 6, 5)),
    threading.Thread(target=calcular_figura, args=("Tri. Der", 'tri', 2, 5))
]

# Arrancamos y esperamos
print("--- Iniciando hilos ---")
for h in hilos: h.start()
for h in hilos: h.join()

print(f"--- Fin --- \nÁrea Total: {area_total} m²")