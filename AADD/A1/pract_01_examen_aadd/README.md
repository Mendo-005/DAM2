# Práctica de Examen: Procesador de Pedidos# Práctica de Examen: Procesador de Pedidos



## Descripción## Descripción



Esta es una aplicación Java de consola desarrollada con Maven, diseñada para procesar un historial de pedidos en formato JSON. La aplicación enriquece estos datos utilizando un catálogo de productos en formato XML y genera un informe de ventas detallado para un producto específico solicitado al usuario.Esta es una aplicación Java de consola desarrollada con Maven, diseñada para procesar un historial de pedidos en formato JSON. La aplicación enriquece estos datos utilizando un catálogo de productos en formato XML y genera un informe de ventas detallado para un producto específico solicitado al usuario.



El proyecto demuestra el manejo de:El proyecto demuestra el manejo de:

* Parseo de ficheros JSON (con Jackson Databind).* Parseo de ficheros JSON (con Jackson Databind).

* Parseo de ficheros XML (con Jackson Dataformat XML).* Parseo de ficheros XML (con Jackson Dataformat XML).

* Entrada de datos por consola.* Entrada de datos por consola.

* Lógica de negocio para filtrado y agregación de datos.* Lógica de negocio para filtrado y agregación de datos.

* Generación de un nuevo fichero JSON como salida.* Generación de un nuevo fichero JSON como salida.

* Configuración de logging con SLF4J y Logback.* Configuración de logging con SLF4J y Logback.



------



## 🚀 Tecnologías Utilizadas## 🚀 Tecnologías Utilizadas



* **Java 11+*** **Java**

* **Maven**: Gestión de dependencias y construcción del proyecto.* **Maven**: Gestión de dependencias y construcción del proyecto.

* **Jackson (Databind, Datatype JSR310)**: Para serializar y deserializar JSON.* **Jackson (Databind, Datatype JSR310)**: Para serializar y deserializar JSON.

* **Jackson (Dataformat XML)**: Para serializar y deserializar XML.* **Jackson (Dataformat XML)**: Para serializar y deserializar XML.

* **SLF4J + Logback**: Para el registro de trazas (logs) de la aplicación.* **SLF4J + Logback**: Para el registro de trazas (logs) de la aplicación.



------



## 📂 Estructura del Proyecto## 📂 Estructura del Proyecto



```Se espera una estructura de proyecto Maven estándar:
pract_01_examen_aadd/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── es/
│   │   │       └── ciudadescolar/
│   │   │           └── Main.java
│   │   └── resources/
│   │       └── Logback.xml
│   └── test/
│       └── java/
├── target/
├── pedidos.json                 # Archivo de entrada con historial de pedidos
├── catalogo_productos.xml       # Catálogo de productos
├── pom.xml                      # Configuración Maven
└── README.md                    # Este archivo
```

---

## 📋 Funcionalidades

### Funcionalidad Principal
1. **Lectura de datos**: Carga el historial de pedidos desde `pedidos.json` y el catálogo de productos desde `catalogo_productos.xml`.
2. **Entrada interactiva**: Solicita al usuario que introduzca el ID de un producto específico.
3. **Procesamiento**: Filtra y agrega los datos de ventas para el producto solicitado.
4. **Generación de informe**: Crea un archivo JSON con el informe detallado de ventas.
5. **Logging**: Registra todas las operaciones importantes en el sistema de logs.

### Datos de Ejemplo
- **17 pedidos** con fechas desde octubre hasta noviembre 2024
- **10 clientes diferentes** (C-001 a C-010)
- **16 productos** en el catálogo (PROD-A a PROD-P)
- **4 estados** de pedido: ENTREGADO, PENDIENTE, PROCESANDO, CANCELADO
- **Múltiples categorías**: Periféricos, Monitores, Audio, Componentes, etc.

---

## 🔧 Configuración y Ejecución

### Prerrequisitos
- Java 11 o superior
- Maven 3.6 o superior

