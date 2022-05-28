package com.example.demo.src.resume;

import com.example.demo.src.resume.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

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

    //경력 리스트 조회
    public List<GetCareerListRes> getCareerList(long resumeId){
        String getCareerListQuery = "select * from career where resume_id = ? order by career_id desc";
        return this.jdbcTemplate.query(getCareerListQuery,
                (rs, rowNum) -> new GetCareerListRes(
                        rs.getLong("career_id"),
                        rs.getString("start_at"),
                        rs.getString("end_at"),
                        rs.getString("company_name"),
                        rs.getString("depart_position"),
                        rs.getString("tenure"),
                        rs.getString("now")
                        ), resumeId);
    }
    //경력 성과 리스트 조회
    public List<GetResultListRes> getResultList(long careerId){
        String getResultListQuery = "select * from result where career_id = ? order by result_id desc";
        return this.jdbcTemplate.query(getResultListQuery,
                (rs, rowNum) -> new GetResultListRes(
                        rs.getLong("result_id"),
                        rs.getString("start_at"),
                        rs.getString("end_at"),
                        rs.getString("result")
                ), careerId);
    }

    //경력 성과 추가
    public long createResult(long careerId){
        String createResultQuery = "insert into result (career_id) values(?)";
        this.jdbcTemplate.update(createResultQuery, careerId);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //주요 성과 수정
    public int modifyResult(PatchResultReq patchResultReq){
        String modifyResultQuery = "update result set start_at = ?, end_at = ?, result = ? where result_id = ?";
        Object[] modifyResultParams = new Object[]{
                patchResultReq.getStartAt(),
                patchResultReq.getEndAt(),
                patchResultReq.getResult(),
                patchResultReq.getResultId()
        };
        return this.jdbcTemplate.update(modifyResultQuery,modifyResultParams);
    }

    //주요 성과 삭제
    public int deleteResult(long resultId){
        String deleteResultQuery = "delete from result where result_id = ? ";
        return this.jdbcTemplate.update(deleteResultQuery, resultId);
    }

    //학력

    //학력 리스트 조회
    public List<GetEducationListRes> getEducationList(long resumeId){
        String getEducationListQuery = "select * from education where resume_id = ? order by education_id desc";
        return this.jdbcTemplate.query(getEducationListQuery,
                (rs, rowNum) -> new GetEducationListRes(
                        rs.getLong("education_id"),
                        rs.getString("start_at"),
                        rs.getString("end_at"),
                        rs.getString("now"),
                        rs.getString("name"),
                        rs.getString("major"),
                        rs.getString("info")
                ), resumeId);
    }

    //이력서 학력 추가
    public long createEducation(long resumeId){
        String createEducationQuery = "insert into education (resume_id) values (?)";
        this.jdbcTemplate.update(createEducationQuery, resumeId);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //이력서 학력 수정
    public int modifyEducation(PatchEducationReq patchEducationReq){
        String modifyEducationQuery = "update education set start_at = ?, end_at = ?, now = ?, name = ?, major = ?, info = ? where education_id = ?";
        Object[] modifyEducationParams = new Object[]{
                patchEducationReq.getStartAt(),
                patchEducationReq.getEndAt(),
                patchEducationReq.getNow(),
                patchEducationReq.getName(),
                patchEducationReq.getMajor(),
                patchEducationReq.getInfo(),
                patchEducationReq.getEducationId()
        };
        return this.jdbcTemplate.update(modifyEducationQuery,modifyEducationParams);
    }

    //이력서 학력 삭제
    public int deleteEducation(long resumeId, long educationId){
        String deleteEducationQuery = "delete from education where education_id = ? ";
        return this.jdbcTemplate.update(deleteEducationQuery, educationId);
    }

    /**수상및 기타**/

    //학력 리스트 조회
    public List<GetEtcListRes> getEtcList(long resumeId){
        String getEtcListQuery = "select * from etc where resume_id = ? order by etc_id desc";
        return this.jdbcTemplate.query(getEtcListQuery,
                (rs, rowNum) -> new GetEtcListRes(
                        rs.getLong("etc_id"),
                        rs.getString("date"),
                        rs.getString("name"),
                        rs.getString("detail")
                ), resumeId);
    }

    //이력서 학력 추가
    public long createEtc(long resumeId){
        String createEtcQuery = "insert into etc (resume_id) values (?)";
        this.jdbcTemplate.update(createEtcQuery, resumeId);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //이력서 학력 수정
    public int modifyEtc(PatchEtcReq patchEtcReq){
        String modifyEtcQuery = "update etc set date = ?, name = ?, detail = ? where etc_id = ?";
        Object[] modifyEtcParams = new Object[]{
                patchEtcReq.getDate(),
                patchEtcReq.getName(),
                patchEtcReq.getDetail(),
                patchEtcReq.getEtcId()
        };
        return this.jdbcTemplate.update(modifyEtcQuery,modifyEtcParams);
    }

    //이력서 학력 삭제
    public int deleteEtc(long resumeId, long etcId){
        String deleteEtcQuery = "delete from etc where etc_id = ? ";
        return this.jdbcTemplate.update(deleteEtcQuery, etcId);
    }

    /** 외국어 **/

    //외국어 리스트 조회
    public List<GetLanguageListRes> getLanguageList(long resumeId){
        String getLanguageListQuery = "select * from language where resume_id = ? order by language_id desc";
        return this.jdbcTemplate.query(getLanguageListQuery,
                (rs, rowNum) -> new GetLanguageListRes(
                        rs.getLong("language_id"),
                        rs.getString("language"),
                        rs.getString("test_name"),
                        rs.getString("score"),
                        rs.getString("test_at")
                ), resumeId);
    }

    //이력서 외국어 추가
    public long createLanguage(long resumeId){
        String createLanguageQuery = "insert into language (resume_id) values (?)";
        this.jdbcTemplate.update(createLanguageQuery, resumeId);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //이력서 외국어 수정
    public int modifyLanguage(PatchLanguageReq patchLanguageReq){
        String modifyLanguageQuery = "update language set language = ?, test_name = ?, score = ?, test_at = ? where language_id = ?";
        Object[] modifyLanguageParams = new Object[]{
                patchLanguageReq.getLanguage(),
                patchLanguageReq.getTestName(),
                patchLanguageReq.getScore(),
                patchLanguageReq.getTestAt(),
                patchLanguageReq.getLanguageId()
        };
        return this.jdbcTemplate.update(modifyLanguageQuery,modifyLanguageParams);
    }

    //이력서 외국어 삭제
    public int deleteLanguage(long resumeId, long languageId){
        String deleteLanguageQuery = "delete from language where language_id = ? ";
        return this.jdbcTemplate.update(deleteLanguageQuery, languageId);
    }

    /** 링크 **/

    //링크 리스트 조회
    public List<GetLinkListRes> getLinkList(long resumeId){
        String getLinkListQuery = "select * from link where resume_id = ? order by link_id desc";
        return this.jdbcTemplate.query(getLinkListQuery,
                (rs, rowNum) -> new GetLinkListRes(
                        rs.getLong("link_id"),
                        rs.getString("link")
                ), resumeId);
    }

    //이력서 링크 추가
    public long createLink(long resumeId){
        String createLinkQuery = "insert into link (resume_id) values (?)";
        this.jdbcTemplate.update(createLinkQuery, resumeId);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    //이력서 링크 수정
    public int modifyLink(PatchLinkReq patchLinkReq){
        String modifyLinkQuery = "update link set link = ? where link_id = ?";
        Object[] modifyLinkParams = new Object[]{
                patchLinkReq.getLink(),
                patchLinkReq.getLinkId()
        };
        return this.jdbcTemplate.update(modifyLinkQuery,modifyLinkParams);
    }

    //이력서 링크 삭제
    public int deleteLink(long resumeId, long linkId){
        String deleteLinkQuery = "delete from link where link_id = ? ";
        return this.jdbcTemplate.update(deleteLinkQuery, linkId);
    }


}
