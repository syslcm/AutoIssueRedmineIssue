package org.james;


import org.james.model.CustomField;
import org.james.model.NewIssue;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetExceptionRecord {
    static String sql = "SELECT\n" +
            "    SYSTEM_SITE_ERROR_ID,\n" +
            "(select ERROR_DETAIL from PUR_ERR_STATISTICS_DETAIL B\n" +
            "where B.SYSTEM_SITE_ERROR_ID = A.SYSTEM_SITE_ERROR_ID and ROWNUM = 1) AS ERROR_DETAIL, " +
            "    ERROR_MESSAGE,\n" +
            "    (LAST_DATE - FIRST_DATE) AS GAP,\n" +
            "    FIRST_DATE,\n" +
            "    LAST_DATE,\n" +
            "    OWNER_ID     \n" +
            "FROM\n" +
            "    (  SELECT\n" +
            "        SYSTEM_SITE_ERROR_ID,\n" +
            "        ERROR_MESSAGE,\n" +
            "        OWNER_ID,\n" +
            "        MIN(CHECK_DATE) KEEP (DENSE_RANK FIRST             \n" +
            "    ORDER BY\n" +
            "        CHECK_DATE) AS FIRST_DATE,\n" +
            "        MAX(CHECK_DATE) KEEP (DENSE_RANK LAST             \n" +
            "    ORDER BY\n" +
            "        CHECK_DATE) AS LAST_DATE             \n" +
            "    FROM\n" +
            "        PUR_ERR_STATISTICS_DETAIL PESD             \n" +
            "    INNER JOIN\n" +
            "        PUR_SYSTEM_OWNER PSO                             \n" +
            "            ON PSO.SYSTEM_ID = PESD.SYSTEM_ID                             \n" +
            "            AND PSO.SYSTEM_SITE = PESD.SITE             \n" +
            "    WHERE\n" +
            "        SYSTEM_SITE_ERROR_ID IN (\n" +
            "            SELECT\n" +
            "                SYSTEM_SITE_ERROR_ID                             \n" +
            "            FROM\n" +
            "                PUR_ERR_STATISTICS_DETAIL                             \n" +
            "            WHERE\n" +
            "                SYSTEM_SITE_ERROR_ID IN (\n" +
            "                    SELECT\n" +
            "                        SYSTEM_SITE_ERROR_ID                                             \n" +
            "                    FROM\n" +
            "                        PUR_ERR_STATISTICS_DETAIL                                             \n" +
            "                    WHERE\n" +
            "                        CHECK_DATE = TRUNC(to_date('2020-09-15','yyyy-MM-dd') - 1)                                     \n" +
            "                )                             \n" +
            "            GROUP BY\n" +
            "                SYSTEM_SITE_ERROR_ID                             \n" +
            "            HAVING\n" +
            "                COUNT(*)  > 1                             \n" +
            "            )                     \n" +
            "        GROUP BY\n" +
            "            SYSTEM_SITE_ERROR_ID,\n" +
            "            ERROR_MESSAGE,\n" +
            "            OWNER_ID                     \n" +
            "        ORDER BY\n" +
            "            SYSTEM_SITE_ERROR_ID             \n" +
            "    ) A    \n" +
            "WHERE\n" +
            "    (\n" +
            "        LAST_DATE - FIRST_DATE            \n" +
            "    ) >= 45     \n" +
            "ORDER BY\n" +
            "    GAP DESC,\n" +
            "    SYSTEM_SITE_ERROR_ID";


    public static void main(String[] args) {
        System.out.println("start");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:applicationContext.xml");
        ctx.refresh();
        System.out.println("Ctx OK");
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        if (dataSource != null){
            System.out.println("dataSource OK");
        }else{
            System.out.println("dataSource NULL");
        }
        GetExceptionRecord getExceptionRecord = new GetExceptionRecord();
        getExceptionRecord.run(dataSource);
    }

    public List<NewIssue> getRecords(DataSource dataSource){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<Map<String,Object>> list = jt.queryForList(sql);
        final Issuer issuer = new Issuer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        String dueDate = dateFormat.format(calendar.getTime());
        List<NewIssue> newIssues = jt.query(sql, new RowMapper<NewIssue>(){
            @Override
            public NewIssue mapRow(ResultSet resultSet, int i) throws SQLException {
                NewIssue newIssue = new NewIssue();
                newIssue.setProjectId(145);
                String desc = resultSet.getString("ERROR_DETAIL");
                String subject = resultSet.getString("SYSTEM_SITE_ERROR_ID") + " [" + resultSet.getString("ERROR_MESSAGE") +"]";
                String userAcct = resultSet.getString("OWNER_ID");

                int userid = Integer.parseInt(issuer.getUserId(userAcct));
                newIssue.setSubject(subject);
                newIssue.setTrackerId(4);
                newIssue.setDescription(desc);
                newIssue.setStartDate(today);
                newIssue.setDueDate(dueDate);
                newIssue.setAssignedToId(userid);
                List<CustomField> customFields = new ArrayList<>();
                customFields.add(new CustomField(28, dueDate));
                newIssue.setCustomFields(customFields);
                return newIssue;
            }
        });
        return newIssues;

    }

    public void run(DataSource dataSource){
        List<NewIssue> list = getRecords(dataSource);
        Issuer issuer = new Issuer();
        for (NewIssue newIssue : list){
            System.out.println(newIssue.getAssignedToId() + ":" + newIssue.getDescription() + ":" + newIssue.getDueDate());
            issuer.issueIssue(newIssue);
        }

    }
}
