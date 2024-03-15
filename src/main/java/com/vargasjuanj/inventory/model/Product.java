package com.vargasjuanj.inventory.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product extends BaseEntity{
    private String name;
    private BigDecimal price;
    private int account;
    //seria agregación, el producto puede seguir existiendo por mas que la categoria ya no exista, es seteo
   // @JsonIgnore // sin esto salta error al serializar a json, Pero ignora categoria,
    // Quitandole el lazy la muestra sin problemas. Leer al final de la clase la explicación
    @ManyToOne(/*fetch = FetchType.LAZY*/) //aca los productos pueden apuntar a su categoria, acceder a ella de forma mas facil. Ya en el caso que se quiera obtener todos los productos desde categoria, deberia ser algo bidireccionalidad. Pero en este caso la categoria no tiene conocimiento de la existencia de sus producñtos. solo con programacion
    private Category category; // haciendo un dto de product no me funcionó la solución para que siga siendo lazy y no salte el error de bytebunded

    /*
    @Lob indica a JPA que maneje la columna como un tipo LOB en la base de datos, lo que generalmente permite almacenar grandes cantidades de datos binarios.
Configurar la recuperación como FetchType.LAZY significa que los datos de la columna no se cargarán automáticamente cuando se recupere la entidad de la base de datos, lo que puede mejorar el rendimiento al evitar cargar datos grandes innecesariamente.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "longblob") // Esta propiedad es necesaria porque por mas que primero se borre la propiedad en la bd, lo va a seguir tomando como blob(tinyblob), entonces con esto ya cambia y nos deja subir archivos grandes
    private byte[] picture;

      public Product() {
    }

    public Product(String name, BigDecimal price, int account) {
        this.name = name;
        this.price = price;
        this.account = account;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    /*
    El error que estás experimentando indica que Jackson (la biblioteca utilizada para la serialización a JSON) no puede serializar la propiedad category de tu entidad Product. Este error se debe a que Jackson no puede serializar las clases proxy creadas por Hibernate para las relaciones de carga tardía (lazy loading), como es el caso de org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor.

Para solucionar este problema, puedes probar algunas de estas soluciones:

Evitar la serialización de la propiedad category: Si no necesitas serializar la propiedad category en tu respuesta JSON, puedes ignorarla con la anotación @JsonIgnore en la propiedad o en la clase si es necesario. Esto evitará que Jackson intente serializar la propiedad problemática.

Forzar la inicialización de la propiedad category: Puedes forzar la inicialización de la propiedad category antes de serializar la entidad Product. Esto se puede lograr accediendo explícitamente a la propiedad category dentro de la transacción activa. Esto evitará que Hibernate cree proxies y permitirá que Jackson serialice la propiedad correctamente.

Crear un DTO (Objeto de Transferencia de Datos): Puedes crear un DTO que represente solo los datos que deseas devolver en tu respuesta JSON. Este DTO puede ser una clase simple con solo las propiedades que deseas serializar, evitando así cualquier problema con las clases proxy de Hibernate.

Configurar Jackson para ignorar clases proxy: Puedes configurar Jackson para ignorar las clases proxy de Hibernate. Esto se puede hacer a nivel de configuración de Jackson, indicando que ignore ciertos tipos de clases durante la serialización.

Aquí hay un ejemplo de cómo podrías implementar la solución 2, forzando la inicialización de la propiedad category antes de la serialización:

java
Copy code
@Transactional(readOnly = true)
public ResponseEntity<Respuesta> getProduct(@PathVariable Long id) {
    Product product = productService.findById(id);
    // Forzar la inicialización de la propiedad category si es necesario
    product.getCategory(); // Esto activará la carga perezosa (lazy loading) de la propiedad category
    // Ahora puedes devolver la respuesta JSON
    return ResponseEntity.ok(new Respuesta("success", "Operación exitosa", product));
}
Esto debería resolver el problema al asegurarte de que la propiedad category esté completamente inicializada antes de que Jackson intente serializarla.


User
ahi le quité el lazy y funcionó bien
ChatGPT
Excelente, me alegra que hayas encontrado una solución. Al quitar la carga perezosa (lazy loading) y cargar la propiedad category de manera eager (ansiosa), estás asegurando que Hibernate inicialice completamente la propiedad antes de que Jackson intente serializarla, evitando así el problema con las clases proxy.

Recuerda que cambiar la estrategia de carga (lazy vs eager) puede tener implicaciones en el rendimiento de tu aplicación, especialmente si estás cargando entidades relacionadas de manera ansiosa cuando podrían no ser necesarias. Es importante evaluar cuidadosamente las necesidades de tu aplicación y el impacto en el rendimiento al elegir la estrategia de carga adecuada para cada relación.





ChatGPT
Exacto, es importante tener en cuenta las implicaciones de cambiar la estrategia de carga de lazy a eager. La carga ansiosa puede resultar en consultas SQL adicionales y cargar más datos de los necesarios en la memoria, lo que podría afectar el rendimiento de tu aplicación, especialmente en escenarios donde se cargan muchas entidades relacionadas.

Algunas consideraciones adicionales:

Impacto en el rendimiento: Evalúa el impacto en el rendimiento de tu aplicación al cargar ansiosamente todas las propiedades relacionadas. Esto puede afectar negativamente el rendimiento, especialmente en consultas que devuelven grandes conjuntos de datos.

Uso selectivo de la carga ansiosa: Si solo necesitas cargar ciertas propiedades relacionadas en casos específicos, considera utilizar la carga ansiosa de manera selectiva. Puedes usar consultas JPQL o Criteria API para cargar selectivamente las propiedades que necesitas en cada contexto.

Monitorización y ajuste: Realiza un seguimiento del rendimiento de tu aplicación y ajusta la estrategia de carga según sea necesario. Puedes utilizar herramientas de monitorización de rendimiento y perfiles de consulta para identificar áreas de mejora.

Considera otras opciones: Además de cambiar la estrategia de carga, considera otras opciones como el uso de DTOs (Objetos de Transferencia de Datos) para representar los datos que necesitas en tu respuesta JSON. Esto te permite tener un control más granular sobre los datos que se devuelven sin cambiar la estrategia de carga de Hibernate.

En resumen, mientras que cambiar la estrategia de carga puede resolver el problema de serialización de las clases proxy de Hibernate, es importante evaluar cuidadosamente las implicaciones en el rendimiento y considerar otras opciones para optimizar el comportamiento de tu aplicación.
     */
}
