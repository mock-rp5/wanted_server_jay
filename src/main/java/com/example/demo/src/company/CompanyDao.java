package com.example.demo.src.company;

import com.example.demo.src.company.model.GetCompany;
import com.example.demo.src.company.model.GetCompanyPicture;
import com.example.demo.src.company.model.GetCompanyPostings;
import com.example.demo.src.company.model.GetCompanyTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CompanyDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetCompany getCompany(long companyId){
        String query = "select * from company where company_id = ?";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetCompany(
                        rs.getLong("company_id"),
                        rs.getString("company_name"),
                        rs.getString("company_logo"),
                        rs.getString("introduce"),
                        rs.getLong("average_salary"),
                        rs.getString("place")
                ), companyId);
    }

    public List<GetCompanyPicture> getCompanyPictures(long companyId){
        String query = "select picture from company_picture where company_id = ?";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetCompanyPicture(
                        rs.getString("picture")
                ), companyId);

    }

    public List<GetCompanyPostings> getCompanyPostings(long companyId){
        String query = "select * from posting where company_id = ?";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetCompanyPostings(
                        rs.getLong("posting_id"),
                        rs.getString("title"),
                        rs.getLong("recommend_money") + rs.getLong("apply_money"),
                        rs.getString("deadline")
                ), companyId);
    }

    public List<GetCompanyTags> getCompanyTags(long companyId){
        String query = "select t.tag_id, tag_name " +
                "from tag_com_relation as tc " +
                "inner join tag as t " +
                "on t.tag_id = tc.tag_id " +
                "where tc.company_id = ?";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetCompanyTags(
                        rs.getLong("tag_id"),
                        rs.getString("tag_name")
                ), companyId);
    }

}
