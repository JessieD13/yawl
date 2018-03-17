package dao;

import org.hibernate.mapping.Set;

import java.io.Serializable;

/**
 * Created by apple on 2018/3/3.
 */
public class ExeQueue implements Serializable {
    private int id;
    private String wir_id;
    private Integer exe_order;

    public Integer getExe_order() {
        return exe_order;
    }

    public void setExe_order(Integer exe_order) {
        this.exe_order = exe_order;
    }

    public ExeQueue() {}

    public ExeQueue(String  wirid, int order_) {
        this.wir_id = wirid;
        this.exe_order = order_;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getWir_id() {
        return this.wir_id;
    }
    public void setWir_id(String wirid) {
        this.wir_id = wirid;
    }

    public Integer getOrder() {
        return this.exe_order;
    }
    public void setOrder(int order_) {
        this.exe_order = order_;
    }
}
