package admin.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import admin.dto.ProductDTO;

/**
 * 상품 DAO
 */
public class ProductDAO {

    private static final ProductDAO INSTANCE = new ProductDAO();
    private final List<ProductDTO> products = new ArrayList<>();

    private ProductDAO() {
        long ts1 = parseTime("2025-01-15T09:00:00Z");
        long ts2 = parseTime("2026-02-06T14:00:00Z");
        long ts3 = parseTime("2026-02-06T14:30:00Z");
        long ts4 = parseTime("2026-02-06T15:00:00Z");

        products.add(new ProductDTO(1, 3, "테스트", "테스트", 20400, "INACTIVE",
                "/img/test.gif", "국내산", 400,
                Arrays.asList("/img/test.gif", "/img/test.gif"), ts1, ts1));
        products.add(new ProductDTO(1000001664, 1, "도드람한돈 목심 에어프라이어용 500g",
                "에어프라이어로 간편하게 조리 가능한 도드람한돈 프리미엄 목심.", 16000, "ACTIVE",
                "/img/product/1000001664_main_04.jpg", "국내산(돼지고기)", 500,
                Arrays.asList("/img/product/6692560fe610a50de8da40bb5c6f8cff_144342.png"), ts2, ts2));
        products.add(new ProductDTO(1000000565, 1, "도드람한돈 칼집구이용 삼겹살 500g",
                "칼집을 넣어 부드럽고 맛있게 구워지는 도드람한돈 삼겹살.", 18500, "ACTIVE",
                "/img/product/1000000565_main_069.jpg", "국내산(돼지고기)", 500,
                Arrays.asList("/img/product/3f8e7d2f132144127fd9b42e10e36bdd_134024.png"), ts2, ts2));
        products.add(new ProductDTO(1000001661, 1, "도드람한돈 목심바비큐용 500g",
                "바비큐에 최적화된 두툼한 도드람한돈 목심.", 17000, "ACTIVE",
                "/img/product/1000001661_main_082.jpg", "국내산(돼지고기)", 500,
                Arrays.asList("/img/product/5304b775a41b3538f78dea57d1998cf1_143935.png"), ts2, ts2));
        products.add(new ProductDTO(1000001660, 1, "도드람한돈 삼겹살 바비큐용 500g",
                "야외 바비큐에 꼭 맞는 프리미엄 도드람한돈 삼겹살.", 15000, "ACTIVE",
                "/img/product/1000001660_main_06.jpg", "국내산(돼지고기)", 500,
                Arrays.asList("/img/product/f12986c60ed5927d63f854b22ab61e3f_143906.png"), ts2, ts2));
        products.add(new ProductDTO(1000001137, 1, "도드람한돈 토시살(칼집) 400g",
                "칼집이 들어간 부드러운 도드람한돈 토시살. 구이용 최적.", 8900, "ACTIVE",
                "/img/product/1000001137_main_067.jpg", "국내산(돼지고기)", 400,
                Arrays.asList("/img/product/9d7a15029bbb0f15d51b9ec60fb560db_143815.png"), ts2, ts2));

        products.add(new ProductDTO(1000000515, 2, "한우 등심 300g",
                "마블링 풍부한 프리미엄 한우 등심. 구이용 최적.", 31500, "ACTIVE",
                "/img/product/1000000515_main.jpg", "국내산(한우)", 300,
                Arrays.asList("/img/product/1000000515_detail.png"), ts3, ts3));
        products.add(new ProductDTO(1000000514, 2, "한우 불고기 300g",
                "부드럽고 달콤한 한우 불고기용.", 14100, "DISCONTINUED",
                null, "국내산(한우)", 300,
                Collections.emptyList(), ts3, ts3));
        products.add(new ProductDTO(1000000513, 2, "한우 채끝 300g",
                "풍미 깊은 프리미엄 한우 채끝. 스테이크용 최적.", 36000, "DISCONTINUED",
                null, "국내산(한우)", 300,
                Collections.emptyList(), ts3, ts3));
        products.add(new ProductDTO(1000000512, 2, "한우 국거리 300g",
                "깊은 육수가 우러나는 한우 국거리용.", 12600, "DISCONTINUED",
                null, "국내산(한우)", 300,
                Collections.emptyList(), ts3, ts3));

        products.add(new ProductDTO(1000001851, 5, "도드람한돈 4구 삼목세트",
                "삼겹살과 목심이 함께 구성된 프리미엄 선물세트.", 75000, "ACTIVE",
                "/img/product/1000001851_main.png", "국내산(돼지고기)", 2000,
                Arrays.asList("/img/product/1000001851_detail.png"), ts4, ts4));
        products.add(new ProductDTO(1000000158, 5, "도드람한돈 으뜸구이세트2호",
                "구이용으로 최적화된 도드람한돈 프리미엄 선물세트.", 70000, "ACTIVE",
                "/img/product/1000000158_main.png", "국내산(돼지고기)", 1600,
                Arrays.asList("/img/product/1000000158_detail.png"), ts4, ts4));
    }

    public static ProductDAO getInstance() { return INSTANCE; }

    public List<ProductDTO> getAll() {
        return Collections.unmodifiableList(products);
    }

    public ProductDTO findByNumber(int productNumber) {
        return products.stream()
                .filter(p -> p.getProductNumber() == productNumber)
                .findFirst().orElse(null);
    }

    public List<ProductDTO> findByCategory(int categoryNumber) {
        return products.stream()
                .filter(p -> p.getCategoryNumber() == categoryNumber)
                .collect(Collectors.toList());
    }

    /** 상품 추가 (번호 자동 부여) */
    public ProductDTO add(int categoryNumber, String name, String description,
                          int price, String status, String thumbnailImage,
                          String origin, int weight, List<String> detailImages) {
        int newNumber = products.stream()
                .mapToInt(ProductDTO::getProductNumber).max().orElse(0) + 1;
        long now = System.currentTimeMillis();
        ProductDTO p = new ProductDTO(newNumber, categoryNumber, name, description,
                price, status, thumbnailImage, origin, weight,
                detailImages != null ? detailImages : Collections.emptyList(), now, now);
        products.add(p);
        return p;
    }

    /** 상품 수정 */
    public boolean update(int productNumber, int categoryNumber, String name,
                          String description, int price, String status,
                          String thumbnailImage, String origin, int weight,
                          List<String> detailImages) {
        ProductDTO p = findByNumber(productNumber);
        if (p == null) return false;
        p.setCategoryNumber(categoryNumber);
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setStatus(status);
        p.setThumbnailImage(thumbnailImage);
        p.setOrigin(origin);
        p.setWeight(weight);
        if (detailImages != null) p.setDetailImages(detailImages);
        p.setUpdatedAt(System.currentTimeMillis());
        return true;
    }

    /** 상품 삭제 */
    public boolean delete(int productNumber) {
        return products.removeIf(p -> p.getProductNumber() == productNumber);
    }

    private static long parseTime(String iso) {
        return java.time.Instant.parse(iso).toEpochMilli();
    }
}
