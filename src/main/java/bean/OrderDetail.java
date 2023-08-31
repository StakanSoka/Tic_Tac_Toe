package bean;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "table_id")
    private int tableId;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderDetail() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail that)) return false;
        return id == that.id && tableId == that.tableId && item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableId, item);
    }
}
