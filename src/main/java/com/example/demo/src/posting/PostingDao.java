package com.example.demo.src.posting;

import com.example.demo.src.posting.model.PostApplyReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PostingDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public long applyPosting(PostApplyReq postApplyReq){
        String applyPostingQuery = "insert into apply (user_id, posting_id, resume_id, name, email, phone) values (?,?,?,?,?,?)";
        Object[] applyPostingParams = new Object[]{
                postApplyReq.getUserId(),
                postApplyReq.getPostingId(),
                postApplyReq.getResumeId(),
                postApplyReq.getName(),
                postApplyReq.getEmail(),
                postApplyReq.getPhone()};
        this.jdbcTemplate.update(applyPostingQuery, applyPostingParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }
}
