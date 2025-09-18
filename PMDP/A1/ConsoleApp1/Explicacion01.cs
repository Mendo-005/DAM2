/*
 * EXPLICACIÓN COMPLETA DE CONCEPTOS C# PARA PROGRAMADORES JAVA
 * ============================================================
 * 
 * Este archivo explica todos los conceptos utilizados en Program.cs y Persona.cs
 * con comparaciones entre C# y Java para facilitar el aprendizaje.
 */

using System;

namespace MyApp
{
    // ========================================
    // 1. CONCEPTOS BÁSICOS: NAMESPACES Y CLASES
    // ========================================
    
    /*
     * NAMESPACE (Espacio de nombres)
     * ------------------------------
     * En C#: namespace MyApp { }
     * En Java: package myapp;
     * 
     * Diferencias:
     * - C# usa 'namespace' en lugar de 'package'
     * - En C# el namespace envuelve todo el código con llaves { }
     * - En Java el package se declara al inicio del archivo
     */

    internal class Explicacion01
    {
        // ========================================
        // 2. MODIFICADORES DE ACCESO
        // ========================================
        
        /*
         * MODIFICADORES DE ACCESO EN C# vs JAVA:
         * 
         * C#                    | Java
         * ---------------------|---------------------
         * public               | public
         * private              | private
         * protected            | protected
         * internal             | package-private (sin modificador)
         * protected internal   | (no existe equivalente directo)
         * 
         * 'internal' significa que es accesible dentro del mismo ensamblado (assembly)
         * similar al package-private en Java pero a nivel de ensamblado.
         */

        // ========================================
        // 3. MÉTODO MAIN - PUNTO DE ENTRADA
        // ========================================
        
