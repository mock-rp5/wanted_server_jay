package com.example.demo.src.posting;

import com.example.demo.config.BaseException;
import com.example.demo.src.posting.model.PostApplyReq;
import com.example.demo.src.posting.model.PostApplyRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostApplyRes applyPosting(PostApplyReq postApplyReq) throws BaseException {
        try {

            long applyId = postingDao.applyPosting(postApplyReq);
            return new PostApplyRes(applyId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
