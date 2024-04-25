# ejercicio-api

Api para hacer SIGN-UP y LOGIN

## Instrucciones de Construcción

Clonar el proyecto de github
```bash
  git clone https://github.com/matiasriosdoc/ejercicioBCI.git
```
Vaya a la carpeta del proyecto ejemplo:

```bash
  cd ..\ejercicioBCI
```

Para construir

```bash
  ./gradlew clean assemble build
```

Para desplegar

```bash
  ./gradlew bootRun
```

Para correr las pruebas

```bash
  ./gradlew --info test
```

se desplegara en el puerto 8081

para ver la consola de la BD H2
http://localhost:8081/h2-console/
Password: password

## Referencia API

#### Para mejorar su experiencia probando esta api puede instalar Postman y importar la coleccion que se encuentra adjuntada en este poyecto en una carpeta llamada postman-collection

### Hacer sign-up

```http
  POST /ejercicio-api/sign-up
```
cURL
```bash
curl -X POST http://localhost:8081/ejercicio-api/sign-up -H "Content-Type: application/json" -d "{\"name\":\"Matias\",\"email\":\"matias.rios156@gakkmail.com\",\"password\":\"a2asfGfdfdf4\",\"phones\":[{\"number\":1234,\"citycode\":2,\"countrycode\":\"ARG\"}]}"
```

Para copiar, pegar y ejecutar en PowerShell
```bash

$headers = New-Object "System.Collections.Generic.Dictionary[[String],[String]]"
$headers.Add("Content-Type", "application/json")

$body = @"
{
    `"name`": `"Matias`",
    `"email`": `"matias.rios156@gakkmail.com`",
    `"password`": `"a2asfGfdfdf4`",
    `"phones`": [
        {
            `"number`": 1234,
            `"citycode`": 2,
            `"countrycode`": `"ARG`"
        }
    ]
}
"@

$response = Invoke-RestMethod 'http://localhost:8081/ejercicio-api/sign-up' -Method 'POST' -Headers $headers -Body $body
$response | ConvertTo-Json

```

### Hacer login

```http
  GET /ejercicio-api/login
```

| Parameter | Type     | Description                                     |
|:----------| :------- |:------------------------------------------------|
| `Authorization`     | `string` | **Bearer**. Token JWT generado al hacer sign-up |

cURL (recuerde tomar el token de la respuesta del sign-up)
```bash
curl -X GET http://localhost:8081/ejercicio-api/login -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IiQyYSQxMCR1ZUZ2S1dpZzVvQVJpcDFaeERSa0xlcjdwbi9ZYkRlTVoxVVVXVHBPMHY1Y0xCMHdjRGR1bSIsImVtYWlsIjoibGV4LjE4NC4xdzlhc2E1czE1NnFhQGdha2ttYWlsLmNvbSJ9.8-Q_F6svHqYzIymEQZ4UnhYcZUtWlXHP6aqKdoAMQTg"
```