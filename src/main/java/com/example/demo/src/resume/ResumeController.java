package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.resume.model.GetResumeListRes;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import com.example.demo.src.resume.model.Resume;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