### Pasos para ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd pract_01_examen_aadd
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn exec:java -Dexec.mainClass="es.ciudadescolar.Main"
   ```

   O alternativamente:
   ```bash
   mvn clean package
   java -cp target/classes es.ciudadescolar.Main
   ```

### Uso de la Aplicación

1. Al ejecutar, la aplicación mostrará los productos disponibles del catálogo
2. Introduce el ID del producto que deseas analizar (ej: `PROD-A`)
3. La aplicación generará un informe de ventas en formato JSON
4. Revisa los logs para seguir el proceso de ejecución

---

## 📊 Estructura de Datos

### Archivo pedidos.json
```json
{
  "pedidos": [
    {
      "id_pedido": "P-1001",
      "id_cliente": "C-001",
      "fecha": "2024-10-01",
      "estado": "ENTREGADO",
      "articulos": [
        {
          "id_producto": "PROD-A",
          "cantidad": 2,
          "precio_unitario": 15.50
        }
      ]
    }
  ]
}
```

### Archivo catalogo_productos.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<catalogo>
    <producto id="PROD-A">
        <nombre>Teclado Mecánico RGB</nombre>
        <categoria>Periféricos</categoria>
    </producto>
</catalogo>
```

---

## 🗂️ Catálogo de Productos

| ID | Producto | Categoría | Precio Aprox. |
|---|---|---|---|
| PROD-A | Teclado Mecánico RGB | Periféricos | €15.50 |
| PROD-B | Monitor Ultrawide 34" | Monitores | €200.00 |
| PROD-C | Ratón Óptico Inalámbrico | Periféricos | €10.00 |
| PROD-D | Altavoces Bluetooth 2.1 | Audio | €25.75 |
| PROD-E | Webcam HD 1080p | Periféricos | €89.99 |
| PROD-F | Cable USB-C 2m | Accesorios | €12.30 |
| PROD-G | Tarjeta Gráfica RTX 4070 | Componentes | €450.00 |
| PROD-H | Disco SSD NVMe 1TB | Almacenamiento | €75.00 |
| PROD-I | Memoria RAM DDR4 16GB | Componentes | €33.33 |
| PROD-J | Procesador Intel i7-13700K | Componentes | €120.50 |
| PROD-K | Placa Base ATX Gaming | Componentes | €299.99 |
| PROD-L | Fuente de Alimentación 750W | Componentes | €67.80 |
| PROD-M | Caja Gaming Cristal Templado | Carcasas | €180.00 |
| PROD-N | Hub USB 3.0 7 puertos | Accesorios | €8.25 |
| PROD-O | Auriculares Gaming 7.1 | Audio | €42.50 |
| PROD-P | Mousepad Gaming XXL | Accesorios | €18.90 |

---

## 📝 Logging

La aplicación utiliza SLF4J con Logback para el sistema de logging. La configuración se encuentra en `src/main/resources/Logback.xml`.

Niveles de log configurados:
- **DEBUG**: Información detallada del flujo de ejecución
- **INFO**: Información general de las operaciones
- **WARN**: Advertencias sobre situaciones no críticas
- **ERROR**: Errores que requieren atención

---

## 🧪 Testing

Para ejecutar las pruebas (si están implementadas):
```bash
mvn test
```

Para generar un reporte de cobertura:
```bash
mvn jacoco:report
```

---

## 📈 Posibles Mejoras

- [ ] Implementar validación de entrada más robusta
- [ ] Añadir más formatos de salida (CSV, Excel)
- [ ] Implementar cache para mejorar rendimiento
- [ ] Añadir interfaz gráfica
- [ ] Soporte para múltiples idiomas
- [ ] API REST para consultas remotas
- [ ] Base de datos para persistencia
- [ ] Reportes con gráficos y visualizaciones

---

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

---

## 📄 Licencia

Este proyecto es parte de un ejercicio académico para el módulo de Acceso a Datos (AADD) del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM).

---

## 👥 Autor

**Mario** - Estudiante DAM2 - Ciudad Escolar

---

## 📞 Soporte

Para dudas o problemas relacionados con este proyecto:
- Crear un issue en el repositorio
- Contactar al profesor del módulo AADD

---

*Última actualización: Noviembre 2025*