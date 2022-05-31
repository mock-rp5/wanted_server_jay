package com.example.demo.src.posting;

import com.example.demo.src.posting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostingDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //채용공고 지원하기
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

    //채용공고 조회
    public GetPosting getPosting(long postingId){
        String getPostingQuery = "select c.company_id, c.company_name, c.place, p.posting_id,p.title, p.main_text, p.place_full, p.deadline, p.recommend_money, p.apply_money " +
                "from posting as p " +
                "inner join company as c " +
                "on c.company_id = p.company_id " +
                "where posting_id = ?";
        return this.jdbcTemplate.queryForObject(getPostingQuery,
                (rs, rowNum) -> new GetPosting(
                        rs.getLong("posting_id"),
                        rs.getLong("company_id"),
                        rs.getString("company_name"),
                        rs.getString("place"),
                        rs.getString("title"),
                        rs.getString("main_text"),
                        rs.getLong("recommend_money"),
                        rs.getLong("apply_money"),
                        rs.getString("place_full")
                ), postingId);
    }

    //채용공고 회사 사진 조회
    public List<String> getPictures(long postingId){
        String getPicturesQuery ="select cp.picture " +
                "from posting as p " +
                "inner join company_picture as cp " +
                "on cp.company_id = p.company_id " +
                "where posting_id = ?";
        return this.jdbcTemplate.query(getPicturesQuery,
                (rs, rowNum) -> new String ( rs.getString("picture") )
                , postingId);
    }
    //채용공고 태그 조회
    public List<String> getTags(long postingId){
        String getTagsQuery = "select t.tag_name " +
                "from posting as p " +
                "right outer join tag_com_relation as tc " +
                "on tc.company_id = p.company_id " +
                "inner join tag as t " +
                "on t.tag_id = tc.tag_id " +
                "where posting_id = ?";
        return this.jdbcTemplate.query(getTagsQuery,
                (rs, rowNum) -> new String( rs.getString("tag_name") )
                , postingId);
    }

//    //채용공고 리스트 조회
//    public List<GetPostingListRes> getPostingList(GetPostingListReq getPostingListReq){
//        String getPostingListQuery ="";
//
//    }

    private int getListSize(List<String> list){
        return list.size();
    }
}
