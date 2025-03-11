# wigilabs
# Documentación de la prueba: Wigilabs
# Se adjunta coleccion de postman para pruebas
Para la prueba debera ejecutar primero la petición de signup para la creación, luego ahi si empieza a interactuar con el token para hacer uso del microservicio.
### Descripción General
Este proyecto es una API desarrollada en Spring Boot 3.4.2 que permite la gestión de productos, con almacenamiento en PostgreSQL corriendo en Docker. La API proporciona funcionalidades para crear, actualizar, eliminar y consultar productos, además de tener una buena implementación de seguridad.

La API proporciona funcionalidades para **crear, actualizar, eliminar y consultar productos**.

## Tecnologías Utilizadas
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **Spring Security**
- **Spring Web**
- **Spring Boot DevTools**
- **Docker & Docker Compose**
- **PostgreSQL**
- **Maven**
- **JWT**
- **Lombok**

## 🛠 Configuración
Instalación y Configuración

``` Instalación y Configuración
Clonar el Repositorio

git clone -b develop https://github.com/Yeicamm/wigilabs.git
cd wigilabs
```
## 🛠 Configuración de la Base de Datos (Docker & PostgreSQL)
El proyecto usa **Docker** para la base de datos. Para ejecutarla, usa el siguiente **docker-compose**:

``` 
docker-compose up -d
```
##  Configurar el archivo application.properties
Edita el archivo src/main/resources/application.properties para configurar la conexión a PostgreSQL:
```

# Configuración de PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/wigilabsdb
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=yJx2w3FvK9QeL8l6mZ5Tt9OqJb5A3xN0K+5Fs9Ys6aA=

```
## Ejecutar la Aplicación
Para iniciar el servicio de Spring Boot, ejecuta:
```
mvn spring-boot:run

si no hacerlo de forma manual con el main de la aplicación
```



Endpoints de la API
## 🛠️ Endpoints de la API

### 🔐 Autenticación (JWT)
| Método | Endpoint | Descripción |
|--------|---------|-------------|
| `POST` | `/api/auth/signup` | Registra un nuevo usuario. |
| `POST` | `/api/auth/login` | Inicia sesión y devuelve un token JWT. |

#### 📌 **Ejemplo de Request (`POST /api/auth/login`)**
```json
{
  "username": "admin",
  "password": "admin123"
}

```
## 🛒 Gestión de Productos

### 📌 Endpoints disponibles:
| Método  | Endpoint                | Descripción                     | Seguridad |
|---------|-------------------------|---------------------------------|-----------|
| `GET`   | `/api/products`         | Obtiene todos los productos.   | 🔒 Requiere autenticación |
| `GET`   | `/api/products/{id}`    | Obtiene un producto por ID.    | 🔒 Requiere autenticación |
| `POST`  | `/api/products`         | Crea un nuevo producto.        | 🔒 Requiere autenticación |
| `PUT`   | `/api/products/{id}`    | Actualiza un producto existente. | 🔒 Requiere autenticación |
| `DELETE`| `/api/products/{id}`    | Elimina un producto.           | 🔒 Requiere autenticación |

---

### 📌 **1️⃣ Obtener todos los productos**
**📍 Endpoint:**  
```http
GET /api/products
