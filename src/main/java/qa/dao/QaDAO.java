package qa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import qa.dto.QaDTO;
import util.DBConnectionMgr;

	public class QaDAO {

		// 회원/비회원 구분 없이 글 등록
	    public int insertQa(QaDTO dto) {
	        String sql = "INSERT INTO qa(type, title, content, user_num, guest_name, guest_password, guest_email) "
	                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
	        int result = 0;

	        try (Connection conn = DBConnectionMgr.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            // 공통 필수 컬럼
	            ps.setString(1, dto.getType());
	            ps.setString(2, dto.getTitle());
	            ps.setString(3, dto.getContent());

	            if (dto.getUserNum() != null) {
	                // 회원 글
	                ps.setLong(4, dto.getUserNum());
	                ps.setNull(5, java.sql.Types.VARCHAR);
	                ps.setNull(6, java.sql.Types.VARCHAR);
	                ps.setNull(7, java.sql.Types.VARCHAR);
	            } else {
	                // 비회원 글
	                ps.setNull(4, java.sql.Types.BIGINT);
	                ps.setString(5, dto.getGuestName());

	                if (dto.getGuestPassword() != null) {
	                    String hashed = BCrypt.hashpw(dto.getGuestPassword(), BCrypt.gensalt());
	                    ps.setString(6, hashed);
	                } else {
	                    ps.setNull(6, java.sql.Types.VARCHAR);
	                }

	                ps.setString(7, dto.getGuestEmail());
	            }

	            result = ps.executeUpdate();
	            System.out.println("[DAO] insertQa result = " + result);

	        } catch (Exception e) {
	            System.out.println("[DAO] ⚠ insertQa 예외 발생: " + e.getMessage());
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	    public List<QaDTO> getQaList() {
	        List<QaDTO> list = new ArrayList<>();
	        String sql = "SELECT * FROM qa ORDER BY qa_num DESC"; // 최신 글 순

	        try (Connection conn = DBConnectionMgr.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                QaDTO dto = new QaDTO();
	                dto.setQaNum(rs.getLong("qa_num"));
	                dto.setType(rs.getString("type"));
	                dto.setTitle(rs.getString("title"));
	                dto.setContent(rs.getString("content"));
	                dto.setGuestName(rs.getString("guest_name"));
	                dto.setUserNum(rs.getObject("user_num") != null ? rs.getLong("user_num") : null);
	                dto.setStatus(rs.getString("status"));

	                if(rs.getTimestamp("created_at") != null)
	                    dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
	                if(rs.getTimestamp("answered_at") != null)
	                    dto.setAnsweredAt(rs.getTimestamp("answered_at").toLocalDateTime());
	                if(rs.getTimestamp("updated_at") != null)
	                    dto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

	                list.add(dto);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
    
    
}