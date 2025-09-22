//using System;
//using System.Collections;

namespace MyApp // Note: actual namespace depends on the project name.
{
    internal class Program
    {


        static void Main(string[] args)
        {
            Random rng = new Random();

            int numeroAleatorio = rng.Next(1, 11);

            Console.WriteLine("Dime el tamaño del tablero:");
            string respuesta = Console.ReadLine();
            int tamanno = int.Parse(respuesta);

            Persona p = new Persona(20);
            p.Edad = 2;
            Console.WriteLine("edad: " + p.Edad);

            int[] notas = new int[3];
            notas[2] = 10;

            int[,] numeros = new int[3, 5]; // Array bidimensional
            int[,] numerosInicializados = new int[tamanno,tamanno];
               
                                        
            for (int i = 0; i < notas.Length; i++)
            {
                Console.WriteLine(i + "-" + notas[i]);
            }

            for (int i = 0; i < numerosInicializados.GetLength(0); i++)
            {
                for (int j = 0; j < numerosInicializados.GetLength(1); j++)
                {
                    numerosInicializados[i, j] = rng.Next(1, 100);
                    Console.Write(numerosInicializados[i, j] + " ");
                }
                Console.WriteLine();
           }
        }
    }
}