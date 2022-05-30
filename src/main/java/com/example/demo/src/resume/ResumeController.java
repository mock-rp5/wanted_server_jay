package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.resume.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/resumes")
public class ResumeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ResumeService resumeService;
    @Autowired
    private final JwtService jwtService;

    public ResumeController(ResumeService resumeService, JwtService jwtService) {
        this.resumeService = resumeService;
        this.jwtService = jwtService;
    }

    /**
     * 이력서 작성 API
     * [Post] /{userId}
     */
    @ResponseBody
    @PostMapping("{userId}")
    public BaseResponse<PostResumeRes> createResume(@PathVariable long userId){
        try {
            //jwt
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostResumeRes postResumeRes = resumeService.createResume(userId);
            return new BaseResponse<>(postResumeRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 기본 조회
     * [Get] /{resumeId}/{userId}
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}")
    public BaseResponse<GetResumeBasicRes> getResumeBasic(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            GetResumeBasicRes getResumeBasicRes = resumeService.getResumeBasic(resumeId, userId);
            return new BaseResponse<>(getResumeBasicRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

//    /**
//     * 이력서 상세 조회
//     * [Get] /{resumeId}/{userId}/all
//     */
//    @ResponseBody
//    @GetMapping("{resumeId}/{userId}/all")
//    public BaseResponse<GetResumeDetailRes> getResumeDetail(@PathVariable long resumeId, @PathVariable long userId){
//        try {
//            long userIdByJwt = jwtService.getUserId();
//            if (userId != userIdByJwt)
//                return new BaseResponse<>(INVALID_USER_JWT);
//            GetResumeDetailRes getResumeDetailRes = resumeService.getResumeDetail(resumeId, userId);
//            return new BaseResponse<>(getResumeDetailRes);
//        } catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

    /**
     * 이력서 목록 조회 API
     * [Get] /{userId}
     */
    @ResponseBody
    @GetMapping("{userId}")
    public BaseResponse<List<GetResumeListRes>> getResumeList(@PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetResumeListRes> getResumeListRes = resumeService.getResumeList(userId);
            return new BaseResponse<>(getResumeListRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 임시저장 API
     * [Patch] /{resumeId}/{userId}
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}")
    public BaseResponse<String> modifyResume(@PathVariable long resumeId, @PathVariable long userId, @RequestBody Resume resume){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchResumeReq patchResumeReq = new PatchResumeReq(
                    userId,
                    resumeId,
                    resume.getIntroduce(),
                    resume.getResumeTitle(),
                    resume.getName(),
                    resume.getEmail(),
                    resume.getPhone());
            resumeService.modifyResume(patchResumeReq);

            String result = "이력서가 임시저장 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 이력서 작성완료 API
     * [Patch] /{resumeId}/{userId}/complete
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/complete")
    public BaseResponse<String> completeResume(@PathVariable long resumeId, @PathVariable long userId, @RequestBody Resume resume){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchResumeReq patchResumeReq = new PatchResumeReq(
                    userId,
                    resumeId,
                    resume.getIntroduce(),
                    resume.getResumeTitle(),
                    resume.getName(),
                    resume.getEmail(),
                    resume.getPhone());
            resumeService.completeResume(patchResumeReq);

            String result = "이력서가 작성완료 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 삭제 API
     * [Patch] /{resumeId}/{userId}/status
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/status")
    public BaseResponse<String> completeResume(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteResume(resumeId);

            String result = "이력서가 삭제 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 경력 추가 API
     * [Post] /{resumeId}/{userId}/career
     */
    @ResponseBody
    @PostMapping("{resumeId}/{userId}/career")
    public BaseResponse<PostCareerRes> createCareer(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostCareerRes postCareerRes = resumeService.createCareer(resumeId);
            return new BaseResponse<>(postCareerRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 경력 수정 API
     * [Patch] /{resumeId}/{userId}/career/{careerId}
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/career/{careerId}")
    public BaseResponse<String> modifyCareer(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long careerId,@RequestBody PatchCareerReq career){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchCareerReq patchCareerReq = new PatchCareerReq(
                    resumeId,
                    careerId,
                    career.getStartAt(),
                    career.getEndAt(),
                    career.getCompanyName(),
                    career.getDepartPosition(),
                    career.getTenure(),
                    career.getNow()
            );
            resumeService.modifyCareer(patchCareerReq);

            String result = "경력이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 이력서 경력 삭제 API
     * [Delete] /{resumeId}/{userId}/career/{careerId}
     */
    @ResponseBody
    @DeleteMapping("{resumeId}/{userId}/career/{careerId}")
    public BaseResponse<String> deleteCareer(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long careerId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteCareer(resumeId, careerId);

            String result = "경력이 삭제 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 경력 조회 API
     * [Get] /{resumeId}/{userId}/career
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}/career")
    public BaseResponse<List<GetCareerListRes>> getCareerList(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetCareerListRes> getCareerListRes = resumeService.getCareerList(resumeId);
            return new BaseResponse<>(getCareerListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 경력 주요 성과 조회 API
     * [Get /{userId}/career/{careerId}
     */
    @ResponseBody
    @GetMapping("{userId}/career/{careerId}")
    public BaseResponse<List<GetResultListRes>> getResultList(@PathVariable long userId, @PathVariable long careerId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetResultListRes> getResultListRes = resumeService.getResultList(careerId);
            return new BaseResponse<>(getResultListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 경력 주요 성과 추가 API
     * [Post] /{userId}/career/{careerId}
     */
    @ResponseBody
    @PostMapping("{userId}/career/{careerId}")
    public BaseResponse<PostResultRes> createResult(@PathVariable long userId, @PathVariable long careerId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostResultRes postResultRes = resumeService.createResult(careerId);
            return new BaseResponse<>(postResultRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 경력 주요 성과 수정 API
     * [Patch] /{userId}/result/{resultId}
     */
    @ResponseBody
    @PatchMapping("{userId}/result/{resultId}")
    public BaseResponse<String> modifyResult(@PathVariable long userId, @PathVariable long resultId,@RequestBody PatchResultReq result){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchResultReq patchResultReq = new PatchResultReq(
                    resultId,
                    result.getStartAt(),
                    result.getEndAt(),
                    result.getResult()
            );
            resumeService.modifyResult(patchResultReq);
            String resultMsg = "성과가 수정되었습니다.";
            return new BaseResponse<>(resultMsg);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 경력 주요 성과 삭제 API
     * [Delete] /{userId}/result/{resultId}
     */
    @ResponseBody
    @DeleteMapping("{userId}/result/{resultId}")
    public BaseResponse<String> deleteResult(@PathVariable long userId, @PathVariable long resultId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteResult(resultId);

            String result = "성과가 삭제 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**이력서 학력**/

    /**
     * 이력서 학력 조회 API
     * [Get] /{resumeId}/{userId}/education
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}/education")
    public BaseResponse<List<GetEducationListRes>> getEducationList(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetEducationListRes> getEducationListRes = resumeService.getEducationList(resumeId);
            return new BaseResponse<>(getEducationListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 학력 추가 API
     * [Post] /{resumeId}/{userId}/education
     */
    @ResponseBody
    @PostMapping("{resumeId}/{userId}/education")
    public BaseResponse<PostEducationRes> createEducation(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostEducationRes postEducationRes = resumeService.createEducation(resumeId);
            return new BaseResponse<>(postEducationRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 학력 수정 API
     * [Patch] /{resumeId}/{userId}/education/{educationId}
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/education/{educationId}")
    public BaseResponse<String> modifyEducation(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long educationId,@RequestBody PatchEducationReq education){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchEducationReq patchEducationReq = new PatchEducationReq(
                    educationId,
                    education.getStartAt(),
                    education.getEndAt(),
                    education.getNow(),
                    education.getName(),
                    education.getMajor(),
                    education.getInfo()
            );
            resumeService.modifyEducation(patchEducationReq);

            String result = "학력이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 이력서 학력 삭제 API
     * [Delete] /{resumeId}/{userId}/education/{educationId}
     */
    @ResponseBody
    @DeleteMapping("{resumeId}/{userId}/education/{educationId}")
    public BaseResponse<String> delete(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long educationId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteEducation(resumeId, educationId);

            String result = "학력이 삭제 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**수상및 기타 **/

    /**
     * 수상 및 기타 조회 API
     * [Get] /{resumeId}/{userId}/etc
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}/etc")
    public BaseResponse<List<GetEtcListRes>> getEtcList(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetEtcListRes> getEtcListRes = resumeService.getEtcList(resumeId);
            return new BaseResponse<>(getEtcListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 수상 및 기타 추가 API
     * [Post] /{resumeId}/{userId}/etc
     */
    @ResponseBody
    @PostMapping("{resumeId}/{userId}/etc")
    public BaseResponse<PostEtcRes> createEtc(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostEtcRes postEtcRes = resumeService.createEtc(resumeId);
            return new BaseResponse<>(postEtcRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 수상 및 기타 수정 API
     * [Patch] /{resumeId}/{userId}/etc/{etcId}
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/etc/{etcId}")
    public BaseResponse<String> modifyEtc(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long etcId,@RequestBody PatchEtcReq etc){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchEtcReq patchEtcReq = new PatchEtcReq(
                    etcId,
                    etc.getDate(),
                    etc.getName(),
                    etc.getDetail()
            );
            resumeService.modifyEtc(patchEtcReq);

            String result = "수상 및 기타가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 수상 및 기타 삭제 API
     * [Delete] /{resumeId}/{userId}/etc/{etcId}
     */
    @ResponseBody
    @DeleteMapping("{resumeId}/{userId}/etc/{etcId}")
    public BaseResponse<String> deleteEtc(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long etcId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteEtc(resumeId, etcId);

            String result = "수상 및 기타가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /** 외국어 **/

    /**
     * 외국어 조회 API
     * [Get] /{resumeId}/{userId}/etc
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}/language")
    public BaseResponse<List<GetLanguageListRes>> getLanguageList(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetLanguageListRes> getLanguageListRes = resumeService.getLanguageList(resumeId);
            return new BaseResponse<>(getLanguageListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 외국어 추가 API
     * [Post] /{resumeId}/{userId}/language
     */
    @ResponseBody
    @PostMapping("{resumeId}/{userId}/language")
    public BaseResponse<PostLanguageRes> createLanguage(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostLanguageRes postLanguageRes = resumeService.createLanguage(resumeId);
            return new BaseResponse<>(postLanguageRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 외국어 수정 API
     * [Patch] /{resumeId}/{userId}/language/{languageId}
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/language/{languageId}")
    public BaseResponse<String> modifyLanguage(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long languageId,@RequestBody PatchLanguageReq language){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchLanguageReq patchLanguageReq = new PatchLanguageReq(
                    languageId,
                    language.getLanguage(),
                    language.getTestName(),
                    language.getScore(),
                    language.getTestAt()
            );
            resumeService.modifyLanguage(patchLanguageReq);

            String result = "외국어가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 외국어 삭제 API
     * [Delete] /{resumeId}/{userId}/language/{languageId}
     */
    @ResponseBody
    @DeleteMapping("{resumeId}/{userId}/language/{languageId}")
    public BaseResponse<String> deleteLanguage(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long languageId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteLanguage(resumeId, languageId);

            String result = "외국어가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /** 링크 **/

    /**
     * 링크 조회 API
     * [Get] /{resumeId}/{userId}/link
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}/link")
    public BaseResponse<List<GetLinkListRes>> getLinkList(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetLinkListRes> getLinkListRes = resumeService.getLinkList(resumeId);
            return new BaseResponse<>(getLinkListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 링크 추가 API
     * [Post] /{resumeId}/{userId}/link
     */
    @ResponseBody
    @PostMapping("{resumeId}/{userId}/link")
    public BaseResponse<PostLinkRes> createLink(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostLinkRes postLinkRes = resumeService.createLink(resumeId);
            return new BaseResponse<>(postLinkRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 링크 수정 API
     * [Patch] /{resumeId}/{userId}/link/{linkId}
     */
    @ResponseBody
    @PatchMapping("{resumeId}/{userId}/link/{linkId}")
    public BaseResponse<String> modifyLink(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long linkId,@RequestBody PatchLinkReq link){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PatchLinkReq patchLinkReq = new PatchLinkReq(
                    linkId,
                    link.getLink()
            );
            resumeService.modifyLink(patchLinkReq);

            String result = "링크가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 링크 삭제 API
     * [Delete] /{resumeId}/{userId}/link/{linkId}
     */
    @ResponseBody
    @DeleteMapping("{resumeId}/{userId}/link/{linkId}")
    public BaseResponse<String> deleteLink(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long linkId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteLink(resumeId, linkId);

            String result = "링크가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /** 이력서 스킬 **/

    /**
     * 이력서 스킬 조회 API
     * [Get] /{resumeId}/{userId}/skill
     */
    @ResponseBody
    @GetMapping("{resumeId}/{userId}/skill")
    public BaseResponse<List<GetResumeSkillListRes>> getResumeSkillList(@PathVariable long resumeId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            List<GetResumeSkillListRes> getResumeSkillListRes = resumeService.getResumeSkillList(resumeId);
            return new BaseResponse<>(getResumeSkillListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 이력서 스킬 추가 API
     * [Post] /{resumeId}/{userId}/skill/{skillId}
     */
    @ResponseBody
    @PostMapping("{resumeId}/{userId}/skill/{skillId}")
    public BaseResponse<PostResumeSkillRes> createResumeSkill(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long skillId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostResumeSkillRes postResumeSkillRes = resumeService.createResumeSkill(resumeId, skillId);
            return new BaseResponse<>(postResumeSkillRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    /**
     * 이력서 스킬 삭제 API
     * [Delete] /{resumeId}/{userId}/skill/{skillId}
     */
    @ResponseBody
    @DeleteMapping("{resumeId}/{userId}/skill/{skillId}")
    public BaseResponse<String> deleteResumeSkill(@PathVariable long resumeId, @PathVariable long userId, @PathVariable long skillId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            resumeService.deleteResumeSkill(resumeId, skillId);

            String result = "이력서의 스킬이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
