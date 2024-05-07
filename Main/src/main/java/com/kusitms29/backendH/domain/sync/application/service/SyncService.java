//package com.kusitms29.backendH.domain.sync.application.service;
//
//import com.kusitms29.backendH.domain.category.domain.Category;
//import com.kusitms29.backendH.domain.category.repository.CategoryRepository;
//import com.kusitms29.backendH.domain.participation.domain.Participation;
//import com.kusitms29.backendH.domain.participation.repository.ParticipationRepository;
//import com.kusitms29.backendH.domain.sync.application.controller.dto.request.SyncCreateRequestDto;
//import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncCreateResponseDto;
//import com.kusitms29.backendH.domain.sync.domain.Sync;
//import com.kusitms29.backendH.domain.sync.domain.SyncType;
//import com.kusitms29.backendH.domain.sync.repository.SyncRepository;
//import com.kusitms29.backendH.domain.user.domain.User;
//import com.kusitms29.backendH.domain.user.repository.UserRepository;
//import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
//import com.kusitms29.backendH.infra.config.AwsS3Service;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;
//
//import static com.kusitms29.backendH.global.error.ErrorCode.CATEGORY_NOT_FOUND;
//import static com.kusitms29.backendH.global.error.ErrorCode.USER_NOT_FOUND;
//
//@Slf4j
//@RequiredArgsConstructor
//@Transactional
//@Service
//public class SyncService {
//    private final AwsS3Service awsS3Service;
//    private final SyncRepository syncRepository;
//    private final CategoryRepository categoryRepository;
//    private final UserRepository userRepository;
//    private final ParticipationRepository participationRepository;
//    @Transactional
//    public SyncCreateResponseDto createSync(Long userId, MultipartFile image, SyncCreateRequestDto syncCreateRequestDto) {
//        //1. Sync 저장
//
//        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
//        //link
//        String link = UUID.randomUUID().toString();
//        //syncType
//        SyncType syncType = SyncType.getEnumFROMStringSyncType(syncCreateRequestDto.getSyncType());
//        //parentCategory
//        Category parentCategory = categoryRepository.findByName(syncCreateRequestDto.getParentCategory())
//                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
//        //childCategory
//        Category childCategory = categoryRepository.findByName(syncCreateRequestDto.getChildCategory())
//                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
//        //image
//        String fileUrl = awsS3Service.uploadImage(image);
//        //date (형식확인필요)
//        String date = syncCreateRequestDto.getDate();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
//
//        Sync newSync = Sync.createSync(user, link, syncType, parentCategory, childCategory,
//                syncCreateRequestDto.getName(), fileUrl, syncCreateRequestDto.getComment(), syncCreateRequestDto.getLocation(), localDateTime,
//                syncCreateRequestDto.getMeeting_cnt(), syncCreateRequestDto.getMember_min(), syncCreateRequestDto.getMember_max());
//        syncRepository.save(newSync);
//
//        //2. Particiaption 저장
//
//        Participation ownerParticipation = Participation.createParticipation(user, newSync);
//        participationRepository.save(ownerParticipation);
//
//        return SyncCreateResponseDto.of(
//                newSync.getId(),
//                link,
//                String.valueOf(syncType),
//                parentCategory.getName(),
//                childCategory.getName(),
//                syncCreateRequestDto.getName(),
//                fileUrl,
//                syncCreateRequestDto.getComment(),
//                syncCreateRequestDto.getLocation(),
//                syncCreateRequestDto.getDate(),
//                syncCreateRequestDto.getMeeting_cnt(),
//                syncCreateRequestDto.getMember_min(),
//                syncCreateRequestDto.getMember_max()
//        );
//    }
//
//
//
//
//}