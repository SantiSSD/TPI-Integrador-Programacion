# FoodStoreTPI - Sistema de Gestión de Pedidos de Comida

Sistema de consola desarrollado en **Java** para la gestión de productos, categorías, usuarios y pedidos de un negocio de comidas. Implementa un menú interactivo con operaciones CRUD completas y almacenamiento en memoria mediante Colecciones.

---

## Tecnologías Utilizadas

- **Java 21**
- **NetBeans** (IDE)
- **Programación Orientada a Objetos** (POO)
- **Colecciones en memoria** (ArrayList)
- **Manejo de Excepciones** personalizadas

---

## Estructura del Proyecto

```
src/
├── entities/          # Clases del modelo (UML)
│   ├── Base.java         (Clase abstracta)
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Usuario.java
│   ├── Pedido.java
│   └── DetallePedido.java
├── enums/             # Enumeraciones
│   ├── Estado.java       (PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO)
│   ├── FormaPago.java    (EFECTIVO, TARJETA, TRANSFERENCIA)
│   └── Rol.java          (ADMIN, USUARIO)
├── excepciones/       # Excepciones personalizadas
│   ├── DatosInvalidosException.java
│   ├── EntidadDuplicadaException.java
│   ├── EntidadNoEncontradaException.java
│   └── StockInvalidoException.java
├── interfaces/        # Interfaces
│   └── Calculable.java
├── services/          # Lógica de negocio
│   ├── CategoriaService.java
│   ├── ProductoService.java
│   ├── UsuarioService.java
│   └── GestorPedidos.java
└── vista/             # Interfaz de consola
    └── Main.java
```

---

## Cómo Ejecutar

### Desde NetBeans:
1. Abrir NetBeans
2. Ir a **File > Open Project** y seleccionar la carpeta del proyecto
3. Ejecutar **Clean and Build** (Shift + F11)
4. Ejecutar **Run** (F6)

### Desde terminal (si tenés Java instalado):

```bash
cd FoodStoreTPI
javac -d build/classes src/**/*.java
java -cp build/classes vista.Main
```

---

## Funcionalidades del Menú

### Menú Principal
```
1. Gestión de Categorías
2. Gestión de Productos
3. Gestión de Usuarios
4. Gestión de Pedidos
0. Salir
```

### Categorías (HU-CAT-01 a 04)
- **Listar** - Muestra categorías activas
- **Crear** - Solicita nombre y descripción (valida nombre único)
- **Editar** - Permite modificar nombre y descripción por ID
- **Eliminar** - Baja lógica con confirmación (S/N)

### Productos (HU-PROD-01 a 04)
- **Listar** - Muestra productos activos con stock y categoría
- **Crear** - Solicita datos y lo asocia a una categoría existente (valida stock >= 0, precio > 0)
- **Editar** - Permite modificar todos los campos
- **Eliminar** - Baja lógica con confirmación (S/N)

### Usuarios (HU-USR-01 a 04)
- **Listar** - Muestra usuarios activos
- **Crear** - Solicita datos personales (valida email único)
- **Editar** - Permite modificar datos y rol
- **Eliminar** - Baja lógica con confirmación (S/N)

### Pedidos (HU-PED-01 a 04)
- **Registrar Pedido** - Selecciona cliente, productos y cantidades. Descuenta stock automáticamente
- **Listar Historial** - Muestra todos los pedidos registrados
- **Actualizar Estado / Forma de Pago** - Permite cambiar a CONFIRMADO, TERMINADO o CANCELADO
- **Eliminar Pedido** - Baja lógica con confirmación (S/N)

---

## Modelo UML

| Clase | Hereda de | Atributos clave |
|-------|-----------|-----------------|
| **Base** (abstracta) | - | id, eliminado, createdAt |
| **Categoria** | Base | nombre, descripcion |
| **Producto** | Base | nombre, precio, descripcion, stock, imagen, disponible, categoria |
| **Usuario** | Base | nombre, apellido, mail (único), celular, contrasena, rol |
| **Pedido** | Base + Calculable | fecha, estado, total, formaPago, usuario, detalles |
| **DetallePedido** | Base | producto, cantidad, subtotal |

---

## Diagrama de Estados del Pedido

```
PENDIENTE → CONFIRMADO → TERMINADO
     ↓
  CANCELADO
```

---

## Reglas de Negocio

- No se permiten productos con precio < 0 o stock < 0
- No se permiten categorías con nombre duplicado
- No se permiten usuarios con email duplicado
- No se permiten pedidos sin usuario o sin productos
- Al crear un pedido, se valida stock suficiente
- Todas las bajas son lógicas (soft delete)

---

## Video Demostrativo

*(Enlace próximamente)*

---

## Documentación

*(Enlace próximamente)*

---

## Autor

Trabajo Práctico Integrador - Programación 2
Tecnicatura Universitaria en Programación (A Distancia)
