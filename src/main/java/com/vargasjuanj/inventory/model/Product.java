package com.vargasjuanj.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product extends BaseEntity{
    private String name;
    private BigDecimal price;
    private int account;
    //seria agregación, el producto puede seguir existiendo por mas que la categoria ya no exista, es seteo
    @ManyToOne(fetch = FetchType.LAZY) //aca los productos pueden apuntar a su categoria, acceder a ella de forma mas facil. Ya en el caso que se quiera obtener todos los productos desde categoria, deberia ser algo bidireccionalidad. Pero en este caso la categoria no tiene conocimiento de la existencia de sus producñtos. solo con programacion
    @JsonIgnoreProperties({"hibernateLazyInitializer,handler"})
    private Category category;

    /*
    @Lob indica a JPA que maneje la columna como un tipo LOB en la base de datos, lo que generalmente permite almacenar grandes cantidades de datos binarios.
Configurar la recuperación como FetchType.LAZY significa que los datos de la columna no se cargarán automáticamente cuando se recupere la entidad de la base de datos, lo que puede mejorar el rendimiento al evitar cargar datos grandes innecesariamente.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "longblob") // Esta propiedad es necesaria porque por mas que primero se borre la propiedad en la bd, lo va a seguir tomando como blob(tinyblob), entonces con esto ya cambia y nos deja subir archivos grandes
    private byte[] picture;

    /*
    Exactamente, en el caso de la carga perezosa (lazy), Hibernate posterga la recuperación de los datos hasta que se accede a la propiedad de la entidad relacionada. Por lo tanto, al llamar a getCategory(), no se cargará la entidad Category de inmediato. Solo cuando accedes a los atributos de la categoría, como getName(), Hibernate realizará la consulta para recuperar la entidad Category de la base de datos. Esto puede mejorar el rendimiento de la aplicación al evitar la carga innecesaria de datos.
     */
    /*
    Si no se usa @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}), al intentar serializar la entidad a JSON, Hibernate intentará cargar las propiedades hibernateLazyInitializer y handler de la entidad, lo que puede provocar problemas como:

Ciclos infinitos: Si las entidades tienen relaciones circulares, como una referencia bidireccional entre dos entidades, Hibernate puede entrar en un ciclo infinito al intentar cargar todas las propiedades.

Carga innecesaria de datos: La carga de hibernateLazyInitializer y handler puede resultar en una sobrecarga de datos innecesarios al serializar la entidad a JSON, lo que afecta negativamente el rendimiento y el tamaño de la respuesta JSON.

     No necesariamente. Si bien la anotación `@JsonIgnoreProperties` y la exclusión del `hibernateLazyInitializer` y `handler` se pueden utilizar en contextos de bidireccionalidad, también son útiles en otros escenarios donde se desee serializar objetos Hibernate a JSON de una manera limpia y consistente.

Estas anotaciones son especialmente útiles cuando se trabaja con entidades JPA que tienen asociaciones de carga perezosa (lazy loading) y se serializan a JSON utilizando bibliotecas como Jackson. Al ignorar estas propiedades internas de Hibernate, se evita que aparezcan en la salida JSON, lo que mejora la legibilidad y la integridad de los datos serializados.

Entonces, aunque estas anotaciones pueden ser utilizadas en el contexto de la bidireccionalidad, su propósito principal es garantizar una serialización limpia y coherente de las entidades JPA a JSON.
     */

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
}
