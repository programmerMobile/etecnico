Ingresar por swagger: 

http://localhost:8080/swagger-ui/index.html#/user-controller

Ingresar el siguiente JSON:

{
  "name": "Marco Tito",
  "password": "aAbBcB12",
  "email": "marco.atitov@gmail.com",
  "phones": [
    {
      "cityCode": "001",
      "countryCode": "0032",
      "number": "12345"
    }
  ]
}



Se agrego también pruebas unitarias con Mockito y Junit:
solo ejecutar el archivo que esta en el TEST.
