package admin.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import admin.dto.OrderDTO;
import admin.dto.OrderItemDTO;
import admin.dto.ProductDTO;

/**
 * 주문 DAO
 */
public class OrderDAO {

    private static final OrderDAO INSTANCE = new OrderDAO();
    private final List<OrderDTO> orders = new ArrayList<>();

    private OrderDAO() {
        orders.add(makeOrder(1001, "2026-01-10", "DELIVERED",
                "정문주", "010-1234-5678", "kahyou222@gmail.com",
                "정문주", "010-1234-5678", "서울특별시 강남구 테헤란로 123, 4층 401호",
                Arrays.asList(item(1000001664, 2), item(1000000565, 1))));
        orders.add(makeOrder(1002, "2026-01-12", "DELIVERED",
                "조휘일", "010-9876-5432", "whyeil@naver.com",
                "조휘일", "010-9876-5432", "경기도 성남시 분당구 판교역로 235, 7층",
                Arrays.asList(item(1000000565, 1))));
        orders.add(makeOrder(1003, "2026-01-15", "CANCELLED",
                "이름4", "010-2222-3333", "user4@jfs.rf.gd",
                "이름4", "010-2222-3333", "부산광역시 해운대구 우동 센텀중앙로 48, 12층",
                Arrays.asList(item(1000001661, 1), item(1000001660, 2), item(1000001137, 1))));
        orders.add(makeOrder(1004, "2026-01-18", "SHIPPING_IN_PROGRESS",
                "정문주", "010-1234-5678", "kahyou222@gmail.com",
                "김민수", "010-5555-7777", "대전광역시 유성구 대학로 99, 과학기술원 기숙사 B동 305호",
                Arrays.asList(item(1000001660, 1))));
        orders.add(makeOrder(1005, "2026-01-20", "PREPARING_PRODUCT",
                "름이5", "010-4444-8888", "user5@jfs.rf.gd",
                "박영희", "010-6666-9999", "인천광역시 연수구 송도과학로 32, 송도타워 15층",
                Arrays.asList(item(1000001137, 2), item(1000000515, 1))));
        orders.add(makeOrder(1006, "2026-01-22", "PAYMENT_PENDING",
                "이름6", "010-3333-1111", "user6@jfs.rf.gd",
                "이름6", "010-3333-1111", "대구광역시 수성구 들안로 67, 수성아파트 102동 803호",
                Arrays.asList(item(1000000515, 1))));
        orders.add(makeOrder(1007, "2026-01-25", "SHIPPING_PENDING",
                "조휘일", "010-9876-5432", "whyeil@naver.com",
                "조부모님", "010-7777-2222", "전라남도 순천시 장천로 51, 순천만아파트 3동 201호",
                Arrays.asList(item(1000001851, 1))));
        orders.add(makeOrder(1008, "2026-01-28", "RETURN_REQUESTED",
                "름이7", "010-8888-4444", "user7@jfs.rf.gd",
                "름이7", "010-8888-4444", "경기도 고양시 일산동구 중앙로 1036, 웨스턴돔 B1층",
                Arrays.asList(item(1000000158, 1), item(1000001664, 3))));
        orders.add(makeOrder(1009, "2026-02-01", "CANCEL_REQUESTED",
                "이름8", "010-1111-5555", "user8@jfs.rf.gd",
                "이름8", "010-1111-5555", "울산광역시 남구 삼산로 282, 삼산타운 5동 1501호",
                Arrays.asList(item(1, 5))));
        orders.add(makeOrder(1010, "2026-02-05", "DELIVERED",
                "정문주", "010-1234-5678", "kahyou222@gmail.com",
                "정문주", "010-1234-5678", "서울특별시 강남구 테헤란로 123, 4층 401호",
                Arrays.asList(item(1000001664, 1), item(1000001661, 1),
                        item(1000001660, 1), item(1000001137, 1))));
    }

    public static OrderDAO getInstance() { return INSTANCE; }

    private static OrderItemDTO item(int productNumber, int quantity) {
        return new OrderItemDTO(productNumber, quantity);
    }

