package com.practice.facility.auth.repository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.practice.facility.auth.dto.UserDto;
import com.practice.facility.auth.model.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUserDto_whenFindByEmail_thenFindOneMember() {
        //given
        UserDto userDto = new UserDto();
        userDto.setEmail("tester@gmail.com");
        userDto.setPassword("123");
        userDto.setUserId("qweasdzxc123");
        userDto.setName("테스터");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setCreatedAt(Instant.now());
        userEntity.setUpdatedAt(Instant.now());

        userRepository.save(userEntity);

        //when
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userDto.getEmail());
        optionalUser.orElse(null);

        //then
        Assertions.assertThat(optionalUser.isPresent()).isEqualTo(true);
    }


}
