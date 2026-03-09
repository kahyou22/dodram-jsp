package qa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.mindrot.jbcrypt.BCrypt;

import qa.dto.QaDTO;
import util.DBConnectionMgr;

public class QaDAO {

    public int insertQa(QaDTO dto) {

        String sql = "INSERT INTO qa(type, title, content, guest_name, guest_password, guest_email) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        int result = 0;

        try (
            Connection conn = DBConnectionMgr.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, dto.getType());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getGuestName());

            // 비밀번호 BCrypt
            String hashed = BCrypt.hashpw(dto.getGuestPassword(), BCrypt.gensalt());
            ps.setString(5, hashed);

            ps.setString(6, dto.getGuestEmail());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public List<QaDTO> getQaList(){

        List<QaDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM qa ORDER BY qa_num DESC";

        try(Connection conn = DBConnectionMgr.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                QaDTO dto = new QaDTO();

                dto.setQaNum(rs.getLong("qa_num"));
                dto.setType(rs.getString("type"));
                dto.setTitle(rs.getString("title"));
                dto.setGuestName(rs.getString("guest_name"));
                dto.setStatus(rs.getString("status"));

                dto.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
                );

                list.add(dto);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    
    
    
}

