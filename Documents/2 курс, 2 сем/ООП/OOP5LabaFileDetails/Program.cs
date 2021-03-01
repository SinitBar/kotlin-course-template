using System;
using System.IO; // FileStream, FileReader

namespace OOP5LabaFileDetails
{
	class FileDetails
	{
		static void Summarize(char[] char_array, out long vowels, out long consonents, out long line_breaks)
		{
			vowels = consonents = line_breaks = 0;
			foreach (char symbol in char_array)
			{
				if ("AEIOUaeiou".IndexOf(symbol) != -1)
					// symbol is a vowel
					vowels++;
				else if ("BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz".IndexOf(symbol) != -1)
					// symbol is a consonent
					consonents++;
				else if (symbol == '\n') // symbol is a line break
					line_breaks++;
			}
		}
		static void Main(string[] args)
		{
			//Console.WriteLine(args.Length);
			//foreach (string string_arg in args)
			//	Console.WriteLine(string_arg);
			try
			{
				try
				{
					if (args.Length < 1)
						throw new IndexOutOfRangeException("start argument is missing");
					string fileName = args[0]; // we sholud start program from cmd using fileName like parameter
					FileStream stream = new FileStream(fileName, FileMode.Open);
					StreamReader reader = new StreamReader(stream);
					long length_of_file = stream.Length; // length of fileName file 
					if (!File.Exists(fileName)) // file doesn't exist
						throw new FileNotFoundException("file doesn't exist");
					if (length_of_file <= 0)
						throw new ArgumentOutOfRangeException("file is empty");
					char[] contents = new char[length_of_file]; // char massive, copy of fileName file
					//Console.WriteLine("length = {0}", contents.Length);
					for (int i = 0; i < length_of_file; i++)
						contents[i] = (char)(reader.Read());
					foreach (char symbol in contents)
						Console.Write(symbol);
					reader.Close();
					long vowels_amount, consonents_amount, line_breaks_amount;
					Summarize(contents, out vowels_amount, out consonents_amount, out line_breaks_amount);
					Console.WriteLine("\nAmount of symbols in the file: {0}", contents.Length);
					Console.WriteLine("Amount of vowels: {0}", vowels_amount);
					Console.WriteLine("Amount of consonents: {0}", consonents_amount);
					Console.WriteLine("Amount of lines: {0}", line_breaks_amount + 1); // last line doesn't have a line break

				}
				catch (IndexOutOfRangeException startArgument)
				{
					Console.WriteLine("Error: {0}", startArgument.Message);
					Console.WriteLine("Stack: {0}", startArgument.StackTrace);
				}
				catch (ArgumentOutOfRangeException argument)
				{
					Console.WriteLine("Error: {0}", argument.Message);
					Console.WriteLine("Stack: {0}", argument.StackTrace);
				}
				catch (FileNotFoundException noFile)
				{
					Console.WriteLine("Error: {0}", noFile.Message);
					Console.WriteLine("Stack: {0}", noFile.StackTrace);
				}
			}
			catch (Exception some_exception)
			{
				Console.WriteLine("Error: {0}", some_exception.Message);
				Console.WriteLine("Stack: {0}", some_exception.StackTrace);
			}
		}
	}
}
