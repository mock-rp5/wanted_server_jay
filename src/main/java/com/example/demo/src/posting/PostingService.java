package com.example.demo.src.posting;

import com.example.demo.config.BaseException;
import com.example.demo.src.posting.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class PostingService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostingDao postingDao;

    @Autowired
    public PostingService(PostingDao postingDao) {
        this.postingDao = postingDao;
    }

    //이력서 지원
    public PostApplyRes applyPosting(PostApplyReq postApplyReq) throws BaseException {
        try {
            long applyId = postingDao.applyPosting(postApplyReq);
            return new PostApplyRes(applyId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채용 공고 상세 조회
    public GetPostingRes getPostingDetail(long postingId) throws BaseException {
        try {
            GetPosting getPosting = postingDao.getPosting(postingId);
            List<String> pictures = postingDao.getPictures(postingId);
            List<String> tags = postingDao.getTags(postingId);

            return new GetPostingRes(
                    getPosting.getPostingId(),
                    getPosting.getCompanyId(),
                    getPosting.getCompanyName(),
                    getPosting.getCompanyPlace(),
                    pictures,
                    tags,
                    getPosting.getTitle(),
                    getPosting.getDetail(),
                    getPosting.getRecommendMoney(),
                    getPosting.getApplyMoney(),
                    getPosting.getPlaceFull());
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    // 채용 공고 리스트 조회
//    public List<GetPostingListRes> getPostingList(GetPostingListReq getPostingListReq) throws BaseException {
//        try {
//            List<GetPostingListRes> getPostingListRes = postingDao.getPostingList(getPostingListReq);
//            return getPostingListRes;
//        } catch (Exception e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


}
