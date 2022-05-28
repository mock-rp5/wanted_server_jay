package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.src.resume.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class ResumeService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ResumeDao resumeDao;

    @Autowired
    public ResumeService(ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    //이력서 생성
    public PostResumeRes createResume(PostResumeReq postResumeReq) throws BaseException {
        try {
            long resumeId = resumeDao.createResume(postResumeReq);
            return new PostResumeRes(resumeId);
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 리스트 조회
    public List<GetResumeListRes> getResumeList(long userId) throws BaseException{
        try {
            List<GetResumeListRes> getResumeListRes = resumeDao.getResumeList(userId);
            return getResumeListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 임시저장
    public void modifyResume(PatchResumeReq patchResumeReq) throws BaseException{
        try {
            int result = resumeDao.modifyResume(patchResumeReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_RESUME);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 작성완료
    public void completeResume(PatchResumeReq patchResumeReq) throws BaseException{
        try {
            int result = resumeDao.completeResume(patchResumeReq);
            if (result == 0){
                throw new BaseException(COMPLETE_FAIL_RESUME);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 삭제
    public void deleteResume(long resumeId) throws BaseException{
        try {
            int result = resumeDao.deleteResume(resumeId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_RESUME);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 경력 추가
    public PostCareerRes createCareer(long resumeId) throws BaseException{
        try {
            long careerId = resumeDao.createCareer(resumeId);
            return new PostCareerRes(careerId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 경력 수정
    public void modifyCareer(PatchCareerReq patchCareerReq) throws BaseException{
        try {
            int result = resumeDao.modifyCareer(patchCareerReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_CAREER);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 경력 삭제
    public void deleteCareer(long resumeId, long careerId) throws BaseException{
        try {
            int result = resumeDao.deleteCareer(resumeId, careerId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_CAREER);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 경력 리스트 조회
    public List<GetCareerListRes> getCareerList(long resumeId) throws BaseException{
        try {
            List<GetCareerListRes> getCareerListRes = resumeDao.getCareerList(resumeId);
            return getCareerListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //경력 성과 리스트 조회
    public List<GetResultListRes> getResultList(long careerId) throws BaseException{
        try {
            List<GetResultListRes> getCareerListRes = resumeDao.getResultList(careerId);
            return getCareerListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //경력 성과 추가
    public PostResultRes createResult(long careerId) throws BaseException{
        try {
            long resultId = resumeDao.createResult(careerId);
            return new PostResultRes(resultId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //경력 성과 수정
    public void modifyResult(PatchResultReq patchResultReq) throws BaseException{
        try {
            int result = resumeDao.modifyResult(patchResultReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_RESULT);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //성과 삭제
    public void deleteResult(long resultId) throws BaseException{
        try {
            int result = resumeDao.deleteResult(resultId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_RESULT);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 학력

    //이력서 학력 리스트 조회
    public List<GetEducationListRes> getEducationList(long resumeId) throws BaseException{
        try {
            List<GetEducationListRes> getEducationListRes = resumeDao.getEducationList(resumeId);
            return getEducationListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 학력 추가
    public PostEducationRes createEducation(long resumeId) throws BaseException{
        try {
            long educationId = resumeDao.createEducation(resumeId);
            return new PostEducationRes(educationId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 학력 수정
    public void modifyEducation(PatchEducationReq patchEducationReq) throws BaseException{
        try {
            int result = resumeDao.modifyEducation(patchEducationReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_EDUCATION);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 학력 삭제
    public void deleteEducation(long resumeId, long educationId) throws BaseException{
        try {
            int result = resumeDao.deleteEducation(resumeId, educationId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_EDUCATION);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**수상및 기타 **/
    //이력서 수상 및 기타 리스트 조회
    public List<GetEtcListRes> getEtcList(long resumeId) throws BaseException{
        try {
            List<GetEtcListRes> getEtcListRes = resumeDao.getEtcList(resumeId);
            return getEtcListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 수상 및 기타 추가
    public PostEtcRes createEtc(long resumeId) throws BaseException{
        try {
            long educationId = resumeDao.createEtc(resumeId);
            return new PostEtcRes(educationId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 수상 및 기타 수정
    public void modifyEtc(PatchEtcReq patchEtcReq) throws BaseException{
        try {
            int result = resumeDao.modifyEtc(patchEtcReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_ETC);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 수상 및 기타 삭제
    public void deleteEtc(long resumeId, long etcId) throws BaseException{
        try {
            int result = resumeDao.deleteEtc(resumeId, etcId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_ETC);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 외국어 **/

    //이력서 외국어 리스트 조회
    public List<GetLanguageListRes> getLanguageList(long resumeId) throws BaseException{
        try {
            List<GetLanguageListRes> getLanguageListRes = resumeDao.getLanguageList(resumeId);
            return getLanguageListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 외국어 추가
    public PostLanguageRes createLanguage(long resumeId) throws BaseException{
        try {
            long educationId = resumeDao.createLanguage(resumeId);
            return new PostLanguageRes(educationId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 외국어 수정
    public void modifyLanguage(PatchLanguageReq patchLanguageReq) throws BaseException{
        try {
            int result = resumeDao.modifyLanguage(patchLanguageReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_LANGUAGE);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이력서 외국어 삭제
    public void deleteLanguage(long resumeId, long languageId) throws BaseException{
        try {
            int result = resumeDao.deleteLanguage(resumeId, languageId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_LANGUAGE);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 링크 **/

    //링크 리스트 조회
    public List<GetLinkListRes> getLinkList(long resumeId) throws BaseException{
        try {
            List<GetLinkListRes> getLinkListRes = resumeDao.getLinkList(resumeId);
            return getLinkListRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //링크 추가
    public PostLinkRes createLink(long resumeId) throws BaseException{
        try {
            long educationId = resumeDao.createLink(resumeId);
            return new PostLinkRes(educationId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //링크 수정
    public void modifyLink(PatchLinkReq patchLinkReq) throws BaseException{
        try {
            int result = resumeDao.modifyLink(patchLinkReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_LINK);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //링크 삭제
    public void deleteLink(long resumeId, long linkId) throws BaseException{
        try {
            int result = resumeDao.deleteLink(resumeId, linkId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_LINK);
            }
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
