# PROTOCOLO CHAT CLIENTE-SERVIDOR 😄
En este Readme vamos a definir el protocolo que vamos a seguir en nuestra aplicación, el protocolo inicial. Después según avancemos en el proyecto, el protocolo puede ir cambiando.

**Índice**
- Planteamiento de los elementos que necesita mi aplicación.
- Configuración de conexión del cliente con el servidor.
- Mensajes y desconexión de los clientes.
- Configuración de conexión y límite de conexiones de los clientes con el servidor.
- Establecer hilos para las conexiones de los clientes en el servidor.
- Manejo de errores del servidor.

### 1. Planteamiento de los elementos que necesita mi aplicación 😄
Primero necesitamos identificar que es lo que necesitamos en la aplicación, es decir, necesitamos saber las reglas que tenemos que seguir para alcanzar un objetivo básico:

```bash
Protocolo de comunicación
```
El protocolo de comunicación que vamos a usar es TCP/IP por la siguientes razones:
1. **Entrega ordenada** -> Los mensajes deben de llegar en el mismo orden en el cual fueron enviados.
2. **Fiabilidad** -> Para asegurarnos de que no se pierdan los paquetes.

```bash
uso correcto de hilos para manejar las múltiples conexiones
```
Tenemos que asegurarnos de hacer uso correcto de hilos para manejar las conexiones de todos los clientes.

### 2. Configuración de conexión del cliente con el servidor 😄
En los ejercicios anteriores, el cliente se conectaba directamente al servidor. Aquí tenemos que hacerlo de manera diferente. Antes de que el cliente establezca una conexión con el servidor,
necesitamos que nos de la dirección IP y el puerto para conectar. En caso de que la dirección IP y el puerto sean correctos, se establece la conexión.

Resumen de pasos:
1. El cliente entra en la aplicación.
2. Se le solicita la IP y el puerto a conectarse.
3. Si son correctos se establece la conexión y luego se le pide un ***nickName*** para indentificarlo en la sala de chat.
4. Aparecerá un mensaje en la sala de chat indicando que el cliente con el nickName establecido, se ha conectado.

### 3. Mensajes y desconexión de los clientes. 😄
El cliente ya estableció conexión, ahora tiene que enviar los mensajes al servidor.

Como hacerlo: 🤔
1. Crear un hilo en el cliente para poder mandar mensajes al servidor todo el tiempo.
2. Podríamos poner un bucle infinito para que el cliente mande los mensajes que quiera en todo momento.
3. Si el cliente escribe una palabra clave, como ***\bye***, cerraríamos la conexión con el servidor.

Podemos probarlo con un solo cliente de momento para ver si funciona e ir ampliando funcionalidades.

### 4. Configuración de conexión y límite de conexiones de los clientes con el servidor 😄
Los clientes se van conectando al servidor. Este tiene la parte importante de:
1. Establecer un límite de conexiones: tenemos que hacer que solo un máximo de **10 personas** se conecte al servidor.
2. Una vez se conecta un cliente, se instancia la conexión en un hilo independiente.
3. Si ya hay 10 o más clientes en el chat, rechazamos la conexión con el cliente hasta que los clientes actuales se vayan.

### 5. Establecer hilos para las conexiones de los clientes en el servidor. 😄
Como maneja el servidor la lógica de los hilos con los clientes 🤔.

**Se mandan mensajes**
- ***¿Quién los mandan?*** -> Los clientes, ya que el servidor solo se encarga de recibir y mandar mensajes a todos los clientes.
- ***¿Quién los recibe?*** -> Los clientes, ellos mandan y reciben los mensajes de los demás clientes.

**Se manejan hilos de conexión**
- ¿Quién los crea? -> El servidor, ya que tiene que poner límites de conexión y cada conexión va en un hilo hasta un máximo de 10.

**¿Sólo el servidor maneja hilos?**
Nó, lo suyo es que el cliente maneje hilos para poder recibir los mensajes de los demás clientes también y le vaya notificando.

### 6. Manejo de errores del servidor. 😄
Tenemos que asegurarnos de manejar todos los errores posibles en la aplicación.

**Errores del lado del cliente**
1. Error de no conexión con el servidor. Tenemos que controlar que si el cliente manda una IP o un puerto equivocado, manejar esa excepción estableciendo el fin de la aplicación para el cliente o un ***while*** en el que se le pida otra dirección IP o un puerto.
2. Error de desconectarse del servidor. tenemos que manejar cuando el cliente se desconecte del servidor, de manera que aparezca un mensaje en el chat diciendo que el cliente con el ***nickName*** establecido al principio, se desconectó y los clientes actuales que hay en la sala.

**Errores del lado del servidor**
1. Error de manejo incorrecto de hilos. Tenemos que asegurarnos de establecer bien el límite de clientes que pueden conectarse al servidor e instanciar correctamente cada conexión en hilos.
2. Error de desconexión de clientes. tenemos que recoger bien cuando un cliente se desconecta, recoger el **nickName** y ponerlo en la sala de chat como mensaje explicativo.
   




