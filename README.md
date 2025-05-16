# 🛒 Proyecto - Gestión de Productos y Tiendas

Este proyecto proporciona una API REST para la gestión de productos y tiendas. Permite realizar operaciones CRUD sobre
productos y tiendas, así como filtrado y modificaciones específicas.

---

## Funcionalidades ##

### 📦 Productos

- **Alta de productos** → Permite añadir nuevos productos a la base de datos.
- **Baja de productos** → Elimina un producto de la base de datos.
- **Modificación de productos** → Permite modificar el nombre de los productos.
- **Filtrar productos** → Permite buscar productos por **name** y **price**.

### 🏬 Tienda

- **Alta de tienda** → Permite registrar una nueva .
- **Baja de tienda** → Elimina una existente.
- **Modificación de tienda** → Permite actualizar los datos de una tienda.
- **Añadir un producto a tienda** → Asigna un producto a una tienda específica.
- **Modificar un producto de la tienda** → Cambiar el precio un producto de una tienda
- **Filtrar tiendas** → Permite buscar tiendas por **city, country y address**.

### 👨 Cliente

- **Alta de cliente** → Permite registrar uno nuevo .
- **Baja de cliente** → Elimina uno existente.
- **Modificación de cliente** → Permite actualizar los datos de un cliente.
- **Filtrado de clientes** → Permite buscar clientes por **name, phone y email**.

### 🎟️ Compra

- **Alta de compra** → Permite registrar una nueva .
- **Baja de compra** → Elimina una existente.
- **Modificación de compra** → Permite actualizar los datos de una compra.
- **Añadir un producto a una compra** → Asigna un producto a una tienda específica.
- **Filtrar compras** → Permite buscar compras por **customerId, lista de productos tienda, price, shopping**.

---

## ENDPOINTS ##

### Productos

#### Obtener los productos  pudiendo aplicar filtros

```
http
GET /products?
```

Ejemplo JSON respuesta

```json
{
    "content": [
        {
            "productId": 1,
            "name": "Zumos",
            "shop": [
                {
                    "productInShopId": 1,
                    "shopId": 1,
                    "price": 7.90
                }
            ]
        },
        {
            "productId": 2,
            "name": "Bebidas energéticas",
            "shop": [
                {
                    "productInShopId": 2,
                    "shopId": 2,
                    "price": 8.90
                }
            ]
        },
        {
            "productId": 3,
            "name": "Agua",
            "shop": [
                {
                    "productInShopId": 3,
                    "shopId": 3,
                    "price": 3.90
                }
            ]
        }
    ],
    "totalElements": 3,
    "totalPages": 1
}
```

Ejemplo JSON respuesta con filtros

```
http://localhost:8080/products?name=fru
```

```json
{
    "content": [
        {
            "productId": 5,
            "name": "Fruta",
            "shop": [
                {
                    "productInShopId": 5,
                    "shopId": 5,
                    "price": 3.20
                }
            ]
        }
    ],
    "totalElements": 1,
    "totalPages": 1
}
```

#### Obtener producto por ID

```
http
GET /products/{productId}
```

Ejemplo JSON respuesta

```json
{
  "productId": 1,
  "name": "Zumos",
  "shop": [
    {
      "productInShopId": 1,
      "shopId": 1,
      "price": 7.90
    }
  ]
}
```

Ejemplo Producto no existente

```
HTTP/1.1 404 Not Found
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

**Ejemplo Error**

```
HTTP/1.1 400 Bad Request
```

```json
{
  "message": "Fields misentered"
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

Ejemplo Error

```
HTTP/1.1 404 Not Found
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

**Ejemplo Error**

```
HTTP/1.1 400 Bad request
```

Json Respuesta

```json
{
    "message": "Fields misentered"
}
```

### **Tienda** ###

#### Obtener todas las tiendas pudiendo aplicar filtros

```
http
GET /shops?
```

Ejemplo salida

```json
    "content": [
        {
            "shopId": 1,
            "country": "Argentina",
            "city": "Buenos Aires",
            "address": "Alfredo R. Bufano 2701-2799"
        },
        {
            "shopId": 2,
            "country": "Argentina",
            "city": "Córdoba",
            "address": "Av. Vélez Sarsfield"
        },
        {
            "shopId": 3,
            "country": "España",
            "city": "A Coruña",
            "address": "Rúa Río Brexa, 5 "
        }
    ],
    "totalElements": 3,
    "totalPages": 1
```

Ejemplo JSON respuesta con filtros

```
http
GET shops?country=es
```

Json respuesta

```json
{
    "content": [
        {
            "shopId": 3,
            "country": "España",
            "city": "A Coruña",
            "address": "Rúa Río Brexa, 5 "
        }
    ],
    "totalElements": 1,
    "totalPages": 1
}
```

Ejemplo Error

```
HTTP/1.1 404 Not Found
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
  "country": "Argentina",
  "city": "Buenos Aires",
  "address": "Alfredo R. Bufano 2701-2799"
}
```

Ejemplo si no encuentra tiendas

```
HTTP/1.1 404 Not Found
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
    "shopId": 4,
    "country": "España",
    "city": "Santiago",
    "address":"Av Buenos Aires"
}
```

Ejemplo error

```
HTTP/1.1 400 Bad Request
```

```json
{
    "message": "Fields misentered"
}
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
  "productInShopId": 6,
  "productId": 2,
  "shopId": 3,
  "price": 10.5
}
```

**Ejemplo Error**

```
HTTP/1.1 409 Conflict
```

#### Actualizar tienda

```
http
PUT `/shops/{shopId}
```

