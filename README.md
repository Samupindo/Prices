# 🛒 Proyecto - Gestión de Productos y Tiendas  

Este proyecto proporciona una API REST para la gestión de productos y tiendas. Permite realizar operaciones CRUD sobre productos y tiendas, así como filtrado y modificaciones específicas.  

---

## Funcionalidades ##
### 📦 Productos  
- **Alta de productos** → Permite añadir nuevos productos a la base de datos.  
- **Baja de productos** → Elimina un producto de la base de datos.  
- **Modificación de productos** → Permite modificar los valores de un producto.  
- **Filtrar productos** → Permite buscar productos por **name** y **price**.  


### 🏬 Tienda  
- **Alta de tienda** → Permite registrar una nueva .  
- **Baja de tienda** → Elimina una  existente.  
- **Modificación de tienda** → Permite actualizar los datos de una tienda.  
- **Añadir un producto a tienda** → Asigna un producto a una tienda específica.  
- **Filtrar tiendas** → Permite buscar tiendas por **name, país y ubicación**.  

---



## ENDPOINTS ##

### Productos 
#### Obtener los productos 
```
http
GET /products
```
Ejemplo JSON respuesta
```json
[
  {
    "productId": 2,
    "name": "Agua",
    "shop": [
      {
        "shopId": 1,
        "price": 10.5
      },
      {
        "shopId": 2,
        "price": 9.5
      }
    ]
  },
  {
    "productId": 10,
    "name": "Pizza con piña",
    "shop": [
      {
        "shopId": 1,
        "price": 10.5
      }
    ]
  }
]

```
#### Obtener producto por ID  
```
http
GET /products/{productId}
```
Ejemplo JSON respuesta
```json
{
    "productId": 2,
    "name": "Agua",
    "shop":[
        {
            "shopId" : 1,
            "price": 10.5
        },
        {
            "shopId" : 2,
            "price": 9.5
        }
    ]
}
```
Ejemplo Producto no existente
```
HTTP/1.1 200 OK
```
```json
[]
```

#### Añadir un producto
```
http
POST /products
```
Json entrada
```json
{
    "name": "Pizza con piña"
}
```

Respuesta
```
HTTP/1.1 201 Created
```

```json

    {
    "productId":1,
    "name": "Pizza con piña"
}
```
**Ejemplo  Error**
```
HTTP/1.1 400 Bad Request
```

```json
{
  "message": "The field doesn`t exist"
}

```

#### Borrar un producto
```
http
DELETE /products/{productId}
```
Respuesta:
```
HTTP/1.1 200 OK
```
Ejemplo  Error
```
HTTP/1.1 404 Not found
```
#### Actualizar producto 
```
http
PUT `/products/{productId}
```

Json entrada
```json
{
    "name": "Pizza con piña y chocolate"
}
```
Respuesta:
```
HTTP/1.1 200 OK
```
```json

{
    "productId":1,
    "name": "Pizza con piña y chocolate"
}
```
**Ejemplo  Error**
```
HTTP/1.1 400 Bad request
```
Json Respuesta

