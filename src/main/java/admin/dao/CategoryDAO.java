package admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import admin.dto.CategoryDTO;

/**
 * 카테고리 DAO
 */
public class CategoryDAO {

    private static final CategoryDAO INSTANCE = new CategoryDAO();
    private final List<CategoryDTO> categories = new ArrayList<>();

    private CategoryDAO() {
        categories.add(new CategoryDTO(1, "한돈", "신선한 국내산 돼지고기", "🥩",
                parseTime("2025-01-01T00:00:00Z")));
        categories.add(new CategoryDTO(2, "한우", "신선한 국내산 한우", "🥓",
                parseTime("2025-01-01T00:00:00Z")));
        categories.add(new CategoryDTO(3, "테스트", "테스트", "🚀",
                parseTime("2025-01-01T00:00:00Z")));
        categories.add(new CategoryDTO(5, "선물세트", "", "🎁",
                parseTime("2025-01-10T00:00:00Z")));
    }

    public static CategoryDAO getInstance() { return INSTANCE; }

    public List<CategoryDTO> getAll() {
        return Collections.unmodifiableList(categories);
    }

    public CategoryDTO findByNumber(int categoryNumber) {
        return categories.stream()
                .filter(c -> c.getCategoryNumber() == categoryNumber)
                .findFirst().orElse(null);
    }

    /** 카테고리 추가 (번호 자동 부여) */
    public CategoryDTO add(String name, String description, String icon) {
        int newNumber = categories.stream()
                .mapToInt(CategoryDTO::getCategoryNumber).max().orElse(0) + 1;
        CategoryDTO cat = new CategoryDTO(newNumber, name, description, icon,
                System.currentTimeMillis());
        categories.add(cat);
        return cat;
    }

    /** 카테고리 수정 */
    public boolean update(int categoryNumber, String name, String description, String icon) {
        CategoryDTO cat = findByNumber(categoryNumber);
        if (cat == null) return false;
        if (name != null) cat.setName(name);
        if (description != null) cat.setDescription(description);
        if (icon != null) cat.setIcon(icon);
        return true;
    }

    /** 카테고리 삭제 */
    public boolean delete(int categoryNumber) {
        return categories.removeIf(c -> c.getCategoryNumber() == categoryNumber);
    }

    private static long parseTime(String iso) {
        return java.time.Instant.parse(iso).toEpochMilli();
    }
}
