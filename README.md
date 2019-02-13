# Appliance Control
Backend service (REST API) to control an appliance such as a wash machine or an oven.

*Note:* For now there is only oven control.

#### Build and run
In root folder of project, execute:
```cmd
mvn clean install 
java -jar target\appliancecontrol-0.0.1-SNAPSHOT.jar
```

#### Docker
After steps above you can make a docker image and start it. Also from root folder:
```cmd
docker build -f Dockerfile -t appliance-control .
docker run -p 8090:8080 appliance-control
```
Where 8090 is port your local machine. 

**Note:** on Windows system you cannot just type CTRL+C or etc for closing the program,
 you need know 'Container NAME or ID' and close it directly:
```cmd
docker container ls
docker container stop <Container NAME or ID>
```

#### H2 database
When app run you can check database state through web console: [http://localhost:8080/h2](http://localhost:8080/h2)
```cmd
JDBC URL: jdbc:h2:mem:test
User Name: sa 
(without password)
```
On start there is already test data from *data.sql*.

# API

For testing API you can use `curl` util (for example, from Linux or Git Bash) or smth like.

## Oven Endpoint (/api/v1/oven)

**Request**: `GET /api/v1/oven/{id}` return oven info.

**Response**: 200 or 404 if oven not found

*Example command: `curl http://localhost:8080/api/v1/oven/1`*
```json
{
  "id": 1,
  "name": "Timur oven",
  "doorIsOpen": false,
  "lightBulbIsOn": false,
  "cooking": false,
  "cookingParam": {
    "temperature": 0,
    "cookingTimeSeconds": 0,
    "heatingMode": null
  },
  "recipe": {
    "id": 1,
    "name": "Timur best chicken",
    "description": "Hello friends! My recipe steps too simple: 1. Get chicken 2. Add salt 3. Move chicken into oven 4. Accept recipe params 5. Well done!",
    "cookingParam": {
      "temperature": 255,
      "cookingTimeSeconds": 600,
      "heatingMode": {
        "id": 3,
        "name": "Convection"
      }
    }
  }
}
```

---
---
**Request**: `PUT /api/v1/oven/{id}/cooking` start or stop cooking

**Body**: true (if you want start cooking, or false to stop)

*Example command: `curl -X PUT -d 'true' -H 'Content-Type: application/json' http://localhost:8080/api/v1/oven/1/cooking`*

**Response**: 204 (no content) or 404 if oven not found

**Note**: if door is open, it will automatically closed on cooking start

---

---
**Request**: `PUT /api/v1/oven/{id}/doorIsOpen` open or close door

**Body**: true (if you want open door, or false to close)

*Example command: `curl -X PUT -d 'true' -H 'Content-Type: application/json' http://localhost:8080/api/v1/oven/1/doorIsOpen`*

**Response**: 204 (no content) or 404 if oven not found

---

---
**Request**: `PUT /api/v1/oven/{id}/lightBulbIsOn` on or off light in oven

**Body**: true (if you want light in oven)

*Example command: `curl -X PUT -d 'true' -H 'Content-Type: application/json' http://localhost:8080/api/v1/oven/1/lightBulbIsOn`*

**Response**: 204 (no content) or 404 if oven not found

---

---
**Request**: `PUT /api/v1/oven/{id}/recipe/id` to set some recipe, 
also recipe cooking params (temperature, heating mode, cooking time) 
will set to current oven params

**Body**: (long) id, of recipe

*Example command: `curl -X PUT -d '1' -H 'Content-Type: application/json' http://localhost:8080/api/v1/oven/1/recipe/id`*

**Response**: 200 (oven info, like show above), or 404 if oven or recipe not found

---

---
**Request**: `PUT /api/v1/oven/{id}/cookingParam` to set cooking param: 
temperature, heating mode, cooking time

**Body**: 
```json
{
    "temperature": 255,
    "cookingTimeSeconds": 600,
    "heatingMode": {
        "id": 3,
        "name": "Convection"
    }
}
```

*Example command: `curl -X PUT -d '{ "temperature": 255, "cookingTimeSeconds": 600, "heatingMode": { "id": 3, "name": "Convection" } }' -H 'Content-Type: application/json' http://localhost:8080/api/v1/oven/1/cookingParam`*

**Response**: 200 (oven info, like show above), or 404 if oven or heating mode not found

**Note**: there is no need to specify heating mode name, only id needed, 
name ignored and will gotten from DB

---
---



## Recipe Endpoint (/api/v1/recipe)

**Request**: `GET /api/v1/recipe/{id}` return recipe info.

**Response**: 200 or 404 if recipe not found

*Example command: `curl http://localhost:8080/api/v1/recipe/1`*
```json
{
  "id": 1,
  "name": "Timur best chicken",
  "description": "Hello friends! My recipe steps too simple: 1. Get chicken 2. Add salt 3. Move chicken into oven 4. Accept recipe params 5. Well done!",
  "cookingParam": {
    "temperature": 255,
    "cookingTimeSeconds": 600,
    "heatingMode": {
      "id": 3,
      "name": "Convection"
    }
  }
}
```

---
---
**Request**: `GET /api/v1/recipe` return all recipes.

**Response**: 200 (with list of recipes, one recipe shown above)

*Example command: `curl http://localhost:8080/api/v1/recipe`*

---
---


## Heating Mode Endpoint (/api/v1/heatingmode)

**Request**: `GET /api/v1/heatingmode` return all heating modes.

**Response**: 200

*Example command: `curl http://localhost:8080/api/v1/heatingmode`*
```json
[
  {
    "id": 1,
    "name": "Top heating"
  },
  {
    "id": 2,
    "name": "Bottom heating"
  },
  {
    "id": 3,
    "name": "Convection"
  }
]
```
