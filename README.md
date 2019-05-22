# Laboratorio 2 Sistemas Distribuidos: Streaming de Tweets

## 1. INTEGRANTES (Grupo A1):
* Cristobal Donoso
* Shalini Ramchandani 
* Diego Salazar

## 2. DESCRIPCIÓN DEL PROBLEMA
Hoy en día Twitter es una de las plataformas mas utilizadas a nivel global, donde los millones de usuarios que existen se comunican a través de mensajes cortos de 140 caracteres llamados Tweets. Esto permite que Twitter como red social maneje una cantidad enorme de información, la cual se encuentra dispersa en sus bases de datos. Ademas, Twitter permite obtener tweets con respecto a algún tema en particular mediante el uso de Hashtags, por ejemplo, eventos que han ocurrido en las últimas horas, alguna tendencia, etc. Estos hashtags facilitan la búsqueda de conceptos o temas y conexión con gente que haya hablado con al respecto. Sin embargo, no todos hacen uso de aquello, como también la cantidad de caracteres a ocupar en el mensaje llega a ser un límite. Por lo que para realizar una búsqueda con respecto a un tema en especial y hacer algún análisis de sentimientos, si el Tweet no tiene el hashtag del tema, no se considera el Tweet. 

## 3. SOLUCIÓN
La solución de lo descrito anteriormente, consiste en implementar un sistema de streaming que obtenga todos los tweets recientes mediante el uso de la API de Twitter. El usuario podrá colocar las palabras que desee en base al tema que quiera filtrar los tweets, y se obtendrán los tweets que contengan todas o la mayoría de las palabras de la bolsa. Esto permite abstraerse del concepto de Hashtag, es decir, se filtrarán todos los tweets que esten necesariamente relacionados al tema y no sólo los que contengan el Hashtag correspondiente. Además, al realizarse mediante streaming, se obtienen tweets en tiempo real, por ende la información que el usuario recibirá siempre será de último momento. 

## 4. DESARROLLO DE LA SOLUCIÓN
Se realiza la ingesta de datos a una base de datos y se disponibiliza una interfaz gráfica para la consulta de datos. En este caso, se hace uso de Twitter4j para la obtención de Tweets en tiempo real, los cuales son almacenados en MongoDB. Sobre los tweets en streaming se realizan dos filtros: que contenga alguna o varias palabras de la bolsa y que el idioma sea en Español. Esto se realiza en el lenguaje de programación Scala utilizando una extensión de Spark llamada Spark Streaming. Al guardarse los datos en la base de datos de MongoDB, la cual se encuentra alojada en Microsoft Azure, se tiene la API con los distintos servicios para poder consumir los Tweets de la BD, también desarrollado en Scala mediante Play! framework. Estos servicios se detallan en la sección 6. Posteriormente, esta API es consumida por el FrontEnd el cual fue realizado con React, donde el usuario podrá ingresar palabras a una bolsa de palabras y visualizar tweets recientes relacionados al tema que ha escogido. Esto al ser en tiempo real se actualiza a medida que se obtienen nuevos tweets, en caso de que estos sean acorde al tema.

### 4.1 Principales inconvenientes y barreras detectadas

### 4.2 Clases principales del desarrollo
Dentro del Streaming de Twitter las clases importantes son:
* TwitterStreaming/src/main/scala/Example/main.scala
* TwitterStreaming/src/main/scala/Example/utils.scala

Dentro de la API creada con scala las clases son:
* /app/controllers/TweetController.scala
* 
* app/utils/CorsFilter.scala

Dentro del FrontEnd:
* src\components\streaming\index.js

## 5. RESULTADOS
En esta imagen se puede visualizar el FrontEnd de la plataforma, a través de la API creada, contiene los tweets obtenidos en tiempo real. 

## 6. LINKS DEL SOFTWARE EN PRODUCCIÓN:
### 6.1 Servicios de BackEnd:
### Agregar o remover palabras
```
GET 	/words/	  Retorna TODAS las palabras de la bolsa
POST 	/words/	  Almacena una palabra nueva
DELETE 	/words/	 Elimina una palabra
```
### Todos los tweets

```
GET 	/tweets/:limit	Obtiene los <limit> ultimos tweets con cualquiera de las palabras de la bolsa
```  
### Obtención de tweets

```
GET 	/tweets/by-id/:id	 Obtiene el tweet con ID <id>

GET 	/tweets/by-word/:word/:limit	Obtiene los <limit> ultimos tweets que contiene la palabra <word>

```
### 6.2 FrontEnd:

```
http://lab-2-distribuidos.eastus.cloudapp.azure.com/
                      o
http://40.121.62.100/
```
## 7. PASOS PARA DESPLEGAR EL SERVICIO DESDE CERO
Para el streaming de Twitter se puede seguir el siguiente link:
```
https://medium.com/@harinilabs/day-6-realtime-tweets-analysis-using-spark-streaming-with-scala-10a9937aae57
https://github.com/harinij/100DaysOfCode/tree/master/Day%20006%20-%20Spark%20Streaming%20using%20Scala/SparkStreamingTweet

```
Para la creación de la API:
```
https://github.com/ehsanmx/Teispes-myblog

```
Para el frontEnd:
```

```
