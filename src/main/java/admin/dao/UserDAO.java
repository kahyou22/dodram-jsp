package admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import admin.dto.UserDTO;

/**
 * 사용자 DAO
 */
public class UserDAO {

    private static final UserDAO INSTANCE = new UserDAO();
    private final List<UserDTO> users = new ArrayList<>();

    private UserDAO() {
        Random rng = new Random(42);

        users.add(new UserDTO(1, "admin", "admin", "admin@jfs.rf.gd",
                "010-0000-0000", parseTime("2025-01-01T00:00:00Z")));
        users.add(new UserDTO(2, "kahyou222", "정문주", "kahyou222@gmail.com",
                "010-0000-0000", parseTime("2025-06-15T09:30:00Z")));
        users.add(new UserDTO(3, "whyeli", "조휘일", "whyeil@naver.com",
                "010-0000-0000", parseTime("2025-08-20T14:00:00Z")));

        long lastTs = parseTime("2025-08-20T14:00:00Z");
        for (int i = 4; i <= 54; i++) {
            long dayGap = (rng.nextInt(7) + 1) * 24L * 60 * 60 * 1000;
            lastTs += dayGap;
            String mid = String.format("%04d", rng.nextInt(10000));
            String last = String.format("%04d", rng.nextInt(10000));
            String phone = "010-" + mid + "-" + last;
            String name = (i % 2 == 0) ? "이름" + i : "름이" + i;
            users.add(new UserDTO(i, "user_" + i, name,
                    "user" + i + "@jfs.rf.gd", phone, lastTs));
        }
    }

    public static UserDAO getInstance() { return INSTANCE; }

    public List<UserDTO> getAll() {
        return Collections.unmodifiableList(users);
    }

    public UserDTO findByUserNumber(int userNumber) {
        return users.stream()
                .filter(u -> u.getUserNumber() == userNumber)
                .findFirst().orElse(null);
    }

    public UserDTO findByUserName(String userName) {
        return users.stream()
                .filter(u -> userName.equals(u.getUserName()))
                .findFirst().orElse(null);
    }

    /** 사용자 정보 수정 */
    public boolean update(int userNumber, String name, String email, String phoneNumber) {
        UserDTO user = findByUserNumber(userNumber);
        if (user == null) return false;
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
        return true;
    }

    /** 사용자 삭제 */
    public boolean delete(int userNumber) {
        return users.removeIf(u -> u.getUserNumber() == userNumber);
    }

    public int count() { return users.size(); }

    private static long parseTime(String iso) {
        return java.time.Instant.parse(iso).toEpochMilli();
    }
}
