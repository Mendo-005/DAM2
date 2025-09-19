using System.Runtime.InteropServices;
using System.Security.Cryptography.X509Certificates;

namespace EjerciciosProgramacion
{
    internal class Program
    {
        static void Menu()
        {
            Console.Clear();
            // Inicializamos el menú
            Console.WriteLine("MENU");
            Console.WriteLine("1. Ejercicio-1");
            Console.Write("Selecciona un programa a ejecutar: ");

            // Añadimos una casilla para un Input 
            int eleccion = int.Parse(Console.ReadLine());

            // Creamos un switch
            switch (eleccion)
            {
                case 1:
                    Ejercicio1();
                    break;
                default:
                    Console.WriteLine("Opción no válida");
                    VolverMenu();
                    break;
            }

        }
        static void Ejercicio1()
        {
            // 1. Pedir al usuario que introduzca un número. Mostrar la raíz cuadrada del mismo. El
            // número debe ser mayor que cero, en caso contrario debe aparecer el mensaje "ERROR.
            // Los números negativos no tienen raíz cuadrada real."
            Console.Clear();
            Console.Write("Introduzca un numero: ");
            double nRaizCuadrada = double.Parse(Console.ReadLine());

            // Comprobamos que el número sea positivo
            if (nRaizCuadrada < 0)
            {
                Console.WriteLine("ERROR: El numero debe ser positivo");
                Ejercicio1();
            }
            else
            {
                Console.WriteLine($"La raiz cuadrada de {nRaizCuadrada} es igual a {Math.Sqrt(nRaizCuadrada)}");
            }
            VolverMenu();
        }

        static void Ejercicio15()
        {
            // 15. Programa que solicita números entre 1 y 10. Si se salen de ese rango, el programa da
            // mensaje de error. Si el número es correcto, el programa te dice si es o no un número primo
            Console.Clear();
            Console.WriteLine("Introduzca un numero del 1-10");
            int nPrimo = int.Parse(Console.ReadLine());

            if (nPrimo < 1 || nPrimo > 10)
            {
                Console.WriteLine("ERROR: EL numero debe estar en el rango de (1-10)");
                Ejercicio15();
            }
            else
            {
                
            }
        }

        // Función para volver al menú
        static void VolverMenu()
        {
            Console.WriteLine("Volver al menu: ");
            Console.WriteLine();
            Console.WriteLine();
            int volverMenu = int.Parse(Console.ReadLine());

            switch (volverMenu)
            {
                case 1:
                    Menu();
                    break;
                case 2:
                    Console.Clear();
                    Console.WriteLine("¡Hasta luego!");
                    break;
                default:
                    Console.WriteLine("Opción no válida");
                    VolverMenu();
                    break;
            }
        }
        
        static void Main(string[] args)
        {
            Menu();
        }
     } 
}

