package com.example.demo.src.skill;

import com.example.demo.config.BaseException;
import com.example.demo.src.resume.model.GetResumeListRes;
import com.example.demo.src.skill.model.GetSkillsRes;
import com.example.demo.src.skill.model.PostSkillsRes;
import com.example.demo.src.skill.model.SearchSkillsReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class SkillService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SkillDao skillDao;

    @Autowired
    public SkillService(SkillDao skillDao) {
        this.skillDao = skillDao;
    }
    //스킬 조회
    public List<GetSkillsRes> getSkills()  throws BaseException {
        try {
            List<GetSkillsRes> getSkillsRes = skillDao.getSkills();
            return getSkillsRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //스킬 검색
    public List<PostSkillsRes> searchSkills(SearchSkillsReq searchSkillsReq) throws BaseException {
        try {
            List<PostSkillsRes> searchSkills = skillDao.searchSkills(searchSkillsReq);
            return searchSkills;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
