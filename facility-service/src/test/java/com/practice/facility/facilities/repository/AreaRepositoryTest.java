package com.practice.facility.facilities.repository;

import com.practice.facility.facilities.model.AreaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;

    private static AreaEntity areaEntity1;
    private static AreaEntity areaEntity2;
    private static AreaEntity areaEntity3;

    @BeforeEach
    void setUp() {
        areaRepository.deleteAll();

        areaEntity1 = new AreaEntity("해운대구", "000001", true);
        areaEntity2 = new AreaEntity("동래구", "000002", true);
        areaEntity3 = new AreaEntity("금정구", "000003", true);
    }

    @DisplayName("area 저장 테스트")
    @Test
    void saveAreaTest() {
        //given

        //when
        AreaEntity savedEntity = areaRepository.save(areaEntity1);

        //then
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isGreaterThan(0);
        assertThat(savedEntity.getName()).isEqualTo("해운대구");
    }

    @DisplayName("area 전체 목록 조회 테스트")
    @Test
    void getAllListTest() {
        //given

        areaRepository.save(areaEntity1);
        areaRepository.save(areaEntity2);
        areaRepository.save(areaEntity3);

        //when
        List<AreaEntity> areas = areaRepository.findAll();

        //test
        assertThat(areas.size()).isEqualTo(3);
    }

    @DisplayName("area id로 조회 테스트")
    @Test
    void getAreaByIdTest() {
        //given
        areaRepository.save(areaEntity1);
        areaRepository.save(areaEntity2);
        areaRepository.save(areaEntity3);

        //when
        AreaEntity area = areaRepository.findById(areaEntity1.getId()).get();

        //then
        assertThat(area).isNotNull();
        assertThat(area.getName()).isEqualTo("해운대구");
    }

    @DisplayName("area 수정 테스트")
    @Test
    void updateAreaTest() {
        //given
        areaRepository.save(areaEntity1);
        areaRepository.save(areaEntity2);

        //when
        AreaEntity savedArea = areaRepository.findById(areaEntity1.getId()).get();

        savedArea.updateNameAndCode("해운대구-수정", "010101");
        AreaEntity updatedArea = areaRepository.save(savedArea);

        //then
        List<AreaEntity> areas = areaRepository.findAll();
        assertThat(updatedArea.getName()).contains("수정");
        assertThat(areas.size()).isEqualTo(2);
    }

    @DisplayName("area 사용안함 처리 테스트")
    @Test
    void disableAreaTest() {
        //given
        AreaEntity savedArea = areaRepository.save(areaEntity1);

        //when
        savedArea.disable();
        areaRepository.save(savedArea);

        AreaEntity findArea = areaRepository.findById(savedArea.getId()).get();

        //then
        assertThat(findArea.isAvailable()).isFalse();
    }


}
