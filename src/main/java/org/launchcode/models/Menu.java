package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int Id;

    @Size(min=3,max=15)
    private String name;

    @ManyToMany
    private List<Cheese> cheeses;

    public int getId(){
        return this.Id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }

    public Menu(){}

    public Menu(String name){
        this.name=name;
    }

    public void addItem(Cheese item){
        cheeses.add(item);
    }
    public List<Cheese> getCheeses(){return cheeses;};

}

