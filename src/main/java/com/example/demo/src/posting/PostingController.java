package com.example.demo.src.posting;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.posting.model.PostApplyReq;
import com.example.demo.src.posting.model.PostApplyRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