```json
{
    "message": "The field doesn`t exist"
}
```
#### Filtro de productos
```
http
GET /products/filter?=
```

Ejemplo busqueda: 
```
GET /products/filter?name=agua&priceMin=9
```
Salida
```json
[
      {
        "productId": 2,
        "name": "Agua",
        "shop": [
              {
              "shopId": 1,
              "price": 10.5
            },
            {
              "shopId": 2,
              "price": 9.5
            }
        ]  
      },
    
    {
        "productId": 4,
        "name": "Agua con gas",
        "shop":[
            {
                "shopId" : 1,
                "price": 14
            },
            {
                "shopId" : 5,
                "price": 20
            }
        ]
    }
]
```

Ejemplo  Sin productos
```
HTTP/1.1 200 OK
```
```json
[]
```
### **Tienda** ###
#### Obtener todas las tiendas

```
http
GET /shops
```
Ejemplo salida
```json
[
    {
        "shopId": 1,
        "country": "España",
        "city": "A Coruña",
        "address": "Los Mallos, 10"
    },
    {
        "shopId": 2,
        "country": "Argentina",
        "city": "Buenos Aires",
        "address": "Dirección inventada"
    },
    {
        "shopId": 3,
        "country": "España",
        "city": "Santiago",
        "address": "Av. Toledo"
    }
]
```
#### Obtener una tienda por id

```
http
GET /shops/{shopId}
```
Ejemplo salida
```json
{
    "shopId": 1,
    "country": "España",
    "city": "Coruña",
    "address": "Av. Finisterre"
}
```
Ejemplo si no encuentra tiendas
```
HTTP/1.1 200 OK
```

```json
[]
```
#### Crear una tienda

```
http
POST `/shops`
```

Json entrada 
```json
{
    "country": "España",
    "city": "Santiago",
    "address":"Av Buenos Aires"
}
```
Salida
```
Status: 201 Created
```
```json
{
    "shopId": 1,
    "country": "España",
    "city": "Santiago",
    "address":"Av Buenos Aires"
}
```
**Ejemplo error**
```
HTTP/1.1 409 Conflict
```
#### Añadir un producto a la tienda 

```
http
POST /shops/{shopId}/products/{productId}
```
Json entrada 
```json
{
    "price": 10.5
}
```
Respuesta
```
HTTP/1.1 200 OK
```
```json
{
    "productId" : 1,
    "shopId": 1,
    "price": 10.5
}
```
**Ejemplo  Error**
```
HTTP/1.1 409 Conflict
```
Json Salida 
```json
{
    "message": "The product alredy exists"
}
```
#### Dar de baja una tienda
```
http
DELETE /shops/{shopId}
```

Salida:
```
HTTP/1.1 200 OK
```

Ejemplo  Error

```
HTTP/1.1 404 Not found
```

#### Borrar un producto de la tienda
```
http
DELETE /shops/{shopId}/products/{productId}
```

Json entrada 
```json
{
    "price": 10.5
}
```
Respuesta
```
HTTP/1.1 200 OK
```
```json
{
    "productId" : 1,
    "shopId": 1,
    "price": 10.5
}
```
**Ejemplo  Error**
```
HTTP/1.1 404 Not Found
```
**Ejemplo  Error**
```
HTTP/1.1 400 Bad Request
```

#### Actualizar tienda
```
http
PUT `/shops/{shopId}
```

Json entrada 
```json
{
    "country": "España",
    "city":" A Coruña",
    "address": "Av de Arteixo 6"
}
```
Salida:
```
HTTP/1.1 200 OK
```

```json
{
    "shopId": 5,
    "country": "Colombia",
    "city": "A Coruña",
    "address": "Los Mallos, 12"
}
```

**Ejemplo  Error**
Salida
```
HTTP/1.1 404 Not found
```
**Ejemplo  Error**
```
HTTP/1.1 400 Bad request
```
Json salida 
```json
{
    "message": "The field doesn´t exist"
}
```
#### Actualizar parcialmente la tienda 
```
http
PATCH /shops/{shopId}
```
Json entrada 
```json
{
    "city":" Santiago"
}
```
Salida:
```
HTTP/1.1 200 OK
```
```json
{
  "shopId": 5,
  "country": "España",
  "city": "Santiago",
  "address": "Los Mallos, 12"
}
```
**Ejemplo  Error**
Salida
```
HTTP/1.1 404 Not found
```
**Ejemplo  Error**
```
HTTP/1.1 400 Bad request
```
#### Actualizar el precio del producto
```
http
PATCH /shops/{shopId}/product/{productId}
```
Json entrada
```json
{
   "price" : 14.5
}
```

Salida:
```
HTTP/1.1 200 OK
```
**Ejemplo  Error**
Salida
```
HTTP/1.1 404 Not found
```
**Ejemplo  Error**
```
HTTP/1.1 400 Bad request
```
Json salida 
```json
{
    "message": "Price cannot be empty"
}
```
#### Listado con filtros de tiendas 
```
http
GET /shops/filter?=
```
Ejemplo
```
GET /shop/filter?country=España&products_min=10
```
Json respuesta
```json
[
    {
        "shopId": 4,
        "country": "España",
        "city": "Coruña",
        "address": "Av. Finisterre"
    },
    
    {
        "shopId": 6,
        "country": "España",
        "city": "Santiago",
        "address": "Av Orense"
    }
]
```
Ejemplo  Error
```
HTTP/1.1 404 Not found
```



