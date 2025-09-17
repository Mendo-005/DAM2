using System;
using Microsoft.VisualBasic;

namespace MyApp // Note: actual namespace depends on the project name.
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Random rng = new Random();
            int numeroAleatorio = rng.Next(1, 101);
            Console.WriteLine(numeroAleatorio);

            Console.WriteLine("Tamaño del tablero: ");
            string respuesta = Console.ReadLine();
            int tamanno = int.Parse(respuesta);

            Console.WriteLine("Hello World!");
            Persona p = new Persona(20);
            Console.WriteLine("edad: " + p.edad);

            int[] notas = new int[3];
            notas[2] = 10;

            //int[,] numeros = new int[2, 5];
            //int[,] numerosInicializados = { { 1, 1 }, { 2, 2 } };
            int[,] numerosInicializados = new int[tamanno, tamanno];  
            for (int i = 0; i < notas.Length; i++)
            {
                Console.WriteLine(i + "-");
            }

            for (int i = 0; i < numerosInicializados.GetLength(0); i++)
            {
                for (int j = 0; j < numerosInicializados.GetLength(1); j++)
                {
                    numerosInicializados[i,j] = rng.Next(1, 101);
                    Console.Write(numerosInicializados[i, j] + "");
                }
                Console.WriteLine();
            }
             
        }
    }
}