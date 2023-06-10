package com.project.ihealme.kakaoMaps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ihealme.kakaoMaps.config.KakaoConfig;
import com.project.ihealme.kakaoMaps.dto.KakaoMapsDto;
import com.project.ihealme.kakaoMaps.dto.KakaoReservationDto;
import com.project.ihealme.kakaoMaps.entity.KakaoMapsEntity;
import com.project.ihealme.kakaoMaps.entity.KakaoReservationEntity;
import com.project.ihealme.kakaoMaps.repository.KakaoMapsRepository;
import com.project.ihealme.kakaoMaps.repository.KakaoReservationRepository;
import com.project.ihealme.user.dto.HospitalRequest;
import com.project.ihealme.user.entity.User;
import com.project.ihealme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoMapsService {

    private final KakaoMapsRepository kakaoMapsRepository;
    private final KakaoReservationRepository kakaoReservationRepository;
    private final UserRepository userRepository;

    // 카카오 REST API 데이터를 DTO로 변환
    public List<KakaoMapsDto> convertToKakaoMapsDto(String search) throws JsonProcessingException {
        // 카카오 API 호출하여 검색 결과를 받아옴
        String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + search;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 0a931d3e53412cb779f034fc86ec4c96");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // 검색 결과를 리스트로 변환하여 저장
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        List<KakaoMapsDto> kakaoList = new ArrayList<>();
        if (root.has("documents")) {
            JsonNode documents = root.get("documents");
            for (JsonNode document : documents) {
                Long id = Long.valueOf(document.get("id").asText());
                String placeName = document.get("place_name").asText();
                String phone = document.get("phone").asText();
                String roadAddressName = document.get("road_address_name").asText();
                String placeUrl = document.get("place_url").asText();
                String x = document.get("x").asText();
                String y = document.get("y").asText();
                KakaoMapsDto list = new KakaoMapsDto(id, placeName, phone, roadAddressName, placeUrl, x, y);
                kakaoList.add(list);
            }
        }
        return kakaoList;
    }

    //DTO -> 엔티티 변환
    public List<KakaoMapsEntity> convertToKakaoMapsEntity(List<KakaoMapsDto> dtos) {
        return dtos.stream()
                .map(dto -> new KakaoMapsEntity(dto.getId(), dto.getPlaceName(), dto.getPhone(),
                        dto.getRoadAddressName(), dto.getPlaceUrl(), dto.getX(), dto.getY()))
                .collect(Collectors.toList());
    }

    public void saveAllPlaces(List<KakaoMapsDto> dtos) {
        List<KakaoMapsEntity> entities = convertToKakaoMapsEntity(dtos);
        kakaoMapsRepository.saveAll(entities);
    }

    public List<KakaoMapsEntity> getAll() {
        return kakaoMapsRepository.findAll();
    }

    public boolean checkIfDataExist() {
        long count = kakaoMapsRepository.count();
        return count > 0;
    }

    public void deleteAllPlaces() {
        kakaoMapsRepository.deleteAll();
    }

    // 병원명, 병원 주소, 병원 번호 업데이트
    public boolean updateUserPlaceData(Long placeId, Long userId, User updatedUser) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                existingUser.setHptName(updatedUser.getHptName());
                existingUser.setHptAddress(updatedUser.getHptAddress());
                existingUser.setHptPhoneNum(updatedUser.getHptPhoneNum());
                userRepository.save(existingUser);
                return true;
            }
        } catch (Exception e) {
            // 예외 처리
        }
        return false;
    }

    // 예약하기
    public void saveReservation(KakaoReservationDto kakaoReservationDto) {

        // 환아명, 진료항목, 병원명
        KakaoReservationEntity kakaoReservationEntity = new KakaoReservationEntity();
        kakaoReservationEntity.setId(kakaoReservationDto.getId());
        kakaoReservationEntity.setPxName(kakaoReservationDto.getPxName());
        kakaoReservationEntity.setTxtList(kakaoReservationDto.getTxtList());
        kakaoReservationEntity.setSelectedPlaceName(kakaoReservationDto.getSelectedPlaceName());

        // DB에 저장합니다.
        kakaoReservationRepository.save(kakaoReservationEntity);
    }

    // 병원 정보 업데이트
    public void updateUserInformation(String hptName, String hptAddress, String hptPhoneNum) {
        // 현재 로그인된 사용자의 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = ((UserDetails) principal).getUsername();

        // 사용자 정보 가져오기
        User user = (User) userRepository.findByName(name);

        // 컬럼 값 설정
        user.setHptName(hptName);
        user.setHptAddress(hptAddress);
        user.setHptPhoneNum(hptPhoneNum);

        // 엔티티 저장
        userRepository.save(user);
    }
}