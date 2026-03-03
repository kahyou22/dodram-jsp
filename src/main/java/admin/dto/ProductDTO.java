package admin.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    // 상품 상태 enum
    public enum Status {
        ACTIVE("판매중"),
        INACTIVE("판매중지"),
        SOLD_OUT("품절"),
        DISCONTINUED("단종");

        private final String label;

        Status(String label) { this.label = label; }

        public String getKey() { return name(); }
        public String getLabel() { return label; }
    }

    private int productNumber;
    private int categoryNumber;
    private String name;
    private String description;
    private int price;
    private String status; // Status enum의 name() 값
    private String thumbnailImage;
    private String origin;
    private int weight;
    private List<String> detailImages;
    private long createdAt;
    private long updatedAt;

    /** 상태 키에 해당하는 한글 라벨 */
    public String getStatusLabel() {
        try {
            return Status.valueOf(status).getLabel();
        } catch (Exception e) {
            return status;
        }
    }
}
