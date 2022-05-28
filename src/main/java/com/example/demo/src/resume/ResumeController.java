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
    public BaseResponse<PostResumeRes> createResume(@PathVariable long userId, @RequestBody Resume resume){
        try {
            //jwt
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostResumeReq postResumeReq = new PostResumeReq(userId, resume.getIntroduce(), resume.getResumeTitle(), resume.getName(), resume.getEmail(), resume.getPhone());
            PostResumeRes postResumeRes = resumeService.createResume(postResumeReq);
            return new BaseResponse<>(postResumeRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

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

}
