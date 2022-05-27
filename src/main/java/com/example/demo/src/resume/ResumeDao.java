package com.example.demo.src.resume;

import com.example.demo.src.resume.model.GetResumeListRes;
import com.example.demo.src.resume.model.PatchCareerReq;
import com.example.demo.src.resume.model.PatchResumeReq;
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

    //이력서 생성
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

    //이력서 목록 조회
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

    //이력서 임시저장
    public int modifyResume(PatchResumeReq patchResumeReq){
        String modifyResumeQuery = "update resume set introduce = ?, resume_title = ?, name = ?, email = ?, phone = ?, resume_status = ? where resume_id = ?";
        Object[] modifyResumeParams = new Object[]{
                patchResumeReq.getIntroduce(),
                patchResumeReq.getResumeTitle(),
                patchResumeReq.getName(),
                patchResumeReq.getEmail(),
                patchResumeReq.getPhone(),
                "WRITING",
                patchResumeReq.getResumeId()
        };
        return this.jdbcTemplate.update(modifyResumeQuery, modifyResumeParams);
    }

    //이력서 작성 완료
    public int completeResume(PatchResumeReq patchResumeReq){
        String modifyResumeQuery = "update resume set introduce = ?, resume_title = ?, name = ?, email = ?, phone = ?, resume_status = ? where resume_id = ?";
        Object[] modifyResumeParams = new Object[]{
                patchResumeReq.getIntroduce(),
                patchResumeReq.getResumeTitle(),
                patchResumeReq.getName(),
                patchResumeReq.getEmail(),
                patchResumeReq.getPhone(),
                "COMPLETE",
                patchResumeReq.getResumeId()
        };
        return this.jdbcTemplate.update(modifyResumeQuery, modifyResumeParams);
    }

    //이력서 삭제
    public int deleteResume(long resumeId){
        String deleteResumeQuery = "update resume set status = ? where resume_id = ?";
        Object[] deleteResumeParams = new Object[]{"DELETE", resumeId};
        return this.jdbcTemplate.update(deleteResumeQuery, deleteResumeParams);
    }

    //이력서 경력 추가
    public long createCareer(long resumeId){
        String createCareerQuery = "insert into career (resume_id) values (?)";
        this.jdbcTemplate.update(createCareerQuery, resumeId);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //이력서 경력 수정
    public int modifyCareer(PatchCareerReq patchCareerReq){
        String modifyCareerQuery = "update career set start_at = ?, end_at = ?, company_name = ?, depart_position = ?, tenure = ?, now = ? where career_id = ?";
        Object[] modifyCareerParams = new Object[]{
                patchCareerReq.getStartAt(),
                patchCareerReq.getEndAt(),
                patchCareerReq.getCompanyName(),
                patchCareerReq.getDepartPosition(),
                patchCareerReq.getTenure(),
                patchCareerReq.getNow(),
                patchCareerReq.getCareerId()
        };
        return this.jdbcTemplate.update(modifyCareerQuery,modifyCareerParams);
    }

    //이력서 경력 삭제
    public int deleteCareer(long resumeId, long careerId){
        String deleteCareerQuery = "delete from career where career_id = ? ";
        return this.jdbcTemplate.update(deleteCareerQuery, careerId);
    }
}
