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

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
     * 채용 공고 북마크
     * [Post] /bookmarks/{postingId}/{userId}
     */
    @ResponseBody
    @PostMapping("bookmarks/{postingId}/{userId}")
    public BaseResponse<String> bookmarkPosting(@PathVariable long postingId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);

            postingService.bookMarkPosting(postingId, userId);
            String result = "북마크 등록";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 북마크 취소
     * [Patch] /bookmarks/{postingId}/{userId}
     */
    @ResponseBody
    @PatchMapping("bookmarks/{postingId}/{userId}")
    public BaseResponse<String> cancelBookMark(@PathVariable long postingId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            postingService.cancelBookMark(postingId, userId);
            String result = "북마크 해제";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 북마크한 채용공고 조회
     * [Get] /bookmarks/users/:userId
     */
    @ResponseBody
    @GetMapping("bookmarks/users/{userId}")
    public BaseResponse<List<GetBookMarkPostingRes>> getBookMarkPostings(@PathVariable("userId") int userId){
        try {
            //jwt 인가 부분 **************************************
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************
            List<GetBookMarkPostingRes> getBookMarkPostingRes = postingService.getBookMarkPostings(userId);
            return new BaseResponse<>(getBookMarkPostingRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    //좋아요
    /**
     * 채용 공고 좋아요
     * [Post] /like/{postingId}/{userId}
     */
    @ResponseBody
    @PostMapping("like/{postingId}/{userId}")
    public BaseResponse<String> likePosting(@PathVariable long postingId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);

            postingService.likePosting(postingId, userId);
            String result = "좋아요 등록";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 좋아요 취소
     * [Patch] /like/{postingId}/{userId}
     */
    @ResponseBody
    @PatchMapping("like/{postingId}/{userId}")
    public BaseResponse<String> cancelLike(@PathVariable long postingId, @PathVariable long userId){
        try {
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            postingService.cancelLike(postingId, userId);
            String result = "좋아요 해제";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 좋아요한 채용공고 조회
     * [Get] /like/users/:userId
     */
    @ResponseBody
    @GetMapping("like/users/{userId}")
    public BaseResponse<List<GetlikePostingRes>> getLikePostings(@PathVariable("userId") int userId){
        try {
            //jwt 인가 부분 **************************************
            long userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt)
                return new BaseResponse<>(INVALID_USER_JWT);
            //*************************************************
            List<GetlikePostingRes> getlikePostingRes = postingService.getLikePostings(userId);
            return new BaseResponse<>(getlikePostingRes);
        } catch (BaseException e){
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