Json entrada

```json
{
    "country": "Colombia",
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

**Ejemplo Error**
Salida

```
HTTP/1.1 404 Not Found
```

**Ejemplo Error**

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

**Ejemplo Error**
Salida

```
HTTP/1.1 404 Not Found
```

**Ejemplo Error**

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
   "price" : 1
}
```

Salida:

```
HTTP/1.1 200 OK
```

```json
{
  "productInShopId": 2,
  "productId": 2,
  "shopId": 2,
  "price": 1
}
```

**Ejemplo Error**
Salida

```
HTTP/1.1 404 Not Found
```

**Ejemplo Error**

```
HTTP/1.1 400 Bad request
```

Json salida

```json
{
    "message": "Price cannot be empty"
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

Ejemplo Error

```
HTTP/1.1 404 Not Found
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
    "productInShopId": 1,
    "productId" : 1,
    "shopId": 1,
    "price": 10.5
}
```

**Ejemplo Error**

```
HTTP/1.1 404 Not Found
```

**Ejemplo Error**

```
HTTP/1.1 400 Bad Request
````

### **Cliente** ###

#### Obtener todos los clientes

```
http
GET /customers
```

Ejemplo salida

```json
[
  {
    "customerId": 1,
    "name": "Alice Johnson",
    "phone": 123123123,
    "email": "alice@example.com"
  },
  {
    "customerId": 2,
    "name": "Bob Smith",
    "phone": 111111111,
    "email": "bob@example.com"
  },
  {
    "customerId": 3,
    "name": "Carlos Garcia",
    "phone": 999999999,
    "email": "carlos@example.com"
  }
]

```

#### Listado con filtros de clientes

```
http
GET /customers?=
```

Ejemplo

```
GET /customers?name=ca
```

Json respuesta

```json
[
  {
    "customerId": 3,
    "name": "Carlos Garcia",
    "phone": 999999999,
    "email": "carlos@example.com"
  }
]
```

Ejemplo Error

```
HTTP/1.1 404 Not Found
```

#### Obtener un cliente por id

```
http
GET /customers/{customerId}
```

Ejemplo salida

```json
{
  "customerId": 1,
  "name": "Alice Johnson",
  "phone": 123123123,
  "email": "alice@example.com"
}
```

Salida

```
HTTP/1.1 200 OK
```

Ejemplo si no encuentra clientes

```
HTTP/1.1 404 Not Found
```

#### Crear un cliente

```
http
POST `/customers`
```

Json entrada

```json
{
  "name": "a",
  "phone": 999999999,
  "email": "alice@a.com"
}
```

Salida

```
Status: 201 Created
```

```json
{
  "customerId": 5,
  "name": "a",
  "phone": 999999999,
  "email": "alice@a.com"
}
```

**Ejemplo error**

Json entrada

```json
{
  "name": "a",
  "phone": 0,
  "email": "alice@a.com"
}
```

Salida

```json
{
   "phone": "Fields misentered"
}
```

```
HTTP/1.1 400 BAD REQUEST
```

#### Actualizar cliente