    private static OrderDTO makeOrder(int orderNumber, String orderDate, String orderState,
                                      String ordererName, String ordererPhone, String ordererEmail,
                                      String receiverName, String receiverPhone, String receiverAddress,
                                      List<OrderItemDTO> items) {
        OrderDTO o = new OrderDTO();
        o.setOrderNumber(orderNumber);
        o.setOrderDate(orderDate);
        o.setOrderState(orderState);
        o.setOrdererName(ordererName);
        o.setOrdererPhone(ordererPhone);
        o.setOrdererEmail(ordererEmail);
        o.setReceiverName(receiverName);
        o.setReceiverPhone(receiverPhone);
        o.setReceiverAddress(receiverAddress);
        o.setItems(items);
        return o;
    }

    public List<OrderDTO> getAll() {
        return Collections.unmodifiableList(orders);
    }

    public OrderDTO findByOrderNumber(int orderNumber) {
        return orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst().orElse(null);
    }

    /** 상품 정보를 조인한 enriched 목록 */
    public List<OrderDTO> getAllEnriched() {
        ProductDAO pd = ProductDAO.getInstance();
        List<OrderDTO> result = new ArrayList<>();
        for (OrderDTO order : orders) {
            result.add(enrich(order, pd));
        }
        return result;
    }

    /** 단일 주문 enriched */
    public OrderDTO findEnriched(int orderNumber) {
        OrderDTO order = findByOrderNumber(orderNumber);
        if (order == null) return null;
        return enrich(order, ProductDAO.getInstance());
    }

    private OrderDTO enrich(OrderDTO order, ProductDAO pd) {
        OrderDTO enriched = new OrderDTO();
        enriched.setOrderNumber(order.getOrderNumber());
        enriched.setOrderDate(order.getOrderDate());
        enriched.setOrderState(order.getOrderState());
        enriched.setOrdererName(order.getOrdererName());
        enriched.setOrdererPhone(order.getOrdererPhone());
        enriched.setOrdererEmail(order.getOrdererEmail());
        enriched.setReceiverName(order.getReceiverName());
        enriched.setReceiverPhone(order.getReceiverPhone());
        enriched.setReceiverAddress(order.getReceiverAddress());

        List<OrderItemDTO> enrichedItems = new ArrayList<>();
        int totalQuantity = 0;
        int totalAmount = 0;

        for (OrderItemDTO it : order.getItems()) {
            OrderItemDTO ei = new OrderItemDTO(it.getProductNumber(), it.getQuantity());
            ProductDTO product = pd.findByNumber(it.getProductNumber());

            String productName = product != null ? product.getName() : "삭제된 상품";
            int price = product != null ? product.getPrice() : 0;
            int subtotal = price * it.getQuantity();

            ei.setProductName(productName);
            ei.setPrice(price);
            ei.setSubtotal(subtotal);
            if (product != null) {
                ei.setThumbnailImage(product.getThumbnailImage());
                ei.setOrigin(product.getOrigin());
                ei.setWeight(product.getWeight());
            }

            totalQuantity += it.getQuantity();
            totalAmount += subtotal;
            enrichedItems.add(ei);
        }

        String firstName = enrichedItems.isEmpty() ? "상품 없음"
                : enrichedItems.get(0).getProductName();
        int otherCount = enrichedItems.size() - 1;
        String orderName = otherCount > 0
                ? firstName + " 외 " + otherCount + "건"
                : firstName;

        enriched.setItems(enrichedItems);
        enriched.setOrderName(orderName);
        enriched.setTotalQuantity(totalQuantity);
        enriched.setTotalAmount(totalAmount);
        return enriched;
    }

    /** 주문 상태 변경 */
    public boolean updateState(int orderNumber, String newState) {
        OrderDTO order = findByOrderNumber(orderNumber);
        if (order == null) return false;
        order.setOrderState(newState);
        return true;
    }

    /** 주문 삭제 */
    public boolean delete(int orderNumber) {
        return orders.removeIf(o -> o.getOrderNumber() == orderNumber);
    }
}
