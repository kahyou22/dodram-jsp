package admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int userNumber;
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private long createdAt;
}
