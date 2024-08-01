package com.example.mp.service;

import com.example.mp.dto.JoinDto;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JoinServiceImpl implements JoinService {
    /*
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    */
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean joinProcess(JoinDto joinDto) {
        // 동일한 username을 사용하고 있는지 확인
        if (userRepository.existsByUsername(joinDto.getUsername()))
            return false;

        // 패스워드와 패스워드 확인이 일치하는지 확인
        if (!joinDto.checkPassword())
            return false;

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.typeMap(JoinDto.class, UserEntity.class).addMappings(mapper -> {
//            mapper.skip(UserEntity::setId); // ID는 자동 증가하므로 설정하지 않음
//            // 추가적인 커스텀 매핑 설정이 필요할 경우
//        });
//
//        UserEntity userEntity = modelMapper.map(joinDto, UserEntity.class);
//        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword())); // 비밀번호 암호화
//        userEntity.setCreateTime(LocalDateTime.now()); // 생성 시간 설정
//        userEntity.setRole("ROLE_USER"); // 역할 설정
//
//         JoinDto의 값을 UserEntity에 설정
        UserEntity userEntity = new ModelMapper().map(joinDto, UserEntity.class);


//         패스워드 암호화 처리 및 역할을 설정
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("ROLE_USER");	// 사용자 역할을 구분하는 문자로 "ROLE_" 접두어를 사용
//        userEntity.setRole("USER");

        // ----- 데이터베이스에 저장 안돼서 추가해보는 중..
//        userEntity.setCreateTime(LocalDateTime.now());

//         UserEntity를 저장
        try {
            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

