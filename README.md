Para utilizar los recursos, se debe levantar el proyecto a través de un IDE como Eclipse o con el comando: mvn spring-boot:run

Si se desea levantar el proyecto en Eclipse, lo que se debe hacer es bajarse el proyecto de Github. Luego en Eclipse -> File -> Import -> Maven -> Existing Maven Projects -> Browse... (ubicarse en la carpeta del proyecto) -> Finish

Una vez importado el proyecto en Eclipse, se debe ubicar la clase Application.java dentro del path: src/main/java y el paquete: com.fuego.quasar. Hacer click derecho sobre la clase Application.java -> Run as -> Java Application. Una vez hecho esto podremos consultar los recursos de la api a través de Postman o Swagger.

Se puede acceder a Swagger a través de la URL: http://localhost:8080/swagger-ui.html#/

Y allí se podrán probar los recursos. Otra opción es utilizar directamente Postman

Dentro del proyecto, existe una carpeta llamada postman, allí se encuentra un archivo que se puede importar en Postman y así poder probar los recursos.

De todas formas, procedo a listar los recursos:

1. GET: http://localhost:8080/naves/{idNave} -> Sirve para obtener los datos de una nave a través de su id

2. GET: http://localhost:8080/naves/{idNave}/topsecret-split -> Sirve para obtener el mensaje secreto de la nave y su ubicacion, una vez que se le haya proporcionado la información necesaria a los 3 satelites

3. POST: http://localhost:8080/naves -> Sirve para guardar los datos de una nueva nave. Al guardar los datos de la nave, el recurso nos devolverá un id, el cual será importante para poder utilizar en los demás recursos

4. POST: http://localhost:8080/naves/{idNave}/topsecret -> Sirve para guardar los datos que envia la nave a los satelites y con dichos datos se descifra el mensaje secreto junto con la ubicación de la nave.

5. POST: http://localhost:8080/naves/{idNave}/topsecret-split/{satelliteName} -> Sirve para guardar los datos que envia la nave a un satelite en particular. Una vez que se envien los datos a los 3 satelites, se podrá consultar el mensaje descifrado y la ubicación de la nave a través del recurso GET mencionado en el punto 2. 

Para poder generar casos de pruebas y poder chequear que sea correcto lo que devuelven los recursos que calculan la ubicación de la nave a partir de la información de las distancias de los satelites a la nave, se puede utilizar la siguiente página: 

https://www.calculatorsoup.com/calculators/geometry-plane/distance-two-points.php

La misma sirve para obtener las distancias que hay entre 2 puntos en un plano 2D. 

Sabemos que la ubicación de los satelites son las siguientes:

● Kenobi: [-500, -200]
● Skywalker: [100, -100]
● Sato: [500, 100]

Por lo tanto, para generar un caso de prueba, lo que debemos hacer es entrar a dicha página y en el campo (X1, Y1) ponemos la posición del satelite Kenobi: -500, -200, y en el campo (X2, Y2) ponemos una posición donde estará ubicada la nave, por ejemplo: 150,50. Allí obtendremos la distancia entre ambos puntos. Guardamos esa distancia en algún lado (un bloc de notas) para utilizarla luego en los recursos, y procedemos a repetir el mismo procedimiento pero esta vez con el segundo satelite: Skywalker. En el campo (X1, Y1) ponemos la posición del satelite Skywalker y en el campo (X2, Y2) volvemos a poner la posición de la nave: 150, 50. Obtendremos la distancia entre el satelite Skywalker y la nave, guardamos ese dato y procedemos a hacer lo mismo pero con el satelite Sato. 

Una vez tenemos las 3 distancias entre la nave y los satelites, procedemos a ejecutar una prueba ya sea por Postman o Swagger.

Se puede tomar como ejemplo las pruebas que dejé en el archivo dentro de la carpeta postman. Dicho archivo debe importarse en Postman para poder visualizar los ejemplos.

Una vez que realicemos la prueba, veremos que la ubicación resultante de la nave no es 100% exacta, ya que el algoritmo de la trilateración no es 100% exacto pero es muy aproximado. Para este caso yo obtuve como resultado:

"x": 150.00005,
"y": 49.999836

En principio los endpoints de la api iban a ser distintos, pero tuve que lidiar con el hecho de que el recurso mencionado en el punto 5, tiene que ser capaz de guardar la información enviada de la nave a un satelite, y una vez la nave haya enviado los datos a todos los satelites, recién ahí permitir obtener el mensaje y la ubicación de la nave. Entonces, para poder lidiar con esta situación, lo que hice fue implementar una base de datos en h2, para poder persistir esa información y no dejarla guardada en objetos en memoria mientras la api esté levantada. Además, consideré que podrían llegar a haber más de 1 nave, entonces tenía que tener forma de identificarlas, entonces fue ahí cuando tuve que cambiar los endpoints a la forma en las que terminaron quedando. Y además, tuve que crear los recursos necesarios para poder crear y consultar datos de naves.

Por otro lado, hay test para ejecutar en la clase que se encuentra en el path: src/test/java y el paquete: com.fuego.quasar.test

Se puede acceder a la base de datos h2 con la url: http://localhost:8080/h2-console/login.jsp

Con los datos:

Driver class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa

