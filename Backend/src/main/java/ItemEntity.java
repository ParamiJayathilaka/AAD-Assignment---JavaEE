import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemEntity {
    String code;
    String description;
    int qtyOnHand;
    double unitPrice;
}
