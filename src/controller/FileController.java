package controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import exceptions.PalabraNoEncontradaException;

public class FileController {
	
	private static String fileName = "diccionario-v1.txt";
	private static File file = new File(fileName);

	public static void setFileName(String fileName) {
		FileController.fileName = fileName;
		file = new File(fileName);
	}

	static Reader fileReader;
	static BufferedReader bufferedReader;

	static Writer fileWriter;
	static BufferedWriter bufferedWriter;

	public static DefaultListModel<String> leer(String entrada) throws PalabraNoEncontradaException, FileNotFoundException {
		DefaultListModel<String> traducciones = new DefaultListModel<String>();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			bufferedReader = new BufferedReader(fileReader);
			String linea = "";
			while((linea=bufferedReader.readLine())!=null) {
				String[] lineaArray = linea.split(";");
				String japones = lineaArray[0];
				String espanol = lineaArray[1];
				if (entrada.equalsIgnoreCase((japones))) {
					traducciones.addElement(espanol);
				} else if (entrada.equalsIgnoreCase(espanol)) {
					traducciones.addElement(japones);
				}
			}
		} catch (FileNotFoundException e) {
			// El archivo no se encontro
			throw e;
		} catch (IOException e) {
			// Error al leer linea
			e.printStackTrace();
		} 
		finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();					
				}
				if (fileReader != null) {
					fileReader.close();					
				}
			} catch (IOException e) {
				// Error al cerrar readers
				e.printStackTrace();
			}
		}
		if (traducciones.isEmpty()) {
			throw new PalabraNoEncontradaException("Palabra no encontrada");
		}
		return traducciones;
	}

	public static ListModel<String> getAll(int order) {
		DefaultListModel<String> list = new DefaultListModel<String>();
		try {
			fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			bufferedReader = new BufferedReader(fileReader);
			String linea = "";
			while((linea=bufferedReader.readLine())!=null) {
				String[] lineaArray = linea.split(";");
				if (lineaArray.length == 2 && !list.contains(lineaArray[order])) {
					list.addElement(lineaArray[order]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();					
				}
				if (fileReader != null) {
					fileReader.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void escribir(String japones, String espanol) {
		try {
			fileWriter = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(japones + ";" + espanol);
			bufferedWriter.newLine();
			System.out.println("informacion agregada: " + japones + " - " + espanol);
		} catch (IOException e) {
			// Hubo un error al escribir
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e1) {
				// Error al cerrar writters
				e1.printStackTrace();
			}
		}
	}

	public static void eliminar(String jap, String esp) {
		File temp = new File("temp.txt");
		try {
			fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			bufferedReader = new BufferedReader(fileReader);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp, true), StandardCharsets.UTF_8));
			String removeID = jap + ";" + esp;
			String linea = "";
			boolean deleted = false;
			while((linea = bufferedReader.readLine()) != null) {
				if(linea.equalsIgnoreCase(removeID)) {
					if (!deleted) {
						deleted = true;
					} else {
						bufferedWriter.write(linea + System.getProperty("line.separator"));												
					}
				} else {
					bufferedWriter.write(linea + System.getProperty("line.separator"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			temp.delete();
		} finally {
			if (bufferedReader != null) {
				try {
					if (bufferedWriter != null) {
						bufferedWriter.close();
					}
					if (bufferedReader != null) {
						bufferedReader.close();					
					}
					if (fileReader != null) {
						fileReader.close();					
					}
					if (file != null) {
						file.delete();						
					}
					if (temp != null) {
						temp.renameTo(file);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
