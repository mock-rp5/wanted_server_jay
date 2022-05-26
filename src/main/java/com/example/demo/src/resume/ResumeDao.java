package com.example.demo.src.resume;

import com.example.demo.src.resume.model.GetResumeListRes;
import com.example.demo.src.resume.model.PostResumeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ResumeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long createResume(PostResumeReq postResumeReq){
        String createResumeQuery = "insert into resume (user_id, introduce, resume_title, name, email, phone) values(?,?,?,?,?,?)";
        Object[] createResumeParams = new Object[]{
                postResumeReq.getUserId(),
                postResumeReq.getIntroduce(),
                postResumeReq.getResumeTitle(),
                postResumeReq.getName(),
                postResumeReq.getEmail(),
                postResumeReq.getPhone()
        };
        this.jdbcTemplate.update(createResumeQuery, createResumeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    public List<GetResumeListRes> getResumeList(long userId){
        String getResumeListQuery = "select resume_id, DATE_FORMAT(updated_at, '%Y.%m.%d') as updated_at, resume_status, resume_title from resume where user_id = ?";
        return this.jdbcTemplate.query(getResumeListQuery,
                (rs, rowNum) -> new GetResumeListRes(
                        rs.getLong("resume_id"),
                        rs.getString("updated_at"),
                        rs.getString("resume_status"),
                        rs.getString("resume_title")
                ), userId);
    }
}
