package zpark.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class City implements Serializable {

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City [id=" + id + ", name=" + name + "]";
    }

    public City() {
        super();
    }

    public City(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}
