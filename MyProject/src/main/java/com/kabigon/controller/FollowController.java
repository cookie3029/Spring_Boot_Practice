package com.kabigon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kabigon.dto.Follow.FollowResponseDTO;
import com.kabigon.dto.Follow.FollowStateResponseDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.service.follow.FollowService;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("setFollow/{userNo}")
    public ResponseEntity<?> follow(@PathVariable Long userNo, @RequestParam Long partnerNo){
    	FollowResponseDTO dto = followService.setFollow(userNo, partnerNo);
    	
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("unFollow/{userNo}")
    public ResponseEntity<?> unFollow(@PathVariable Long userNo, @RequestParam Long partnerNo){
    	FollowResponseDTO dto = followService.setUnFollow(userNo, partnerNo);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("follower/{userNo}")
    public ResponseEntity<?> followerList(@PathVariable Long userNo){
    	List<UserResponseDTO> list = followService.followerList(userNo);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("following/{userNo}")
    public ResponseEntity<?> followingList(@PathVariable Long userNo){
    	List<UserResponseDTO> list = followService.followingList(userNo);
    	
    	return ResponseEntity.ok().body(list);
    }

    @GetMapping("followBackList/{userNo}")
    public ResponseEntity<?> getFollowBackList(@PathVariable Long userNo){
    	List<UserResponseDTO> list = followService.followBackList(userNo);
    	
    	return ResponseEntity.ok().body(list);
    }

    @GetMapping("/isFollowBack/{userNo}/{partnerNo}")
    public ResponseEntity<?> followEachOther(@PathVariable Long userNo, @PathVariable Long partnerNo){
        FollowResponseDTO dto = followService.isfollowBack(userNo, partnerNo);
        
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/followState/search")
    public ResponseEntity<?> findNameUser(@RequestParam(value = "input") String inputName){
        List<FollowStateResponseDTO> list = followService.followState(inputName);
        
        return ResponseEntity.ok().body(list);
    }
}
