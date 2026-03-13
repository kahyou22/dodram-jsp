package service.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.dto.EventDto;
import util.DBConnectionMgr;

public class EventDao {

    private DBConnectionMgr pool;

    public EventDao() throws Exception {
        pool = new DBConnectionMgr(); 
    }

    public List<EventDto> getAllEvents() throws SQLException {
        List<EventDto> list = new ArrayList<>();
        String sql = "SELECT * FROM event ORDER BY id DESC";
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                list.add(new EventDto(
                        rs.getInt("id"),
                        rs.getString("tab"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getString("alt"),
                        rs.getString("date"),
                        rs.getString("content")
                ));
            }
        }
        return list;
    }

    public EventDto getEventById(int id) throws SQLException {
        EventDto event = null;
        String sql = "SELECT * FROM event WHERE id=?";
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    event = new EventDto(
                            rs.getInt("id"),
                            rs.getString("tab"),
                            rs.getString("title"),
                            rs.getString("img"),
                            rs.getString("alt"),
                            rs.getString("date"),
                            rs.getString("content")
                    );
                }
            }
        }
        return event;
    }
}