        static void Main(string[] args)
        {
            /*
             * MÉTODO MAIN:
             * 
             * C#: static void Main(string[] args)
             * Java: public static void main(String[] args)
             * 
             * Diferencias:
             * - En C# no necesita ser 'public' (puede ser private o internal)
             * - 'string' en C# es equivalente a 'String' en Java
             * - Los arrays se declaran con [] después del tipo
             */

            Console.WriteLine("=== EXPLICACIÓN DE CONCEPTOS C# ===\n");

            // ========================================
            // 4. GENERACIÓN DE NÚMEROS ALEATORIOS
            // ========================================
            
            Console.WriteLine("1. NÚMEROS ALEATORIOS:");
            Console.WriteLine("---------------------");
            
            /*
             * RANDOM EN C# vs JAVA:
             * 
             * C#: Random rng = new Random();
             * Java: Random rng = new Random();
             * 
             * Muy similar, pero métodos diferentes:
             * C#: rng.Next(1, 101)     // Genera número entre 1 y 100 (101 es exclusivo)
             * Java: rng.nextInt(100) + 1  // Genera 0-99, luego suma 1
             */
            
            Random rng = new Random();
            int numeroAleatorio = rng.Next(1, 101);
            Console.WriteLine($"Número aleatorio entre 1 y 100: {numeroAleatorio}");
            
            // Ejemplo de diferentes rangos
            Console.WriteLine($"Número entre 1 y 10: {rng.Next(1, 11)}");
            Console.WriteLine($"Número entre 0 y 999: {rng.Next(1000)}");
            Console.WriteLine();

            // ========================================
            // 5. ENTRADA Y SALIDA DE CONSOLA
            // ========================================
            
            Console.WriteLine("2. ENTRADA Y SALIDA DE CONSOLA:");
            Console.WriteLine("-------------------------------");
            
            /*
             * ENTRADA/SALIDA EN C# vs JAVA:
             * 
             * SALIDA:
             * C#: Console.WriteLine("texto");
             * Java: System.out.println("texto");
             * 
             * ENTRADA:
             * C#: string input = Console.ReadLine();
             * Java: Scanner scanner = new Scanner(System.in);
             *       String input = scanner.nextLine();
             * 
             * C# es más simple para entrada básica de consola.
             */
            
            Console.WriteLine("Ejemplo de salida con Console.WriteLine");
            Console.Write("Ejemplo de salida sin salto de línea con Console.Write");
            Console.WriteLine(" - y aquí continúa en la misma línea\n");

            // Simulamos entrada (en lugar de pedirla realmente)
            string respuestaSimulada = "5";
            Console.WriteLine($"Simulando entrada del usuario: {respuestaSimulada}");

            // ========================================
            // 6. CONVERSIÓN DE TIPOS (PARSING)
            // ========================================
            
            Console.WriteLine("\n3. CONVERSIÓN DE TIPOS:");
            Console.WriteLine("-----------------------");
            
            /*
             * CONVERSIÓN STRING A INT:
             * 
             * C#: int numero = int.Parse(cadena);
             * Java: int numero = Integer.parseInt(cadena);
             * 
             * C# también tiene:
             * - int.TryParse(cadena, out int resultado) // Más seguro, no lanza excepción
             * - Convert.ToInt32(cadena)
             */
            
            int tamanno = int.Parse(respuestaSimulada);
            Console.WriteLine($"String '{respuestaSimulada}' convertido a int: {tamanno}");
            
            // Ejemplo de conversión segura
            string texto = "123";
            if (int.TryParse(texto, out int numeroConvertido))
            {
                Console.WriteLine($"Conversión exitosa: {numeroConvertido}");
            }
            else
            {
                Console.WriteLine("Error en la conversión");
            }
            Console.WriteLine();

            // ========================================
            // 7. INSTANCIACIÓN DE OBJETOS Y PROPIEDADES
            // ========================================
            
            Console.WriteLine("4. OBJETOS Y PROPIEDADES:");
            Console.WriteLine("-------------------------");
            
            /*
             * CREACIÓN DE OBJETOS:
             * 
             * C#: Persona p = new Persona(20);
             * Java: Persona p = new Persona(20);
             * 
             * Idéntico en sintaxis básica.
             * 
             * PROPIEDADES vs MÉTODOS GETTER:
             * 
             * C#: p.edad (acceso directo a propiedad)
             * Java: p.getEdad() (método getter tradicional)
             * 
             * Las propiedades en C# son más elegantes y permiten
             * encapsulación sin necesidad de métodos explícitos.
             */
            
            Persona p = new Persona(20);
            Console.WriteLine($"Edad de la persona: {p.edad}");
            
            // Ejemplo con múltiples personas
            Persona p1 = new Persona(25);
            Persona p2 = new Persona(30);
            Console.WriteLine($"Persona 1: {p1.edad} años");
            Console.WriteLine($"Persona 2: {p2.edad} años");
            Console.WriteLine();

            // ========================================
            // 8. ARRAYS UNIDIMENSIONALES
            // ========================================
            
            Console.WriteLine("5. ARRAYS UNIDIMENSIONALES:");
            Console.WriteLine("---------------------------");
            
            /*
             * DECLARACIÓN DE ARRAYS:
             * 
             * C#: int[] notas = new int[3];
             * Java: int[] notas = new int[3];
             *       o int notas[] = new int[3];
             * 
             * C# prefiere la sintaxis con [] después del tipo.
             * 
             * ACCESO A ELEMENTOS:
             * Idéntico en ambos: array[indice]
             */
            
            int[] notas = new int[3];
            notas[0] = 8;  // Primera nota
            notas[1] = 7;  // Segunda nota  
            notas[2] = 10; // Tercera nota
            
            Console.WriteLine("Array de notas:");
            for (int i = 0; i < notas.Length; i++)
            {
                Console.WriteLine($"Nota {i + 1}: {notas[i]}");
            }
            
            // Inicialización directa de arrays
            int[] otrasNotas = { 9, 8, 7, 10 };
            Console.WriteLine("\nOtro array inicializado directamente:");
            foreach (int nota in otrasNotas)  // foreach similar a Java
            {
                Console.WriteLine($"Nota: {nota}");
            }
            Console.WriteLine();

            // ========================================
            // 9. ARRAYS BIDIMENSIONALES (MATRICES)
            // ========================================
            
            Console.WriteLine("6. ARRAYS BIDIMENSIONALES (MATRICES):");
            Console.WriteLine("-------------------------------------");
            
            /*
             * ARRAYS 2D EN C# vs JAVA:
             * 
             * C#: int[,] matriz = new int[filas, columnas];
             * Java: int[][] matriz = new int[filas][columnas];
             * 
             * GRAN DIFERENCIA:
             * - C# usa una coma para separar dimensiones: [,]
             * - Java usa arrays de arrays: [][]
             * 
             * C# tiene arrays verdaderamente multidimensionales,
             * Java tiene arrays de arrays (jagged arrays).
             */
            
            // Crear matriz cuadrada del tamaño especificado
            int[,] numerosInicializados = new int[tamanno, tamanno];
            
            Console.WriteLine($"Creando matriz de {tamanno}x{tamanno} con números aleatorios:");
            
            // Llenar la matriz con números aleatorios
            for (int i = 0; i < numerosInicializados.GetLength(0); i++)  // GetLength(0) = número de filas
            {
                for (int j = 0; j < numerosInicializados.GetLength(1); j++)  // GetLength(1) = número de columnas
                {
                    numerosInicializados[i, j] = rng.Next(1, 101);
                }
            }
            
            // Mostrar la matriz
            Console.WriteLine("Matriz generada:");
            for (int i = 0; i < numerosInicializados.GetLength(0); i++)
            {
                for (int j = 0; j < numerosInicializados.GetLength(1); j++)
                {
                    Console.Write($"{numerosInicializados[i, j],4}");  // {valor,ancho} para formateo
                }
                Console.WriteLine();  // Nueva línea después de cada fila
            }
            Console.WriteLine();

            // ========================================
            // 10. MÉTODOS DE ARRAYS MULTIDIMENSIONALES
            // ========================================
            
            Console.WriteLine("7. MÉTODOS DE ARRAYS MULTIDIMENSIONALES:");
            Console.WriteLine("----------------------------------------");
            
            /*
             * MÉTODOS ESPECÍFICOS DE C#:
             * 
             * GetLength(dimensión): Obtiene la longitud de una dimensión específica
             * - GetLength(0): número de filas
             * - GetLength(1): número de columnas
             * 
             * En Java sería:
             * - matriz.length: número de filas
             * - matriz[0].length: número de columnas (asumiendo que no es jagged)
             */
            
            Console.WriteLine($"Número de filas: {numerosInicializados.GetLength(0)}");
            Console.WriteLine($"Número de columnas: {numerosInicializados.GetLength(1)}");
            Console.WriteLine($"Total de elementos: {numerosInicializados.Length}");
            Console.WriteLine();

            // ========================================
            // 11. BUCLES FOR vs FOREACH
            // ========================================
            
            Console.WriteLine("8. TIPOS DE BUCLES:");
            Console.WriteLine("-------------------");
            
            /*
             * BUCLE FOR TRADICIONAL:
             * Idéntico en C# y Java:
             * for (inicialización; condición; incremento) { }
             * 
             * BUCLE FOREACH:
             * C#: foreach (tipo variable in colección) { }
             * Java: for (tipo variable : colección) { }
             * 
             * C# usa 'in', Java usa ':'
             */
            
            Console.WriteLine("Bucle for tradicional con índices:");
            for (int i = 0; i < notas.Length; i++)
            {
                Console.WriteLine($"Índice {i}: valor {notas[i]}");
            }
            
            Console.WriteLine("\nBucle foreach (más simple cuando no necesitas el índice):");
            foreach (int nota in notas)
            {
                Console.WriteLine($"Valor: {nota}");
            }
            Console.WriteLine();

            // ========================================
            // 12. INTERPOLACIÓN DE STRINGS
            // ========================================
            
            Console.WriteLine("9. INTERPOLACIÓN DE STRINGS:");
            Console.WriteLine("----------------------------");
            
            /*
             * FORMATEO DE STRINGS:
             * 
             * C# (interpolación): $"Texto {variable}"
             * C# (tradicional): "Texto " + variable
             * C# (format): string.Format("Texto {0}", variable)
             * 
             * Java: "Texto " + variable
             * Java (format): String.format("Texto %d", variable)
             * 
             * La interpolación con $ es una característica moderna de C#
             * que hace el código más legible.
             */
            
            string nombre = "Juan";
            int edad = 25;
            
            // Diferentes formas de formatear strings
            Console.WriteLine($"Interpolación: Hola {nombre}, tienes {edad} años");
            Console.WriteLine("Concatenación: Hola " + nombre + ", tienes " + edad + " años");
            Console.WriteLine(string.Format("Format: Hola {0}, tienes {1} años", nombre, edad));
            Console.WriteLine();

            // ========================================
            // 13. RESUMEN DE DIFERENCIAS PRINCIPALES
            // ========================================
            
            Console.WriteLine("10. RESUMEN DE DIFERENCIAS C# vs JAVA:");
            Console.WriteLine("=======================================");
            Console.WriteLine("• namespace vs package");
            Console.WriteLine("• string vs String");
            Console.WriteLine("• Console.WriteLine vs System.out.println");
            Console.WriteLine("• Console.ReadLine() vs Scanner");
            Console.WriteLine("• int.Parse() vs Integer.parseInt()");
            Console.WriteLine("• Propiedades vs métodos getter/setter");
            Console.WriteLine("• int[,] vs int[][] para matrices");
            Console.WriteLine("• GetLength() vs .length");
            Console.WriteLine("• foreach...in vs for...:");
            Console.WriteLine("• $\"interpolación\" vs concatenación");
            Console.WriteLine("• internal vs package-private");
        }
    }