```
http
PUT `/customers/{customerId}
```

Json entrada

```json
{
  "name": "aaaaa",
  "phone":123456780,
  "email": "a@gmail.com"
}
```

Salida:

```
HTTP/1.1 200 OK
```

```json
{
  "customerId": 1,
  "name": "aaaaa",
  "phone": 123456780,
  "email": "a@gmail.com"
}
```

**Ejemplo Error**
Salida

```
HTTP/1.1 404 Not Found
```

**Ejemplo Error**

```json
{
  "name": "",
  "phone":123456780,
  "email": "a@gmail.com"
}
```

```
HTTP/1.1 400 Bad request
```

Json salida

```json
{
  "name": "The name can only have letters and spaces"
}
```

#### Actualizar parcialmente el cliente

```
http
PATCH /customers/{customerId}
```

Json entrada

```json
{
  "name": "abcd",
  "phone": 123456789
}
```

Salida:

```
HTTP/1.1 200 OK
```

```json
{
  "customerId": 1,
  "name": "abcd",
  "phone": 123456789,
  "email": "a@gmail.com"
}
```

**Ejemplo Error**
Salida

```
HTTP/1.1 404 Not Found
```

**Ejemplo Error**

```
HTTP/1.1 400 Bad request
```

Json salida

```json
{
  "name": "must not be blank"
}
```

#### Dar de baja un cliente

```
http
DELETE /customers/{customerId}
```

Salida:

```
HTTP/1.1 200 OK
```

Ejemplo Error

```
HTTP/1.1 404 Not Found
```

### **Compra** ###

#### Obtener todas las compras

```
http
GET /purchases
```

Ejemplo salida

```json
[
  {
    "purchaseId": 1,
    "customer": {
      "customerId": 1,
      "name": "abcd",
      "phone": 123456789,
      "email": "a@gmail.com"
    },
    "products": [
      {
        "productInShopId": 1,
        "productId": 1,
        "shopId": 1,
        "price": 7.90
      }
    ],
    "totalPrice": 7.90,
    "shopping": true
  },
  {
    "purchaseId": 2,
    "customer": {
      "customerId": 4,
      "name": "Sara Lopez",
      "phone": 654654654,
      "email": "sara@example.com"
    },
    "products": [],
    "totalPrice": 0,
    "shopping": true
  },
  {
    "purchaseId": 3,
    "customer": {
      "customerId": 2,
      "name": "Bob Smith",
      "phone": 111111111,
      "email": "bob@example.com"
    },
    "products": [],
    "totalPrice": 0,
    "shopping": true
  }
]

```

#### Listado con filtros de compras

```
http
GET /purchases?=
```

Ejemplo

```
GET /purchases?shopping=false
```

Json respuesta

```json
[
  {
    "purchaseId": 1,
    "customer": {
      "customerId": 1,
      "name": "abcd",
      "phone": 123456789,
      "email": "a@gmail.com"
    },
    "products": [
      {
        "productInShopId": 1,
        "productId": 1,
        "shopId": 1,
        "price": 7.90
      }
    ],
    "totalPrice": 7.90,
    "shopping": false
  }
]
```

Ejemplo Error

```
HTTP/1.1 404 Not Found
```

#### Obtener una tienda por id

```
http
GET /purchases/{purchaseId}
```

Ejemplo salida

```json
{
  "purchaseId": 2,
  "customer": {
    "customerId": 4,
    "name": "Sara Lopez",
    "phone": 654654654,
    "email": "sara@example.com"
  },
  "products": [
    {
      "productInShopId": 1,
      "productId": 1,
      "shopId": 1,
      "price": 7.90
    }
  ],
  "totalPrice": 7.90,
  "shopping": false
}
```

Ejemplo si no encuentra compras

```
HTTP/1.1 404 Not Found
```

#### Crear una compra

```
http
POST `/purchases`
```

Json entrada

```json
{
  "customerId" : 3
}
```

Salida

```
Status: 201 Created
```

```json
{
  "purchaseId": 7,
  "customer": {
    "customerId": 3,
    "name": "Carlos Garcia",
    "phone": 999999999,
    "email": "carlos@example.com"
  },
  "products": [],
  "totalPrice": 0,
  "shopping": true
}
```

**Ejemplo error**

Json entrada

```json
{
    "customerId" : 6
}
```

```
HTTP/1.1 404 Not Found
```

#### Añadir un producto a una compra

```
http
POST /pruchases/{purchaseId}/productInShop/{productInShopId}
```

Respuesta

```
HTTP/1.1 200 OK
```

```json
{
  "purchaseId": 2,
  "customer": {
    "customerId": 4,
    "name": "Sara Lopez",
    "phone": 654654654,
    "email": "sara@example.com"
  },
  "products": [
    {
      "productInShopId": 1,
      "productId": 1,
      "shopId": 1,
      "price": 7.90
    }
  ],
  "totalPrice": 7.90,
  "shopping": true
}
```

**Ejemplo Error**

Si la compra está finalizada

Json Salida

```
HTTP/1.1 400 Bad Request
```

#### Actualizar parcialmente la compra

```
http
PATCH /purchases/{purchaseId}/finish
```

Salida:

```
HTTP/1.1 200 OK
```

```json
{
  "purchaseId": 2,
  "customer": {
    "customerId": 4,
    "name": "Sara Lopez",
    "phone": 654654654,
    "email": "sara@example.com"
  },
  "products": [
    {
      "productInShopId": 1,
      "productId": 1,
      "shopId": 1,
      "price": 7.90
    }
  ],
  "totalPrice": 7.90,
  "shopping": false
}
```

**Ejemplo Error**

```
HTTP/1.1 400 Bad request
```

#### Dar de baja una compra

```
http
DELETE /purchases/{purchaseId}
```

Salida:

```
HTTP/1.1 200 OK
```

Ejemplo Error

```
HTTP/1.1 404 Not Found
```

#### Borrar un producto de la compra

```
http
DELETE /purchases/{purchaseId}/productInShop/{productInShopId}
```

Respuesta

```
HTTP/1.1 200 OK
```

**Ejemplo Error**

```
HTTP/1.1 404 Not Found
```
