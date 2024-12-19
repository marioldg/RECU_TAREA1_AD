import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import clasesBase.Combate;
import clasesBase.Entrenador;
import clasesBase.Torneo;
import controlFicheros.*;

import static controlFicheros.leerCredenciales.*;


public class Main {
    static ArrayList<String> listaPaises = nombresPaises();

    public static void main(String[] args) {

        menuInvitado();

    }

    /**
     * menu generico de donde parte el programa
     *
     */

    public static void menuInvitado() {
        Scanner sc = new Scanner(System.in);
        int option = -1;
        while (!(option == 1 || option == 2 || option == 0)) {
            System.out.println("Bienvenido al menu de Invitado!!");
            System.out.println("O para salir");
            System.out.println("1 para registrarse");
            System.out.println("2 para iniciar sesion");
            option = controlarExceptionInt();
            switch (option) {
                case 0:
                    System.out.println("Saliendo...");
                    break;
                case 1:
                    registrarse();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    System.out.println("Intentalo de nuevo..");
                    menuInvitado();
            }


        }

    }

    /**
     * menuAdminTorneo de momento no hace nada
     */

    public static void menuAdminTorneo(){
        System.out.println("Bienvenido al menu Administrador de Torneo");
        System.out.println("Pulse 0: salir\n" +
                "Pulse 1: por completar");
        int option = controlarExceptionInt();
        switch (option){
            case 0:
                System.out.println("Saliendo...");
                menuInvitado();
            case 1:
                System.out.println("Actualmente este menu esta en mantenimiento");
        }
    }

    /**
     * menuEntrenador puede ver datos carnet o exportar el carnet
     */

    public void menuEntrenador(Entrenador e) {

        while (true) {
            System.out.println("Eres el Entrenador las opciones son esas :" +
                    "\n 0- Volver al login" +
                    "\n 1- Ver Carnet" +
                    "\n 2- Exportar Carnet");


            int opcion = controlarExceptionInt();


            if (opcion == 1) {
                System.out.println(e.toString());


            } else if (opcion == 2) {
                System.out.println("src/main/Files");


            } else if (opcion == 0) {
                menuInvitado();
                return;
            } else {
                System.out.println("Opcion no valida.Saliendo del programa...");
                break;
            }

        }
    }



    /**
     * menu adminGeneral
     * puede volver o crear torneo
     *
     */

    public static void menuAdminGeneral() {
        System.out.println("Pulsa 0: salir\n" +
                "Pulsa 1: crear torneo");
        int option = controlarExceptionInt();
        switch (option) {
            case 0:
                System.out.println("Volviendo menu Invitado...");
                menuInvitado();
                break;
            case 1:
                crearTorneo();
                break;
        }

    }




    /**
     * menu de inicio de sesion.
     * desde este menu se redirige a otros menus dependiendo del rol;siempre y cuando
     * las credenciales sean correctas.
     */

    public static boolean iniciarSesion() {
        boolean login=false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime tu nombre de user");
        String user = sc.nextLine();
        System.out.println("Dime tu contraseña");
        String pass = sc.nextLine();
        String rol = leerCredenciales.controlLogIn(user, pass);

        if(rol.equalsIgnoreCase("AdminGeneral")){
            System.out.println("Bienvenido al menu Administrador");
            menuAdminGeneral();
            login=true;

        }else if(rol.equalsIgnoreCase("Entrenador")){
            //menuEntrenador();
            System.out.println("Bienvenido al menu Entrenador");
            login=true;

        }else if(rol.equalsIgnoreCase("AdminTorneo")){
            //menuAdminTorneo();
            System.out.println("Bienvenido al menu Administrador de Torneo");
            login=true;

        }else{
            System.out.println("Credenciales incorrectas");
            System.out.println("Pulsa 0: salir\n" +
                    "Pulsa 1:intentarlo de nuevo");
            int option = controlarExceptionInt();
            switch (option) {
                case 0:
                    menuInvitado();
                    System.out.println("Saliendo..");
                    break;
                case 1:
                    iniciarSesion();
                    break;
            }
        }
    return login;

    }

