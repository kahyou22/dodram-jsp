package admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 항목 (주문 1건에 포함되는 개별 상품) DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private int productNumber;
    private int quantity;

    // --- enriched 필드 (상품 조인 후 채워짐) ---
    private String productName;
    private int price;
    private int subtotal;
    private String thumbnailImage;
    private String origin;
    private int weight;

    /** productNumber, quantity만 세팅하는 생성자 */
    public OrderItemDTO(int productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }
}
