package net.therap.viewModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author khandaker.maruf
 * @since 8/4/22
 */
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItem<T> {

    private T item;
    private int quantity;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