    /**
     * intenta crear un Entrenador y escribirlo en Credenciales.txt
     * solo si el nombre(user) no esta repetido y el pais que indica si existe entre los que hay
     */

    public static void registrarse() {
        boolean existePais = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime tu nombre de user");
        String user = sc.nextLine();
        System.out.println("Dime tu contraseña");
        String pass = sc.nextLine();
        for (int i = 0; i < listaPaises.size(); i++) {
            System.out.println(listaPaises.get(i));
        }
        System.out.println("Cual es tu nacionalidad");
        String nombrePais = sc.nextLine();

        for (int i = 0; i < listaPaises.size(); i++) {
            if (nombrePais.equalsIgnoreCase(listaPaises.get(i))) {
                existePais = true;
            }
        }


        if (!(leerCredenciales.comprobarNuevo(user)) && (existePais)) {
            escribirFicheros.insertarCredenciales(user, pass, "Entrenador", idEntrenador());
            System.out.println("Entrenador creado con exito");
            menuInvitado();
        } else {
            System.out.println("Pais no encontrado");
            System.out.println("Pulsa 0: salir \n" +
                    "Pulse 1: reintentar");

            int option = controlarExceptionInt();
            switch (option) {
                case 0:
                    menuInvitado();
                    break;
                case 1:
                    registrarse();
                    break;
            }

        }


    }


    /**
     * metodo crea torneo y asocia a un admin torneo
     */

    public static void crearTorneo() {
        boolean existePais = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Como se llamara tu torneo?");
        String nombreTorneo = sc.nextLine();

        System.out.println("Indica a que region pertenecera el torneo");
        char region = sc.nextLine().charAt(0);
        System.out.println("Nombre del administrador del torneo");
        String nomAdminT = sc.nextLine();
        System.out.println("Contraseña del administrador del torneo");
        String passAdminT = sc.nextLine();



        if (!(comprobarNuevo(nomAdminT))) {
            escribirFicheros.insertarCredenciales(nomAdminT, passAdminT, "AdminTorneo", idAdminTorneo());
            Torneo torneo = new Torneo(idAdminTorneo()-1,nombreTorneo,region,nomAdminT);
            System.out.println("Administrador de torneo creado correctamente!!");
            System.out.println("Torneo creado correctamente");
            Combate combateA = new Combate(pedirFecha(), torneo.getId());
            Combate combateB = new Combate(pedirFecha(), torneo.getId());
            Combate combateC = new Combate(pedirFecha(), torneo.getId());
            System.out.println("Combates registrados con exito");
            menuAdminGeneral();
        } else {
            System.out.println("El administrador del torneo ya existe");
            System.out.println("Pulse 0: salir \n" +
                    "Pulse 1: reintentar");
            int option2 = controlarExceptionInt();
            switch (option2) {
                case 0:
                    System.out.println("Saliendo...");
                    menuInvitado();
                case 1:
                    System.out.println("Reintentando...");
                    menuAdminGeneral();
            }
        }
    }

    /**
     * pedir fecha valida
     * @return
     */

        public static LocalDate pedirFecha() {
            Scanner scanner = new Scanner(System.in);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = null;
            boolean fechaValida = false;

            while (!fechaValida) {
                try {
                    System.out.print("Introduce una fecha en formato yyyy-MM-dd: ");
                    String entrada = scanner.nextLine();
                    fecha = LocalDate.parse(entrada, formatter);
                    fechaValida = true; // Si no hay excepción, la fecha es válida
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Por favor, intenta de nuevo.");
                }
            }

            return fecha;
        }

    /**
     * Con este metodo controlamos que cuando pides un int por pantalla
     * no rompa el programa por no ser int
     */

    public static int controlarExceptionInt() {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        boolean valido = false;

        // Repetir hasta que se ingrese un valor válido
        while (!valido) {
            System.out.print("Por favor, ingresa un número entero: ");
            try {
                // Intentamos parsear el valor ingresado como un entero
                numero = Integer.parseInt(scanner.nextLine());
                valido = true;  // Si no hay error, la entrada es válida
            } catch (NumberFormatException e) {
                System.out.println("¡Error! Debes ingresar un número entero.");
            }
        }
        return numero;
    }

}


