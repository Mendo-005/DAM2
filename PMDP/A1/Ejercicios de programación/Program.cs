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
            Console.WriteLine("15. Ejercicio-15");
            Console.WriteLine("1.9. Ejercicio-1.9");
            Console.WriteLine("Juego de la vida");
            Console.Write("Selecciona un programa a ejecutar: ");

            // Añadimos una casilla para un Input 
            int eleccion = int.Parse(Console.ReadLine());

            // Creamos un switch
            switch (eleccion)
            {
                case 1:
                    Ejercicio1();
                    break;
                case 15:
                    Ejercicio15();
                    break;
                case 19:
                    EjercicioBucles9();
                    break;
                case 1000:
                    JuegoDeLAVida();
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
            Console.Write("Introduzca un numero del 1-10: ");
            int nPrimo = int.Parse(Console.ReadLine());

            if (nPrimo < 1 || nPrimo > 10)
            {
                Console.WriteLine("ERROR: EL numero debe estar en el rango de (1-10)");
                Ejercicio15();
            }
            else
            {
                // Función para comprobar si un número es primo
                static bool EsPrimo(int n)
                {
                    if (n <= 1) return false;
                    if (n == 2) return true;
                    if (n % 2 == 0) return false;

                    for (int i = 3; i <= Math.Sqrt(n); i += 2)
                    {
                        if (n % i == 0)
                            return false;
                    }
                    return true;
                }

                if (EsPrimo(nPrimo))
                {
                    Console.WriteLine($"El numero {nPrimo} es primo");
                    VolverMenu();
                }
                else
                {
                    Console.WriteLine($"El numero {nPrimo} no es primo");
                    VolverMenu();
                }
            }
        }

        static void EjercicioBucles9()
        {
            Console.Clear();
            Console.Write("Ingrese un número: ");
            int numLimite = int.Parse(Console.ReadLine());

            for (int i = 0; numLimite != i - 1; i++)
            {
                Console.WriteLine(i);
            }
            VolverMenu();
        }

        // Función para volver al menú
        static void VolverMenu()
        {
            Console.Write("Volver al menu SI(1) / NO (0): ");
            int volverMenu = int.Parse(Console.ReadLine());

            switch (volverMenu)
            {
                case 1:
                    Menu();
                    break;
                case 0:
                    Console.Clear();
                    Console.WriteLine("¡Hasta luego!");
                    break;
                default:
                    Console.WriteLine("Opción no válida");
                    VolverMenu();
                    break;
            }
        }





        static void JuegoDeLAVida()
        {
            Console.WriteLine("============================");
            Console.WriteLine("===== JUEGO DE LA VIDA =====");
            Console.WriteLine("============================");

            Console.WriteLine("Introduzca el ancho del tablero: ");
            int ancho = int.Parse(Console.ReadLine());

            Console.WriteLine("Introduzca el alto del tablero: ");
            int alto = int.Parse(Console.ReadLine());

            Console.WriteLine("Introduzca el numero de celulas iniciales: ");
            int celulasIniciales = int.Parse(Console.ReadLine());
            

        }



    // Inicializar el Programa
        static void Main(string[] args)
        {
            Menu();
        }
     } 
}

