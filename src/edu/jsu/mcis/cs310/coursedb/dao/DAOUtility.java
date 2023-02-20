package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_SP23 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

                while(rs.next()) {
                        JsonObject jsonObject = new JsonObject();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        
                        for ( int i = 1; i < rsmd.getColumnCount()+1; i++) {
                            String colLabel = rsmd.getColumnLabel(i);
                            String colValue = rs.getString(colLabel);
                            
                            jsonObject.put(colLabel, colValue);
                        }
                        
                        records.add(jsonObject);
                }

            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
