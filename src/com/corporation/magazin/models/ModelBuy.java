package com.corporation.magazin.models;

/**
 * Created by user on 05.03.2019.
 */
public class ModelBuy {
    private Integer id;
    private Integer id_tovar;
    private String name;
    private Double count;
    private String client;
    private String data;
    private Double itogo;

    public ModelBuy(Integer id, Integer id_tovar, String name, Double count, String client, String data, Double itogo) {
        this.id = id;
        this.id_tovar = id_tovar;
        this.name = name;
        this.count = count;
        this.client = client;
        this.data = data;
        this.itogo = itogo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_tovar() {
        return id_tovar;
    }

    public void setId_tovar(Integer id_tovar) {
        this.id_tovar = id_tovar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getItogo() {
        return itogo;
    }

    public void setItogo(Double itogo) {
        this.itogo = itogo;
    }

    @Override
    public String toString() {
        return "ModelBuy{" +
                "id=" + id +
                ", id_tovar=" + id_tovar +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", client='" + client + '\'' +
                ", data='" + data + '\'' +
                ", itogo=" + itogo +
                '}';
    }
}
