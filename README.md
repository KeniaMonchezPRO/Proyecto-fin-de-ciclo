# Viñeta Virtual

**Descripción**
Aplicación Android con backend API REST que ofrece a lectores de cómics, distribuidoras, editoriales y creadores, la oportunidad de tener un espacio para el disfrute o publicación de obras relacionadas al mundo de la viñeta.

[Presentacion](https://drive.google.com/file/d/1Y7Q6hOZQd_kH_dqzjFHSzEjpVNXFwGJO/view?usp=sharing)

[Demo](https://drive.google.com/file/d/1AoXDHO__yQA_xp6FJIWAmAUNx_fUMRrG/view?usp=sharing)

## Guía de instalación

**1. Importación del proyecto**
- Descargar el directorio **Tebeoteca** de este repositorio que contiene el backend, la aplicación Android y la base de datos

**2. Ejecución de la base de datos**
- Una vez descargado el proyecto, se debe de configurar la instancia de una nueva conexión (si es que no está ya configurada) de MySQL Workbench en la versión 8.0.
- La conexion debe ser **localhost** en el puerto **8080**
- Una vez configurada, puedes importar el script **vineta_virtual** y ejecutarlo

**3. Configuración y ejecución de la API en Eclipse IDE 2025-03**
- Para ejecutar la API, se recomienda utilizar el entorno de desarrollo **Eclipse IDE 2025-03**, además de establecer la configuración e instalación de las extensiones necesarias para ejecutar un proyecto **Maven** con **Spring Boot**. Se puede utilizar el marketplace de Eclipse la instalación de librerías, plugins y extensiones.
- El siguiente paso es localizar las application.properties del proyecto en **/src/main/resources/application.properties**

En este archivo se deberá de configurar el usuario y contraseña que se utilizó al momento de instanciar la conexión y ejecución de MySQL. Si en dado caso no se cuenta con usuario y contraseña, se puede probar con user=root, password= 

![image](https://github.com/user-attachments/assets/5c367b13-7a71-4465-b0eb-df0adb11cd0c)

- Ahora se debe de localizar la clase Main para ejecutar el proyecto:

![image](https://github.com/user-attachments/assets/b09896b3-7275-467c-a2f5-6a2ab2e0c39a)

- Una vez abierta, hacer click derecho y deberá de aparecer la opción de ejecutar con Spring Boot (Si no aparece es por que aún no está instalada la librería en Eclipse)

![image](https://github.com/user-attachments/assets/739ca2bd-316c-49ac-8dfa-cc8c7ab0efd8)

- Para comprobar que la ejecución del proyecto fue exitosa, deberá de mostrarse en la consola del entorno algo similar a lo siguiente:

![image](https://github.com/user-attachments/assets/1c09b581-6f40-4df4-a4da-70b04ea07552)

**Importante:**

- Aclarar que si se tiene ocupado el puerto 8080 no se podrá ejecutar el script, o si el IDE se cierra abruptamente y el proceso de Spring sigue abierto, seguirá ocupando el puerto 8080 y no se podrá volver a ejecutar el proyecto hasta que se termine ese proceso y el puerto quede libre.

**4. Ejecución de la aplicación en Android Studio**

**- A tener en cuenta:** 
  - El proceso de spring/eclipse debe de seguir en ejecución para que se puedan realizar las peticiones desde Android a la API para realizar operaciones.
  - El proyecto solo se puede ejecutar en un emulador de Android Studio, no se debe de instalar en un dispositivo real porque la configuración ya está establecida para que se conecte con un proceso abierto de spring/eclipse y el dispositivo virtual que se detallarán a continuación:
    
**- Requisitos:** La versión que se utilizó para la creación de la aplicación fue Android Studio LadyBug 2024, con un minSdk = 24 y un maxSdk = 34, por lo que se recomienda ejecutar el proyecto en un dispositivo virtual que contenga la API de ese rango. Por recomendación personal se puede instalar el dispositivo **Medium Phone API 35**.

![image](https://github.com/user-attachments/assets/83119115-b6d6-4a5e-bf11-4ad7ebc0f542)

- Antes de ejecutar el proyecto se deberá de asegurar que el emulador esté en la misma conexión de red, pero si al ejecutar el proyecto de android resulta en un error, busca en **com.example.vineta_virtual.login.LoginActivity** el siguiente trozo de código y compara si coincide la IP de tu emulador con la que se muestra a continuación:

![image](https://github.com/user-attachments/assets/3bc6fdf6-4f4e-43d0-bced-d43315118c61)



