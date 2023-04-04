package domain;

import java.io.Serializable;

/**
 * the class Entity used for extending other classes
 * @param <ID> generic implementation for keeping things organised
 */
public class Entity<ID>  {

    private ID id;

    public ID getId() {
        return id;
    }


    public void setId(ID id) {
        this.id = id;
    }
}