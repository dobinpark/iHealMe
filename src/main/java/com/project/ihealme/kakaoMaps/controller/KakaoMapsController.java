package com.project.ihealme.kakaoMaps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ihealme.kakaoMaps.dto.KakaoMapsDto;
import com.project.ihealme.kakaoMaps.dto.KakaoReservationDto;
import com.project.ihealme.kakaoMaps.entity.KakaoMapsEntity;
import com.project.ihealme.kakaoMaps.repository.KakaoMapsRepository;
import com.project.ihealme.kakaoMaps.repository.KakaoReservationRepository;
import com.project.ihealme.kakaoMaps.service.KakaoMapsService;
import com.project.ihealme.user.entity.User;
import com.project.ihealme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoMapsController {

    private final KakaoMapsService kakaoMapsService;
    private final KakaoMapsRepository kakaoMapsRepository;
    private final KakaoReservationRepository kakaoReservationRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String searchPlace() {
        return "maps/main";
    }

    @GetMapping("/places")
    public String placeList(@RequestParam String search, Model model) throws JsonProcessingException {
        List<KakaoMapsDto> places = kakaoMapsService.convertToKakaoMapsDto(search);
        boolean isDataExist = kakaoMapsService.checkIfDataExist();

        if (isDataExist) {
            kakaoMapsService.deleteAllPlaces();
        }
        kakaoMapsService.saveAllPlaces(places);

        List<KakaoMapsEntity> entities = kakaoMapsService.convertToKakaoMapsEntity(places);

        model.addAttribute("places", entities);
        return "maps/searchList";
    }

    @PutMapping("/places/{placeId}")
    public ResponseEntity<String> updateUserData(@PathVariable Long placeId, @RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUser = authentication.getName();

        try {
            boolean success = kakaoMapsService.updateUserPlaceData(placeId, Long.valueOf(loggedInUser), updatedUser);
            if (success) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while updating user data");
        }
    }

    @GetMapping("/reservation")
    public String kakaoreservation(Model model) {
        Long id = 1L; // 예약 폼에 표시할 ID 값
        String selectedPlaceName = ""; // 선택한 병원명을 표시할 변수
        String pxName = ""; // 예약자 이름을 표시할 변수
        List<String> options = Arrays.asList("소아진료", "영유아검진", "예방접종"); // 예약 옵션 리스트

        model.addAttribute("id", id);
        model.addAttribute("selectedPlaceName", selectedPlaceName);
        model.addAttribute("pxName", pxName);
        model.addAttribute("options", options);
        return "maps/reservation";
    }

    @PostMapping("/reservation")
    @ResponseBody
    public void saveReservation(@ModelAttribute KakaoReservationDto kakaoReservationDto) {
        kakaoMapsService.saveReservation(kakaoReservationDto);
    }
}
