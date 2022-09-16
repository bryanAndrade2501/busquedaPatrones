import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Busqueda {
    static Scanner entrada = new Scanner(System.in);
    static String rutaArchivo;
    static ArrayList<String> archivoTexto = new ArrayList<>();
    //static ArrayList<String> arrayPosiciones = new ArrayList<>();
    static ArrayList<String> arrayResultadoLineaYPosicion = new ArrayList<>();
    static ArrayList<String> resultadoFinal = new ArrayList<>();
    static String textoIngresado;
    static int numeroDePatrones;
    static String [] patrones;

    //Menu principal
    public static int menu() throws IOException {
        int op;
        String mnu;
        mnu="1. Ingreso de texto	\n";
        mnu+="2. Ingreso de Patron/es \n";
        mnu+="3. Seleccionar algoritmo de busqueda \n";
        mnu+="4. Salir \n";
        mnu+=" INGRESA OPCION: ";
        op=Integer.parseInt (JOptionPane.showInputDialog(null,mnu,"BUSQUEDA DE PATRONES",JOptionPane.QUESTION_MESSAGE));
        return op;

    }
    //Menu para la carga del archivo o ingreso de texto
    public static int menuCargaArchivo() throws IOException {
        int op;
        String mnu;
        mnu="1. Cargar un Archivo	\n";
        mnu+="2. Escribir un texto \n";
        mnu+="3. Salir \n";
        mnu+=" INGRESA OPCION: ";
        op=Integer.parseInt (JOptionPane.showInputDialog(null,mnu,"BUSQUEDA DE PATRONES",JOptionPane.QUESTION_MESSAGE));
        return op;
    }

    //Menu para la seleccion del algoritmo de busqueda
    public static int menuSeleccionarAlgoritmo() throws IOException {
        int op;
        String mnu;
        mnu="1. Fuerza Bruta	\n";
        mnu+="2. KMP \n";
        mnu+="3. Boyer Moore \n";
        mnu+="4. Los 3 algoritmos \n";
        mnu+="5. Salir \n";
        mnu+=" INGRESA OPCION: ";
        op=Integer.parseInt (JOptionPane.showInputDialog(null,mnu,"BUSQUEDA DE PATRONES",JOptionPane.QUESTION_MESSAGE));
        return op;
    }

    //Menu para el ingreso de los patrones
    public static String [] menuIngresoPatron(int npatrones) throws IOException {
        String[] patrones = new String[npatrones];
        for(int i = 0; i<npatrones;i++){
            patrones[i]=JOptionPane.showInputDialog(null,"Ingrese el patron " + (i+1) + ":");
        }
        JOptionPane.showMessageDialog(null, "Patron/es ingresado correctamente"); //Muesta un mensaje con el patron o patrones ingresado.
        return patrones; //Devuelve el vector con los patrones.
    }

    //Devuelve la ruta del archivo en donde se encuentra el texto
    public static String cargarRutaArchivo(){
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);//Muestra la ventana para la seleccion de archivos
        String ruta = fileChooser.getSelectedFile().getAbsolutePath(); //Obteiene la ruta del archivo
        return ruta; //Devuelve la ruta del archivo
    }

    //Permite obtener los datos del archivo
    public static ArrayList<String> obtenerArchivo(String ruta){
        ArrayList<String> archivo = new ArrayList<>(); //Se crea un array tipo string denominado "archivo"
        try {
            File f = new File(ruta);
            entrada = new Scanner(f);
            while (entrada.hasNext()) {
                archivo.add(entrada.nextLine()); //cada linea del archivo de texto ingresado se añade al array "archivo"
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return archivo; //Se devuelve el array
    }

    public static void main(String [] args) throws IOException{
        // Autor; Bryan Andrade
        // Fecha: 11/Agosto/2020
        String verificacionOPC;
        int opc,opcCargaArchivo,opcSeleccionAlgoritmo;
        ArrayList<String> resultados= new ArrayList<>();
        do {
            opc = menu(); //Se inicializa el menu principal
            //Mediante un switch se maneja el menu principal
            switch (opc) {
                //PARA PODER CARGAR O ESCRIBIR EL ARCHIVO DE TEXTO
                //Si la persona ingresa el número 1, entonces se activa el caso 1:
                case 1:
                    do{
                        opcCargaArchivo = menuCargaArchivo(); //Se inicializa el menú para la carga del archivo
                        //Mediante un segundo switch se maneja la carga o escitura del texto
                        switch (opcCargaArchivo){

                            case 1: //Para la carga del archivo
                                rutaArchivo=cargarRutaArchivo(); //Se guarda la ruta del archivo seleccionado
                                archivoTexto=obtenerArchivo(rutaArchivo); //se otiene el contenido del archivo y se lo guarda en un array "archivoTexto"
                                JOptionPane.showMessageDialog(null, "Archivo ingresado correctamente"); //Muestra un mensaje confirmando que el archivo ha sido agregado correctamente
                                opcCargaArchivo = 3; //Permite salir del segundo switch y volver al menú principal
                                break;

                            case 2: //Para escribir el texto
                                //Mediante una ventana se pide escribir el texto y se lo guarda en un string "textoEscrito"
                                String textoEscrito=JOptionPane.showInputDialog(null,"BUSQUEDA DE PATRONES");
                                archivoTexto.add(0,textoEscrito); //El texto ingresado se agrega al array "archivoTexo" en la posicion 0;
                                JOptionPane.showMessageDialog(null, "Texto ingresado correctamente"); //Muestra un mensaje de confirmación del texto ingresado
                                opcCargaArchivo = 3; //Permite salir del segundo switch y volver al menú principal
                                break;
                        }
                    }while(opcCargaArchivo<3);
                    break;
                //PARA DETERMINAR EL PATRON O PATRONES QUE SE VAN A BUSCAR
                //Si la persona ingresa el número 2, entonces se activa el caso 2:
                case 2:
                    numeroDePatrones = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Ingrese el numero de patrones")); //Se muestra una ventana que pide el ingreso del número de patrones que se desea ingresar, y guarda el valor
                    // en la variable "numeroDePatrones"
                    patrones = menuIngresoPatron(numeroDePatrones); //Con el número de patrones obtenido, llama al método "menuIngresoPatron" para el posterior ingreso de el/los patrón/es.
                    break;
                //PARA LA SELECCION DEL ALGORITMO O ALGORITMOS DE BUSQUEDA
                //Si la persona ingresa el número 3, entonces se activa el caso 3:
                case 3:
                    do{
                        opcSeleccionAlgoritmo = menuSeleccionarAlgoritmo(); //Se inicializa el menu para la seleccion del algoritmo
                        switch (opcSeleccionAlgoritmo){
                            //CUANDO SE ESCOJA EL ALGORITMO DE FUERZA BRUTA
                            case 1:
                                for(String patron : patrones){ //Mediante un ciclo for, el string "patron" va a obtener uno a uno los valores dentro del array "patrones"
                                    long tiempoInicio = System.currentTimeMillis(); //Se asigna la hora actual del sistema en milisegundos a la variable "tiempoInicio"
                                    for(int f=0; f<archivoTexto.size();f++){ //Mediante un ciclo for se recorre el array "archivoTexto", la variable f sirve para determinar la linea en que se encuentra el patrón
                                        //Se llama al metodo fuerzaBruta1 al que se envia el dato del array "archivoTexto" y el patron
                                        //El resultado (la posición o posiciones de la coincidencia) se asigna al array "resultados"
                                        resultados=busquedaFuerzaBruta(archivoTexto.get(f),patron);
                                        for(int s =0; s<resultados.size();s++){ //Mediante un ciclo for, se recorre el array "resultados"
                                            //Se agrega la linea f en que se encontró la coincidencia, junto con la posicion o posiciones de la misma
                                            //Todo esto (linea y posicion) se asigna al array "arrayResultadoLineaYPosicion"
                                            arrayResultadoLineaYPosicion.add("Se encontró en la linea " + (f+1) + " en la posicion " + resultados.get(s));
                                        }
                                    }
                                    long tiempoFinal = System.currentTimeMillis(); //Se asigna la hora actual del sistema en milisegundos a la variable "tiempoFinal"
                                    //Se abre una ventana de mensaje que muestra el número total de coincidencias encontrdas, junto con el patron que se utilizó y el tiempo total de ejecucion
                                    JOptionPane.showMessageDialog(null, "Para el algoritmo Fuerza Bruta se encontraron " + arrayResultadoLineaYPosicion.size() +
                                            " coincidencias con el patron " + patron + " y la busqueda tomo " + (tiempoFinal-tiempoInicio) + " milisegundos");
                                    //Mediante un ciclo for se agrega la cantidad de coincidencias, detallando donde se encuentra cada una (linea y posicion)
                                    //Todos estos datos se asignan a un array "resultadoFinal"
                                    for(int v=0; v<arrayResultadoLineaYPosicion.size();v++){
                                        resultadoFinal.add("La coincidencia " + (v+1) + " " + arrayResultadoLineaYPosicion.get(v) + "\n");
                                    }
                                    JOptionPane.showMessageDialog(null,resultadoFinal); //Se muestra una ventana con los resultados.
                                    resultadoFinal.clear(); //Se borran los datos del array "resultadoFinal"
                                    arrayResultadoLineaYPosicion.clear(); //Se borran los datos del array "arrayResultadoLineaYPosicion"

                                }
                                archivoTexto.clear();// Se borran los datos del array "archivoTexto"
                                opcSeleccionAlgoritmo=5; //Permite salir del menú de selección del algoritmo y regresar al menú principal
                                break;
                            //CUANDO SE ESCOJA EL ALGORITMO KMP
                            case 2:
                                for(String patron : patrones){//Mediante un ciclo for, el string "patron" va a obtener uno a uno los valores dentro del array "patrones"
                                    long tiempoInicio = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoInicio"
                                    for(int f=0; f<archivoTexto.size();f++){//Mediante un ciclo for se recorre el array "archivoTexto", la variable f sirve para determinar la linea en que se encuentra el patrón
                                        //Se llama al metodo findKMP al que se envia el dato del array "archivoTexto" y el patron
                                        //El resultado (la posición o posiciones de la coincidencia) se asigna al array "resultados"
                                        resultados=busquedaKMP(archivoTexto.get(f).toCharArray(),patron.toCharArray());
                                        for(int s =0; s<resultados.size();s++){//Mediante un ciclo for, se recorre el array "resultados"
                                            //Se agrega la linea f en que se encontró la coincidencia, junto con la posicion o posiciones de la misma
                                            //Todo esto (linea y posicion) se asigna al array "arrayResultadoLineaYPosicion"
                                            arrayResultadoLineaYPosicion.add("Se encontró en la linea " + (f+1) + " en la posicion " + resultados.get(s));
                                        }
                                    }
                                    long tiempoFinal = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoFinal"
                                    //Se abre una ventana de mensaje que muestra el número total de coincidencias encontrdas, junto con el patron que se utilizó y el tiempo total de ejecucion
                                    JOptionPane.showMessageDialog(null, "Para el algoritmo KMP se encontraron " + arrayResultadoLineaYPosicion.size() +
                                            " coincidencias con el patron " + patron + " y la busqueda tomo " + (tiempoFinal-tiempoInicio) + " milisegundos");
                                    //Mediante un ciclo for se agrega la cantidad de coincidencias, detallando donde se encuentra cada una (linea y posicion)
                                    //Todos estos datos se asignan a un array "resultadoFinal"
                                    for(int v=0; v<arrayResultadoLineaYPosicion.size();v++){
                                        resultadoFinal.add("La coincidencia " + (v+1) + " " + arrayResultadoLineaYPosicion.get(v) + "\n");
                                    }
                                    JOptionPane.showMessageDialog(null,resultadoFinal);//Se muestra una ventana con los resultados.
                                    resultadoFinal.clear();//Se borran los datos del array "resultadoFinal"
                                    arrayResultadoLineaYPosicion.clear();//Se borran los datos del array "arrayResultadoLineaYPosicion"

                                }
                                archivoTexto.clear();// Se borran los datos del array "archivoTexto"
                                opcSeleccionAlgoritmo=5;//Permite salir del menú de selección del algoritmo y regresar al menú principal
                                break;
                            //CUANDO SE ESCOJA EL ALGORITMO BOYER MOORE
                            case 3:
                                for(String patron : patrones){//Mediante un ciclo for, el string "patron" va a obtener uno a uno los valores dentro del array "patrones"
                                    long tiempoInicio = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoInicio"
                                    for(int f=0; f<archivoTexto.size();f++){//Mediante un ciclo for se recorre el array "archivoTexto", la variable f sirve para determinar la linea en que se encuentra el patrón
                                        //Se llama al metodo busquedaBoyerMoore al que se envia el dato del array "archivoTexto" y el patron
                                        //El resultado (la posición o posiciones de la coincidencia) se asigna al array "resultados"
                                        resultados=busquedaBoyerMoore(archivoTexto.get(f).toCharArray(), patron.toCharArray());
                                        for(int s =0; s<resultados.size();s++){//Mediante un ciclo for, se recorre el array "resultados"
                                            //Se agrega la linea f en que se encontró la coincidencia, junto con la posicion o posiciones de la misma
                                            //Todo esto (linea y posicion) se asigna al array "arrayResultadoLineaYPosicion"
                                            arrayResultadoLineaYPosicion.add("Se encontró en la linea " + (f+1) + " en la posicion " + resultados.get(s));
                                        }
                                    }
                                    long tiempoFinal = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoFinal"
                                    //Se abre una ventana de mensaje que muestra el número total de coincidencias encontrdas, junto con el patron que se utilizó y el tiempo total de ejecucion
                                    JOptionPane.showMessageDialog(null, "Para el algoritmo Boyer Moore se encontraron " + arrayResultadoLineaYPosicion.size() +
                                            " coincidencias con el patron " + patron + " y la busqueda tomo " + (tiempoFinal-tiempoInicio) + " milisegundos");
                                    //Mediante un ciclo for se agrega la cantidad de coincidencias, detallando donde se encuentra cada una (linea y posicion)
                                    //Todos estos datos se asignan a un array "resultadoFinal"
                                    for(int v=0; v<arrayResultadoLineaYPosicion.size();v++){
                                        resultadoFinal.add("La coincidencia " + (v+1) + " " + arrayResultadoLineaYPosicion.get(v) + "\n");
                                    }
                                    JOptionPane.showMessageDialog(null,resultadoFinal);//Se muestra una ventana con los resultados.
                                    resultadoFinal.clear();//Se borran los datos del array "resultadoFinal"
                                    arrayResultadoLineaYPosicion.clear();//Se borran los datos del array "arrayResultadoLineaYPosicion"

                                }
                                archivoTexto.clear();// Se borran los datos del array "archivoTexto"
                                opcSeleccionAlgoritmo=5;//Permite salir del menú de selección del algoritmo y regresar al menú principal
                                break;
                            //CUANDO SE ESOCOJAN LOS 3 ALGORITMOS
                            case 4:
                                for(String patron : patrones){
                                    //-----------------------------------------------------------------------------------------------------------------------------------------------
                                    //FUERZA BRUTA
                                    //----------------------------------------------------------------------------------------------------------------------------------------
                                    long tiempoInicio = System.currentTimeMillis(); //Se asigna la hora actual del sistema en milisegundos a la variable "tiempoInicio"
                                    for(int f=0; f<archivoTexto.size();f++){ //Mediante un ciclo for se recorre el array "archivoTexto", la variable f sirve para determinar la linea en que se encuentra el patrón
                                        //Se llama al metodo fuerzaBruta1 al que se envia el dato del array "archivoTexto" y el patron
                                        //El resultado (la posición o posiciones de la coincidencia) se asigna al array "resultados"
                                        resultados=busquedaFuerzaBruta(archivoTexto.get(f),patron);
                                        for(int s =0; s<resultados.size();s++){ //Mediante un ciclo for, se recorre el array "resultados"
                                            //Se agrega la linea f en que se encontró la coincidencia, junto con la posicion o posiciones de la misma
                                            //Todo esto (linea y posicion) se asigna al array "arrayResultadoLineaYPosicion"
                                            arrayResultadoLineaYPosicion.add("Se encontró en la linea " + (f+1) + " en la posicion " + resultados.get(s));
                                        }
                                    }
                                    long tiempoFinal = System.currentTimeMillis(); //Se asigna la hora actual del sistema en milisegundos a la variable "tiempoFinal"
                                    long tiempoFuerzaBruta = tiempoFinal-tiempoInicio;
                                    //Se abre una ventana de mensaje que muestra el número total de coincidencias encontrdas, junto con el patron que se utilizó y el tiempo total de ejecucion
                                    JOptionPane.showMessageDialog(null, "Para el algoritmo Fuerza Bruta se encontraron " + arrayResultadoLineaYPosicion.size() +
                                            " coincidencias con el patron " + patron + " y la busqueda tomo " + (tiempoFinal-tiempoInicio) + " milisegundos");
                                    //Mediante un ciclo for se agrega la cantidad de coincidencias, detallando donde se encuentra cada una (linea y posicion)
                                    //Todos estos datos se asignan a un array "resultadoFinal"
                                    for(int v=0; v<arrayResultadoLineaYPosicion.size();v++){
                                        resultadoFinal.add("La coincidencia " + (v+1) + " " + arrayResultadoLineaYPosicion.get(v) + "\n");
                                    }
                                    JOptionPane.showMessageDialog(null,resultadoFinal); //Se muestra una ventana con los resultados.
                                    resultadoFinal.clear(); //Se borran los datos del array "resultadoFinal"
                                    arrayResultadoLineaYPosicion.clear(); //Se borran los datos del array "arrayResultadoLineaYPosicion"



                                    //----------------------------------------------------------------------------------------------------------------------------------------
                                    //KMP
                                    //-----------------------------------------------------------------------------------------------------------------------------------------
                                    long tiempoInicio2 = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoInicio"
                                    for(int f=0; f<archivoTexto.size();f++){//Mediante un ciclo for se recorre el array "archivoTexto", la variable f sirve para determinar la linea en que se encuentra el patrón
                                        //Se llama al metodo findKMP al que se envia el dato del array "archivoTexto" y el patron
                                        //El resultado (la posición o posiciones de la coincidencia) se asigna al array "resultados"
                                        resultados=busquedaKMP(archivoTexto.get(f).toCharArray(),patron.toCharArray());
                                        for(int s =0; s<resultados.size();s++){//Mediante un ciclo for, se recorre el array "resultados"
                                            //Se agrega la linea f en que se encontró la coincidencia, junto con la posicion o posiciones de la misma
                                            //Todo esto (linea y posicion) se asigna al array "arrayResultadoLineaYPosicion"
                                            arrayResultadoLineaYPosicion.add("Se encontró en la linea " + (f+1) + " en la posicion " + resultados.get(s));
                                        }
                                    }
                                    long tiempoFinal2 = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoFinal"
                                    long tiempoKMP = tiempoFinal2-tiempoInicio2;
                                    //Se abre una ventana de mensaje que muestra el número total de coincidencias encontrdas, junto con el patron que se utilizó y el tiempo total de ejecucion
                                    JOptionPane.showMessageDialog(null, "Para el algoritmo KMP se encontraron " + arrayResultadoLineaYPosicion.size() +
                                            " coincidencias con el patron " + patron + " y la busqueda tomo " + (tiempoFinal2-tiempoInicio2) + " milisegundos");
                                    //Mediante un ciclo for se agrega la cantidad de coincidencias, detallando donde se encuentra cada una (linea y posicion)
                                    //Todos estos datos se asignan a un array "resultadoFinal"
                                    for(int v=0; v<arrayResultadoLineaYPosicion.size();v++){
                                        resultadoFinal.add("La coincidencia " + (v+1) + " " + arrayResultadoLineaYPosicion.get(v) + "\n");
                                    }
                                    JOptionPane.showMessageDialog(null,resultadoFinal);//Se muestra una ventana con los resultados.
                                    resultadoFinal.clear();//Se borran los datos del array "resultadoFinal"
                                    arrayResultadoLineaYPosicion.clear();//Se borran los datos del array "arrayResultadoLineaYPosicion"




                                    //-----------------------------------------------------------------------------------------------------------------------------------
                                    //BOYER MOORE
                                    //----------------------------------------------------------------------------------------------------------------------------------------------
                                    long tiempoInicio3 = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoInicio"
                                    for(int f=0; f<archivoTexto.size();f++){//Mediante un ciclo for se recorre el array "archivoTexto", la variable f sirve para determinar la linea en que se encuentra el patrón
                                        //Se llama al metodo busquedaBoyerMoore al que se envia el dato del array "archivoTexto" y el patron
                                        //El resultado (la posición o posiciones de la coincidencia) se asigna al array "resultados"
                                        resultados=busquedaBoyerMoore(archivoTexto.get(f).toCharArray(), patron.toCharArray());
                                        for(int s =0; s<resultados.size();s++){//Mediante un ciclo for, se recorre el array "resultados"
                                            //Se agrega la linea f en que se encontró la coincidencia, junto con la posicion o posiciones de la misma
                                            //Todo esto (linea y posicion) se asigna al array "arrayResultadoLineaYPosicion"
                                            arrayResultadoLineaYPosicion.add("Se encontró en la linea " + (f+1) + " en la posicion " + resultados.get(s));
                                        }
                                    }
                                    long tiempoFinal3 = System.currentTimeMillis();//Se asigna la hora actual del sistema en milisegundos a la variable "tiempoFinal"
                                    long tiempoBoyerMoore = tiempoFinal3-tiempoInicio3;
                                    //Se abre una ventana de mensaje que muestra el número total de coincidencias encontrdas, junto con el patron que se utilizó y el tiempo total de ejecucion
                                    JOptionPane.showMessageDialog(null, "Para el algoritmo Boyer Moore se encontraron " + arrayResultadoLineaYPosicion.size() +
                                            " coincidencias con el patron " + patron + " y la busqueda tomo " + (tiempoFinal3-tiempoInicio3) + " milisegundos");
                                    //Mediante un ciclo for se agrega la cantidad de coincidencias, detallando donde se encuentra cada una (linea y posicion)
                                    //Todos estos datos se asignan a un array "resultadoFinal"
                                    for(int v=0; v<arrayResultadoLineaYPosicion.size();v++){
                                        resultadoFinal.add("La coincidencia " + (v+1) + " " + arrayResultadoLineaYPosicion.get(v) + "\n");
                                    }
                                    JOptionPane.showMessageDialog(null,resultadoFinal);//Se muestra una ventana con los resultados.
                                    resultadoFinal.clear();//Se borran los datos del array "resultadoFinal"
                                    arrayResultadoLineaYPosicion.clear();//Se borran los datos del array "arrayResultadoLineaYPosicion"
                                    ArrayList<String> tiemposEjecucion = new ArrayList<>(); //Se crea un array "tiemposEjecucion" que guarda los tiempos de ejecucion de los algoritmos
                                    //el tiempo de ejecucion y nombre de cada algoritmo se agrega al array
                                    tiemposEjecucion.add(tiempoFuerzaBruta + " Fuerza Bruta");
                                    tiemposEjecucion.add(tiempoKMP + " KMP");
                                    tiemposEjecucion.add(tiempoBoyerMoore + "  Boyer Moore");
                                    Collections.sort(tiemposEjecucion); //se ordenan los tiempos de menor a mayor
                                    String mejorAlgoritmo = tiemposEjecucion.get(0); //se asigna el primer valor del array al string "mejorAlgoritmo"
                                    //Finalmente se muestra una ventana describiendo el patron y el algoritmo que lo ejecutó en el menor tiempo, para ello se utiliza el método substring
                                    JOptionPane.showMessageDialog(null,"Para buscar el patron " + patron + " el mejor algoritmo fue " + mejorAlgoritmo.substring(3,mejorAlgoritmo.length()));
                                }
                                archivoTexto.clear();// Se borran los datos del array "archivoTexto"
                                opcSeleccionAlgoritmo=5;

                                break;
                        }
                    }while(opcSeleccionAlgoritmo<5);

                    break;
            }
        }while (opc<4);



    }


    //TODO ****************************** FUERZA BRUTA **************************************************************
    private static ArrayList<String> busquedaFuerzaBruta(String texto, String patron) {
        int lt = texto.length();
        int lP = patron.length();
        ArrayList<String> arrayPosiciones = new ArrayList<>();
        for (int q = 0; q <= (lt - lP); q++) { //iterar sober todo el texto
            int t = 0; //iterar el patron
            while (t < lP && texto.charAt(q+t) == patron.charAt(t)) {//Mientras t sea menor a la longitud del texto y el caracter(q+t) del texto
                //sea igual al caracter t del patron, se aumenta el valor de t
                t++;
            }
            if (t == lP) {//si t alcanza la longitud del patrón, entonces se encontró una coincidencia y por tanto se guarda esa coincidencia q en el array "arrayPosiciones"
                arrayPosiciones.add(q + " ");
            }
        }
        return arrayPosiciones;
    }


    //TODO *********************************** KMP***********************************************************
    public static ArrayList<String> busquedaKMP(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        boolean comprobar = false;
        ArrayList<String> arrayPosiciones = new ArrayList<>();
        String strResultados = "";
        if (m == 0)
            return arrayPosiciones; // trivial search for empty string
        comprobar=false;
        int[] fail = computoFalloKMP(pattern); // computed by private utility
        //System.out.println(Arrays.toString(fail)); // Para visualizar el contenido de la funci�n de fallo.
        int j = 0; // index into text
        int k = 0; // index into pattern
        while (j < n) {
            if (text[j] == pattern[k]) { // pattern[0..k] matched thus far
                if (k == m - 1) {
                    strResultados += (j - m + 1) + ","; // match is complete
                    arrayPosiciones.add((j - m + 1) + ",");

                    k = 0;
                }
                j++; // otherwise, try to extend match
                k++;
            } else if (k > 0)
                k = fail[k - 1]; // reuse suffix of P[0..k-1]
            else
                j++;
        }

        return arrayPosiciones; // reached end without match
    }

    private static int[] computoFalloKMP(char[] pattern) {
        int m = pattern.length;
        int[] fail = new int[m]; // Mismo tama�o que el patr�n
        int j = 1;
        int k = 0;
        while (j < m) { // compute fail[j] during this pass, if nonzero
            if (pattern[j] == pattern[k]) { // k + 1 characters match thus far
                fail[j] = k + 1;
                j++;
                k++;
            } else if (k > 0) // k follows a matching prefix
                k = fail[k - 1];
            else // no match found starting at j
                j++;
        }
        return fail;
    }

    //------------------------------------------------------------------------------------------------------
    //ALGORITMO BOYER MOORE MEJORADO
    //-------------------------------------------------------------------------------------------------------
    static int NO_OF_CHARS = 256;

    //A utility function to get maximum of two integers
    static int max (int a, int b) { return (a > b)? a: b; }

    //The preprocessing function for Boyer Moore's
    //bad character heuristic
    static void badCharHeuristic( char []str, int size,int badchar[])
    {

        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++)
            badchar[i] = -1;

        // Fill the actual value of last occurrence
        // of a character (indices of table are ascii and values are index of occurrence)
        for (int i = 0; i < size; i++)
            badchar[(int) str[i]] = i;
    }

    /* A pattern searching function that uses Bad
    Character Heuristic of Boyer Moore Algorithm */
    static ArrayList<String> busquedaBoyerMoore( char txt[],  char pat[])
    {
        ArrayList<String> arrayPosiciones = new ArrayList<>();
        int m = pat.length;
        int n = txt.length;

        int badchar[] = new int[NO_OF_CHARS];

      /* Fill the bad character array by calling
         the preprocessing function badCharHeuristic()
         for given pattern */
        badCharHeuristic(pat, m, badchar);

        int s = 0;  // s is shift of the pattern with
        // respect to text
        //there are n-m+1 potential allignments
        while(s <= (n - m))
        {
            int j = m-1;

          /* Keep reducing index j of pattern while
             characters of pattern and text are
             matching at this shift s */
            while(j >= 0 && pat[j] == txt[s+j])
                j--;

          /* If the pattern is present at current
             shift, then index j will become -1 after
             the above loop */
            if (j < 0)
            {
                //System.out.println("Patterns occur at shift = " + s);
                arrayPosiciones.add(s + " ");
              /* Shift the pattern so that the next
                 character in text aligns with the last
                 occurrence of it in pattern.
                 The condition s+m < n is necessary for
                 the case when pattern occurs at the end
                 of text */
                //txt[s+m] is character after the pattern in text
                s += (s+m < n)? m-badchar[txt[s+m]] : 1;

            }

            else
              /* Shift the pattern so that the bad character
                 in text aligns with the last occurrence of
                 it in pattern. The max function is used to
                 make sure that we get a positive shift.
                 We may get a negative shift if the last
                 occurrence  of bad character in pattern
                 is on the right side of the current
                 character. */
                s += max(1, j - badchar[txt[s+j]]);
        }
        return arrayPosiciones;
    }
}
