import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailsEntity {
    private String oid;
    private String code;
    private int qty;
    private boolean unitPrice;


}
