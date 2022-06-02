package com.example.demo.src.posting;

import com.example.demo.src.posting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
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
    // 북마크
    public int bookMarkPosting(long postingId, long userId) {
        String bookMarkPostingQuery = "insert into book_mark (posting_id, user_id) values (?,?)";
        Object[] bookMarkPostingParams = new Object[]{postingId, userId};
        return this.jdbcTemplate.update(bookMarkPostingQuery, bookMarkPostingParams);
    }

    //북마크 이력 체크
    public int checkBookMarkHistory(long postingId, long userId) {
        String checkBookMarkQuery = "select exists(select book_mark_id from book_mark where posting_id = ? and user_id = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        Object[] checkBookMarkParams = new Object[]{postingId, userId}; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkBookMarkQuery,
                int.class,
                checkBookMarkParams);
    }

    // 현재 북마크 인지 체크
    public int checkBookMark(long postingId, long userId) {
        String checkBookMarkQuery = "select exists(select book_mark_id from book_mark where posting_id = ? and user_id = ? and status = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        Object[] checkBookMarkParams = new Object[]{postingId, userId, "ACTIVE"}; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkBookMarkQuery,
                int.class,
                checkBookMarkParams);
    }

    //다시  북마크
    public int reBookMarkPosting(long postingId, long userId) {
        String bookMarkPostingQuery = "update book_mark set status = ? where posting_id = ? and user_id = ?";
        Object[] bookMarkPostingParams = new Object[]{"ACTIVE", postingId, userId};
        return this.jdbcTemplate.update(bookMarkPostingQuery, bookMarkPostingParams);
    }

    //북마크 해제
    public int cancelBookMark(long postingId, long userId) {
        String bookMarkPostingQuery = "update book_mark set status = ? where posting_id = ? and user_id = ?";
        Object[] bookMarkPostingParams = new Object[]{"DELETE", postingId, userId};
        return this.jdbcTemplate.update(bookMarkPostingQuery, bookMarkPostingParams);
    }

    //북마크한 채용공고 조회
    public List<GetBookMarkPostingRes> getBookMarkPostings(long userId){
        String getBookMarkPostingsQuery = "select cp.picture, c.company_name, p.posting_id,p.title, p.place_1, p.recommend_money, p.apply_money, b.book_mark_id, b.created_at, b.user_id " +
                "from company as c " +
                "inner join (select cp.company_id, min(com_pic_id), cp.picture " +
                "from company_picture as cp " +
                "group by company_id) as cp " +
                "on cp.company_id = c.company_id " +
                "inner join posting as p " +
                "on p.company_id = c.company_id " +
                "inner join book_mark as b " +
                "on b.posting_id = p.posting_id " +
                "where user_id = ? and b.status = ?" +
                "order by b.created_at desc";
        Object[] getBookMarkPostingsParams = new Object[]{userId, "ACTIVE"};
        return this.jdbcTemplate.query(getBookMarkPostingsQuery,
                (rs, rowNum) -> new GetBookMarkPostingRes(
                        rs.getLong("posting_id"),
                        rs.getLong("book_mark_id"),
                        rs.getString("created_at"),
                        rs.getString("picture"),
                        rs.getString("company_name"),
                        rs.getString("place_1"),
                        rs.getLong("recommend_money") + rs.getLong("apply_money")
                ), getBookMarkPostingsParams);
    }

    //좋아요
    public int likePosting(long postingId, long userId) {
        String bookMarkPostingQuery = "insert into likes (posting_id, user_id) values (?,?)";
        Object[] bookMarkPostingParams = new Object[]{postingId, userId};
        return this.jdbcTemplate.update(bookMarkPostingQuery, bookMarkPostingParams);
    }

    //좋아요 이력 체크
    public int checkLikeHistory(long postingId, long userId) {
        String checkLikeQuery = "select exists(select like_id from likes where posting_id = ? and user_id = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        Object[] checkLikeParams = new Object[]{postingId, userId}; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkLikeQuery,
                int.class,
                checkLikeParams);
    }

    // 현재 좋아요 인지 체크
    public int checkLike(long postingId, long userId) {
        String checkLikeQuery = "select exists(select like_id from likes where posting_id = ? and user_id = ? and status = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        Object[] checkLikeParams = new Object[]{postingId, userId, "ACTIVE"}; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkLikeQuery,
                int.class,
                checkLikeParams);
    }


    //다시 좋아요
    public int reLikePosting(long postingId, long userId) {
        String bookMarkPostingQuery = "update likes set status = ? where posting_id = ? and user_id = ?";
        Object[] bookMarkPostingParams = new Object[]{"ACTIVE", postingId, userId};
        return this.jdbcTemplate.update(bookMarkPostingQuery, bookMarkPostingParams);
    }

    //좋아요 해제
    public int cancelLike(long postingId, long userId) {
        String bookMarkPostingQuery = "update likes set status = ? where posting_id = ? and user_id = ?";
        Object[] bookMarkPostingParams = new Object[]{"DELETE", postingId, userId};
        return this.jdbcTemplate.update(bookMarkPostingQuery, bookMarkPostingParams);
    }

    //좋아요한 채용공고 조회
   public List<GetlikePostingRes> getLikePostings(long userId){
        String getlikePostingsQuery = "select cp.picture, c.company_name, p.posting_id,p.title, p.place_1, p.recommend_money, p.apply_money, l.like_id, l.created_at, l.user_id " +
                "from company as c " +
                "inner join (select cp.company_id, min(com_pic_id), cp.picture " +
                "from company_picture as cp " +
                "group by company_id) as cp " +
                "on cp.company_id = c.company_id " +
                "inner join posting as p " +
                "on p.company_id = c.company_id " +
                "inner join likes as l " +
                "on l.posting_id = p.posting_id " +
                "where user_id = ? and l.status = ? " +
                "order by l.created_at desc";
        Object[] getLikePostingParams = new Object[]{userId, "ACTIVE"};
       System.out.println(Arrays.toString(getLikePostingParams));
        return this.jdbcTemplate.query(getlikePostingsQuery,
                (rs, rowNum) -> new GetlikePostingRes(
                        rs.getLong("posting_id"),
                        rs.getLong("like_id"),
                        rs.getString("created_at"),
                        rs.getString("picture"),
                        rs.getString("company_name"),
                        rs.getString("place_1"),
                        rs.getLong("recommend_money") + rs.getLong("apply_money")
                ), getLikePostingParams);
    }
    // 지원 리스트
    public List<GetApplyRes> getApply(long userId){
        String getApplyQuery = "select a.apply_id, a.posting_id, p.company_id, c.company_name, p.title, r.resume_title, r.resume_id " +
                "from apply as a " +
                "inner join posting as p " +
                "on p.posting_id = a.posting_id " +
                "inner join company as c " +
                "on c.company_id = p.company_id " +
                "inner join resume as r " +
                "on r.resume_id = a.resume_id " +
                "where a.user_id = ?";
        return this.jdbcTemplate.query(getApplyQuery,
                (rs, rowNum) -> new GetApplyRes(
                        rs.getLong("apply_id"),
                        rs.getLong("posting_id"),
                        rs.getLong("company_id"),
                        rs.getLong("resume_id"),
                        rs.getString("company_name"),
                        rs.getString("title"),
                        rs.getString("resume_title")
                ), userId);
    }


    //채용공고 리스트 조회
    public List<GetPostingListRes> getPostingList(GetPostingListReq getPostingListReq){
        List<String> locationOneParams = getPostingListReq.getLocation_1();
        List<String> locationTwoParams = getPostingListReq.getLocation_2();
        List<String> skillTagParams = getPostingListReq.getSkillTags();
        List<String> userTagParams = getPostingListReq.getUserTags();
        List<String> jobParams = getPostingListReq.getJobSelected();
        long years = getPostingListReq.getYears();
        int ut, st, l1, l2, j;

        List<Object> getPostingListParams = new ArrayList<Object>();
        getPostingListParams.add(years);

        if (userTagParams == null)
            ut = 0;
        else{
            ut = userTagParams.size();
            for (String userTagParam : userTagParams) {
                getPostingListParams.add(userTagParam);
            }
        }

        if (locationOneParams == null)
            l1 = 0;
        else{
            l1 = locationOneParams.size();
            for (String locationOneParam : locationOneParams) {
                getPostingListParams.add(locationOneParam);
            }
        }

        if (locationTwoParams == null)
            l2 = 0;
        else{
            l2 = locationTwoParams.size();
            for (String locationTwoParam : locationTwoParams) {
                getPostingListParams.add(locationTwoParam);
            }
        }

        if (skillTagParams == null)
            st = 0;
        else{
            st = skillTagParams.size();
            for (String skillTagParam : skillTagParams) {
                getPostingListParams.add(skillTagParam);
            }
        }

        if (jobParams == null)
            j = 0;
        else{
            j = jobParams.size();
            for (String jobParam : jobParams) {
                getPostingListParams.add(jobParam);
            }
        }

        StringBuilder getPostingListQuery = new StringBuilder("select cp.picture, lc.like_count ,jp.job_id,sp.skill_id,p.posting_id,p.company_id, j.job_group_id,ta.tag_id, c.company_name,ta.tag_name, p.job_group_id, p.created_at, p.title, p.recommend_money, p.apply_money, p.place_1, p.place_2, p.career " +
                "from ( " +
                "select tc.company_id, t.tag_id, t.tag_name " +
                "from tag_com_relation as tc " +
                "left outer join tag as t " +
                "on t.tag_id = tc.tag_id " +
                ") as ta " +
                "left outer join posting as p " +
                "on ta.company_id = p.company_id " +
                "left outer join company as c " +
                "on c.company_id = p.company_id " +
                "left outer join (select cp.company_id, min(com_pic_id), cp.picture " +
                "from company_picture as cp " +
                "group by company_id) as cp " +
                "on cp.company_id = p.company_id " +
                "left outer join (select s.skill_id, sp.posting_id " +
                "from skill_posting_relation as sp " +
                "right outer join skill as s " +
                "on s.skill_id = sp.skill_id " +
                ") as sp " +
                "on sp.posting_id = p.posting_id " +
                "left outer join (select * " +
                "from job_posting_relation " +
                ") as jp " +
                "on jp.posting_id = p.posting_id " +
                "left outer join job as j " +
                "on j.job_id = jp.job_id " +
                "left outer join ( " +
                "select posting_id, count(like_id) as like_count " +
                "from likes as l " +
                "group by posting_id) as lc " +
                "on lc.posting_id = p.posting_id " +
                "where p.career >= (?) ");

        long jobGroupId = getPostingListReq.getJobGroupId();

        if (l1 > 0){
            getPostingListQuery.append("and p.place_1 IN (");
            for(int i = 0; i < l1; i++){
                getPostingListQuery.append("?");
                if (i != l1 - 1)
                    getPostingListQuery.append(", ");
            }
            getPostingListQuery.append(") ");
        }
        if (l2 > 0){
            getPostingListQuery.append("and p.place_2 IN (");
            for(int i = 0; i < l2; i++){
                getPostingListQuery.append("?");
                if (i != l2 - 1)
                    getPostingListQuery.append(", ");
            }
            getPostingListQuery.append(") ");
        }
        if (ut > 0){
            getPostingListQuery.append("and ta.tag_id IN (");
            for(int i = 0; i < ut; i++){
                getPostingListQuery.append("?");
                if (i != ut - 1)
                    getPostingListQuery.append(", ");
            }
            getPostingListQuery.append(") ");
        }
        if (st > 0){
            getPostingListQuery.append("and sp.skill_id IN (");
            for(int i = 0; i < st; i++){
                getPostingListQuery.append("?");
                if (i != st - 1)
                    getPostingListQuery.append(", ");
            }
            getPostingListQuery.append(") ");
        }
        if (j > 0){
            getPostingListQuery.append("and jp.job_id IN (");
            for(int i = 0; i < j; i++){
                getPostingListQuery.append("?");
                if (i != j - 1)
                    getPostingListQuery.append(", ");
            }
            getPostingListQuery.append(") ");
        }

        if (jobGroupId != 0){
            getPostingListQuery.append("and j.job_group_id = (?) ");
            getPostingListParams.add(jobGroupId);
        }
        getPostingListQuery.append("group by posting_id ");

        String sortType = getPostingListReq.getSortType();
        if (sortType.equals("recent")){
            getPostingListQuery.append("order by p.created_at desc");
        } else if (sortType.equals("popular")){
            getPostingListQuery.append("order by lc.like_count desc");
        } else if (sortType.equals("money")){
            getPostingListQuery.append("order by p.apply_money desc");
        }


        String getPostingQuery = getPostingListQuery.toString();
        System.out.println(getPostingQuery);

        int size = getPostingListParams.size();
        Object params[] = getPostingListParams.toArray(new Object[size]);
        System.out.println(getPostingQuery.toString());
        System.out.println(Arrays.toString(params));
        return this.jdbcTemplate.query(getPostingQuery,
                (rs, rowNum)-> new GetPostingListRes(
                        rs.getLong("posting_id"),
                        rs.getString("picture"),
                        rs.getString("title"),
                        rs.getString("company_name"),
                        rs.getString("place_1"),
                        rs.getLong("recommend_money") + rs.getLong("apply_money")
                ), params);
    }
}
