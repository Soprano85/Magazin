package com.corporation.magazin.models;

/**
 * Created by user on 03.02.2019.
 */
public class ModelCategory {
    private Integer id;
       private String name;

       public ModelCategory(Integer id, String name) {
           this.id = id;
           this.name = name;
       }

       public Integer getId() {
           return id;
       }

       public void setId(Integer id) {
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
           return "ModelCategory{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   '}';
       }
}
