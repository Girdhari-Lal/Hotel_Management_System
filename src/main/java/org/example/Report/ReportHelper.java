package org.example.Report;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class ReportHelper {
    public int getRoomCategoryId(Session session, int roomId){
        Query query = session.createSQLQuery("Select category_id from room_detail where room_id = '"+roomId+"'");
        return (Integer) query.uniqueResult();
    }
    public String getRoomCategory(Session session, int categoryId){
        Query query = session.createSQLQuery("Select room_type from room_category where id = '"+categoryId+"'");
        return (String) query.uniqueResult();
    }
}
