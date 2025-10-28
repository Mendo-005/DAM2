import threading
import time

# Variables globales para almacenar resultados de cada hilo
resultados = {}
lock = threading.Lock()

def areaTri(base, altura):
    return (base * altura) / 2


def areaRect(ancho, alto):
    return ancho * alto


def calcular_triangulo_izquierdo():
    """Hilo para calcular el área del triángulo izquierdo"""
    time.sleep(0.1)  # Simular trabajo computacional
    area = areaTri(10, 12)
    with lock:
        resultados['triangulo_izq'] = area
        print(f"[Hilo 1] Área del triángulo izquierdo: {area} m²")


def calcular_rectangulo_superior():
    """Hilo para calcular el área del rectángulo superior"""
    time.sleep(0.1)  # Simular trabajo computacional
    area = areaRect(8, 12)
    with lock:
        resultados['rectangulo_sup'] = area
        print(f"[Hilo 2] Área del rectángulo superior: {area} m²")


def calcular_rectangulo_derecho():
    """Hilo para calcular el área del rectángulo derecho"""
    time.sleep(0.1)  # Simular trabajo computacional
    area = areaRect(6, 5)
    with lock:
        resultados['rectangulo_der'] = area
        print(f"[Hilo 3] Área del rectángulo derecho: {area} m²")


def calcular_triangulo_derecho():
    """Hilo para calcular el área del triángulo derecho"""
    time.sleep(0.1)  # Simular trabajo computacional
    area = areaTri(2, 5)
    with lock:
        resultados['triangulo_der'] = area
        print(f"[Hilo 4] Área del triángulo derecho: {area} m²")


def calcularAreaPoligono():
    """
    Calcula el área del polígono usando hilos para paralelizar los cálculos
    de cada figura geométrica individual.
    """
    print("Iniciando cálculos en paralelo usando hilos...")
    print()
    
    # Limpiar resultados previos
    global resultados
    resultados.clear()
    
    # Crear hilos para cada cálculo
    hilo1 = threading.Thread(target=calcular_triangulo_izquierdo)
    hilo2 = threading.Thread(target=calcular_rectangulo_superior)
    hilo3 = threading.Thread(target=calcular_rectangulo_derecho)
    hilo4 = threading.Thread(target=calcular_triangulo_derecho)
    
    # Marcar tiempo de inicio
    inicio = time.time()
    
    # Iniciar todos los hilos
    print("Lanzando hilos de cálculo...")
    hilo1.start()
    hilo2.start()
    hilo3.start()
    hilo4.start()
    
    # Esperar a que terminen todos los hilos
    hilo1.join()
    hilo2.join()
    hilo3.join()
    hilo4.join()
    
    # Marcar tiempo de fin
    fin = time.time()
    
    print(f"\nTodos los hilos han terminado en {fin - inicio:.3f} segundos")
    
    # Calcular área total sumando los resultados
    area_total = (resultados['triangulo_izq'] + 
                  resultados['rectangulo_sup'] + 
                  resultados['rectangulo_der'] + 
                  resultados['triangulo_der'])
    
    return area_total


def main():
    """
    Función principal que calcula el área del polígono complejo
    utilizando programación concurrente con hilos para optimizar el rendimiento.
    """
    print("=== CÁLCULO DE ÁREA DE POLÍGONO COMPLEJO CON HILOS ===")
    print("Utilizando programación concurrente para paralelizar los cálculos")
    print()
    
    area_total = calcularAreaPoligono()
    
    print()
    print("=== RESUMEN DE RESULTADOS ===")
    print(f"• Triángulo izquierdo: {resultados['triangulo_izq']} m²")
    print(f"• Rectángulo superior: {resultados['rectangulo_sup']} m²") 
    print(f"• Rectángulo derecho: {resultados['rectangulo_der']} m²")
    print(f"• Triángulo derecho: {resultados['triangulo_der']} m²")
    print("=" * 40)
    print(f"ÁREA TOTAL DEL POLÍGONO: {area_total} m²")
    print("=== CÁLCULO COMPLETADO ===")
    
    return area_total

if __name__ == "__main__":
    main()