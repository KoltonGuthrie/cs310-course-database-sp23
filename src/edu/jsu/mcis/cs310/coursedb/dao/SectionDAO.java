package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.*;

public class SectionDAO {
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        JsonArray result = new JsonArray();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {

                String query = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
                ps = conn.prepareStatement(query);
                ps.setString(1, String.valueOf(termid));
                ps.setString(2, subjectid);
                ps.setString(3, num);
                
                boolean hasresults = ps.execute();
                
                if (hasresults) {
                    rs = ps.getResultSet();

                    while(rs.next()) {
                        JsonObject jsonObject = new JsonObject();
                        rsmd = rs.getMetaData();
                        
                        for ( int i = 1; i < rsmd.getColumnCount()+1; i++) {
                            String colLabel = rsmd.getColumnLabel(i);
                            String colValue = rs.getString(colLabel);
                            
                            jsonObject.put(colLabel, colValue);
                        }
                        
                        result.add(jsonObject);
                    }
                }
            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return Jsoner.serialize(result);
        
    }
    
}