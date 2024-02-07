package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemEntity {
    private String code;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
}
