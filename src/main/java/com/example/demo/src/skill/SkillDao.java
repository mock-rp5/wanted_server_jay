package com.example.demo.src.skill;

import com.example.demo.src.skill.model.GetSkillsRes;
import com.example.demo.src.skill.model.PostSkillsRes;
import com.example.demo.src.skill.model.SearchSkillsReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SkillDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetSkillsRes> getSkills(){
        String getSkillsQuery = "select * from skill";
        return this.jdbcTemplate.query(getSkillsQuery,
                (rs, rowNum)-> new GetSkillsRes(
                        rs.getLong("skill_id"),
                        rs.getString("skill_name")
                ));
    }


    //스킬 검색
    public List<PostSkillsRes> searchSkills(SearchSkillsReq searchSkillsReq){
        String getSkillsQuery = "select * from skill where skill_name like ?";
        return this.jdbcTemplate.query(getSkillsQuery,
                (rs, rowNum)-> new PostSkillsRes(
                        rs.getLong("skill_id"),
                        rs.getString("skill_name")
                ), "%" + searchSkillsReq.getSkillName() + "%");
    }
}
