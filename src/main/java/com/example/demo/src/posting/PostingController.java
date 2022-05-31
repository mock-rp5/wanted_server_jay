package com.example.demo.src.posting;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.posting.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/postings")
public class PostingController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostingService postingService;
    @Autowired
    private final JwtService jwtService;

    public PostingController(PostingService postingService, JwtService jwtService) {
        this.postingService = postingService;
        this.jwtService = jwtService;
    }

    /**
     * 채용공고 지원하기
     * [Post] /{postingId}/{userId}
     */
    @ResponseBody
    @PostMapping("{postingId}/{userId}")
    public BaseResponse<PostApplyRes> applyPosting(@PathVariable long postingId, @PathVariable long userId, @RequestBody PostApplyReq apply){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            PostApplyReq postApplyReq = new PostApplyReq(
                    postingId,
                    userId,
                    apply.getName(),
                    apply.getEmail(),
                    apply.getPhone(),
                    apply.getResumeId());
            PostApplyRes postApplyRes = postingService.applyPosting(postApplyReq);
            return new BaseResponse<>(postApplyRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채용 공고 상세 조회
     * [Get] /{postingId}
     */
    @ResponseBody
    @GetMapping("{postingId}")
    public BaseResponse<GetPostingRes> getPostingDetail(@PathVariable long postingId){
        try {
             GetPostingRes getPostingRes = postingService.getPostingDetail(postingId);
             return new BaseResponse<>(getPostingRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채용 공고 리스트 조회
     * [Get] /{jobGroupId}
     * /app/postings/:jobGroupId?job_sort={sort_type}&skill_tags={tag}&locations={location}&user_tags={tag}&job_selected={job}
     * https://www.wanted.co.kr/wdlist/518/872?job_sort=company.response_rate_order&years=0&skill_tags=3078&skill_tags=3451&locations=all
     */

//    @ResponseBody
//    @GetMapping("{jobGroupId}")
//    public BaseResponse<List<GetPostingListRes>> getPostingList(
//            @PathVariable long jobGroupId,
//            @RequestParam(required = false, defaultValue = "popular") String sortType,
//            @RequestParam(required = false) List<String> location,
//            @RequestParam(required = false) List<String> skillTags,
//            @RequestParam(required = false, defaultValue = "0") long years,
//            @RequestParam(required = false) List<String> userTags,
//            @RequestParam(required = false) List<String> jobSelected){
//        try {
//            GetPostingListReq getPostingListReq = new GetPostingListReq(
//                    jobGroupId,
//                    sortType,
//                    location,
//                    skillTags,
//                    years,
//                    userTags,
//                    jobSelected
//            );
//            List<GetPostingListRes> getPostingListRes = postingService.getPostingList(getPostingListReq);
//            return new BaseResponse<>(getPostingListRes);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
}