    // ========================================
    // 14. EJEMPLO DE CLASE CON PROPIEDADES
    // ========================================
    
    /*
     * PROPIEDADES EN C# vs JAVA:
     * 
     * Esta clase demuestra el uso de propiedades de solo lectura en C#.
     * En Java necesitarías un campo privado y un método getter público.
     */
    public class Persona
    {
        /*
         * PROPIEDAD DE SOLO LECTURA:
         * 
         * C#: public int edad { get; }
         * 
         * Equivalente en Java:
         * private int edad;
         * public int getEdad() { return edad; }
         * 
         * La propiedad { get; } crea automáticamente:
         * - Un campo privado backing
         * - Un getter público
         * - Solo permite asignación en el constructor
         */
        public int edad { get; }

        /*
         * CONSTRUCTOR:
         * 
         * C#: public Persona(int edad) { this.edad = edad; }
         * Java: public Persona(int edad) { this.edad = edad; }
         * 
         * Sintaxis idéntica para constructores básicos.
         */
        public Persona(int edad)
        {
            this.edad = edad;
        }

        /*
         * EJEMPLO DE OTROS TIPOS DE PROPIEDADES:
         * 
         * Propiedad de lectura/escritura:
         * public string Nombre { get; set; }
         * 
         * Propiedad con lógica personalizada:
         * private string _apellido;
         * public string Apellido 
         * { 
         *     get { return _apellido; } 
         *     set { _apellido = value?.ToUpper(); } 
         * }
         * 
         * Propiedad calculada:
         * public string NombreCompleto => $"{Nombre} {Apellido}";
         */
    }
}

/*
 * CONCLUSIÓN:
 * ===========
 * 
 * C# y Java son muy similares en sintaxis básica, pero C# tiene características
 * más modernas como:
 * - Propiedades automáticas
 * - Interpolación de strings
 * - Arrays verdaderamente multidimensionales
 * - Entrada de consola más simple
 * 
 * Como programador Java, te sentirás cómodo con C# rápidamente,
 * pero aprovecha estas características modernas para escribir código más limpio.
 */
