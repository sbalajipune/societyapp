package com.housingsociety.memberservice.dao;

import com.housingsociety.memberservice.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

@Component
public class MemberDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MemberDAO.class);
    private static final String DATA_FILENAME = "members.json";

    private JdbcTemplate jdbc;

    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    protected void init() {
        try {
            String dbName = env.getProperty("POSTGRES_DB", "mapitdb");
            String dbHost = env.getProperty("POSTGRES_HOST", "localhost");

            LOG.info("Connecting to database {}:5432/{}", dbHost, dbName);
            DriverManagerDataSource dm = new DriverManagerDataSource();
            dm.setDriverClassName("org.postgresql.Driver");
            dm.setUrl("jdbc:postgresql://" + dbHost + ":5432/" + dbName);
            dm.setUsername(env.getProperty("POSTGRES_USER", "postgresql"));
            dm.setPassword(env.getProperty("POSTGRES_PASSWORD", "postgresql"));

            jdbc = new JdbcTemplate(dm, false);

            createTables();
        } catch (Exception e) {
            LOG.error("Failed to connect to database", e);
        }

        if (jdbc != null) {
            Integer itemCount = 0;
            try {
                itemCount = jdbc.queryForObject("select count(*) from member", Integer.class);

                if (itemCount == 0) {
                    loadMembers();
                }
            } catch (DataAccessException e) {
                LOG.warn(e.getMessage());
            }
        }
    }

    private void createTables() {
        try {
            // create table
            jdbc.execute("CREATE TABLE member (" +
                    "id serial NOT NULL, " +
                    "memberId character varying(15) NOT NULL, " +
                    "gender character varying(1), " +
                    "memberFirstName character varying(20), " +
                    "memberLastName character varying(20)," +
                    "age int varying(3), " +
                    "profession character varying(256), " +
                    "CONSTRAINT memberId_pk PRIMARY KEY (memberId));");

        } catch (Exception ex) {
            LOG.debug("Failed to create member table, it might already exist!");
            LOG.debug(ex.getMessage());
        }
    }

    private void loadMembers() {
        LOG.info("Importing members data");

        try {
            // import json data
            String json = getJsonContent(DATA_FILENAME);
            JsonParser parser = JsonParserFactory.getJsonParser();
            List<Object> jsonObjects = parser.parseList(json);
            List<Member> members = new LinkedList<Member>();
            for (Object o : jsonObjects) {
                Map<String, String> map = (Map<String, String>) o;

                String memberId = map.get("memberId");
                String gender = map.get("gender");
                String memberFirstName = map.get("memberFirstName");
                String memberSecondName = map.get("memberSecondName");
                int age = Integer.parseInt(map.get("age"));
                String profession = map.get("profession");
                members.add(new Member(memberId, gender.charAt(0), memberFirstName, memberSecondName, age, profession));
            }

            jdbc.batchUpdate("insert into member (memberId, gender, memberFirstName, memberSecondName, age, profession) values (?,?,?,?,?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, members.get(i).getMemberId());
                            ps.setString(2, String.valueOf(members.get(i).getGender()));
                            ps.setString(3, members.get(i).getMemberFirstName());
                            ps.setString(4, members.get(i).getMemberLastName());
                            ps.setInt(5, members.get(i).getAge());
                            ps.setString(6, members.get(i).getProfession());
                        }

                        @Override
                        public int getBatchSize() {
                            return members.size();
                        }
                    });
            LOG.info("{} airports data imported", members.size());

        } catch (IOException ex) {
            LOG.error("Failed to parse members data from {}", CLASSPATH_URL_PREFIX + DATA_FILENAME);
            ex.printStackTrace();
        }
    }

    private String getJsonContent(String fileName) throws IOException {
        StringBuilder json = new StringBuilder("");
        InputStream is = resourceLoader.getResource(CLASSPATH_URL_PREFIX + fileName).getInputStream();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            json.append(line).append("\n");
        }

        scanner.close();
        return json.toString();
    }

    public List<Member> getMembers() {
        return jdbc.query(
                "select memberId, gender, memberFirstName, memberSecondName, age, profession from member ORDER by memberId DESC LIMIT 100",
                (rs, i) -> new Member(rs.getString("memberId"),
                        rs.getString("gender").charAt(0),
                        rs.getString("memberFirstName"),
                        rs.getString("memberSecondName"),
                        rs.getInt("age"),
                        rs.getString("profession")));
    }

    public List<Member> getMembersByApartmentId(String apartmentId) {
        return jdbc.query(
                "select memberId, gender, memberFirstName, memberSecondName, age, profession from member where apartmentId = " + apartmentId + " ORDER by memberId DESC LIMIT 100",
                (rs, i) -> new Member(rs.getString("memberId"),
                        rs.getString("gender").charAt(0),
                        rs.getString("memberFirstName"),
                        rs.getString("memberSecondName"),
                        rs.getInt("age"),
                        rs.getString("profession")));
    }
}
