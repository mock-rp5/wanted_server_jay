package com.example.demo.src.skill;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.skill.model.GetSkillsRes;
import com.example.demo.src.skill.model.PostSkillsRes;
import com.example.demo.src.skill.model.SearchSkillsReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/app/skills")
public class SkillController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SkillService skillService;
    @Autowired
    private final JwtService jwtService;

    public SkillController(SkillService skillService, JwtService jwtService) {
        this.skillService = skillService;
        this.jwtService = jwtService;
    }

    /**
     * 스킬 조회
     * [Get] /
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetSkillsRes>> getSkills(){
        try {
            List<GetSkillsRes> getSkillsRes =skillService.getSkills();
            return new BaseResponse<>(getSkillsRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 스킬 검색
     * [Post] /
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<List<PostSkillsRes>> searchSkills(@RequestBody SearchSkillsReq searchSkillsReq){
        try {
            List<PostSkillsRes> searchSkills = skillService.searchSkills(searchSkillsReq);
            return new BaseResponse<>(searchSkills);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
