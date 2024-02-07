package dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {


    private String oid;
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private String date;
    private String customerId;
    private List <OrderDetailsDTO> orderDetails;
}
