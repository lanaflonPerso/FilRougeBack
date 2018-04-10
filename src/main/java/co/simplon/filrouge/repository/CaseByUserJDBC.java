package co.simplon.filrouge.repository;

import co.simplon.filrouge.modele.PoliceCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CaseByUserJDBC {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private DataSource datasource;

    /**
     * Constructor
     * @param jdbcTemplate : the JDBCTemplace connected to the Database (thanks to Spring)
     */
    @Autowired
    public CaseByUserJDBC(JdbcTemplate jdbcTemplate) {
        this.datasource = jdbcTemplate.getDataSource();
    }


    /**
     * @param idUser
     * @return
     * @throws Exception
     */
    public List<PoliceCase> listCases(Long idUser) throws Exception {
        PoliceCase policeCase;
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql;
        ArrayList<PoliceCase> aLlistOfCase = new ArrayList<PoliceCase>();

        try {
            // Prepare the SQL query
            sql =   "SELECT c.id\n" +
                    "FROM user AS u\n" +
                    "LEFT JOIN police_case_user AS cu ON cu.user_id  = u.id \n" +
                    "LEFT JOIN police_case AS c ON cu.police_case_id  = c.id \n" +
                    "WHERE u.id = ?";
            pstmt = datasource.getConnection().prepareStatement(sql);
            pstmt.setLong(1, idUser);

            // Log info
            logSQL(pstmt);

            // Run the query
            rs = pstmt.executeQuery();

            // Handle the query results
            while (rs.next()) {
                policeCase = getCaseFromResultSet(rs);
                aLlistOfCase.add(policeCase);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL Error !:" + pstmt.toString(), e);
            throw e;
        } finally {
            pstmt.close();
        }

        return aLlistOfCase;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    private PoliceCase getCaseFromResultSet(ResultSet rs) throws SQLException {
        PoliceCase policeCase = new PoliceCase();
        policeCase.setId(rs.getLong("id"));


        return policeCase;
    }

    private void logSQL(PreparedStatement pstmt) {
        String sql;

        if (pstmt == null)
            return;

        sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
        log.debug(sql);
    }
}