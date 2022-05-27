package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.src.resume.model.GetResumeListRes;
import com.example.demo.src.resume.model.PatchResumeReq;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
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
}
