package com.factory.persistence;

import com.factory.common.FactoryIntegrationTest;
import com.factory.persistence.entity.CountedWords;
import com.factory.persistence.repository.CountedWordsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@FactoryIntegrationTest
class CountedWordsRepositoryTest {

    @Autowired
    private CountedWordsRepository countedWordsRepository;

    @Test
    void testSaveAndRetrieveCountedWords() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now();
        Long lettersCount = 42L;

        CountedWords countedWords = new CountedWords();
        countedWords.setTimestamp(timestamp);
        countedWords.setLettersCount(lettersCount);

        // When
        var result = countedWordsRepository.save(countedWords);
        CountedWords retrievedCountedWords = countedWordsRepository.findById(result.getId())
                .orElse(null);

        // Then
        assertThat(retrievedCountedWords).isNotNull();
        assertThat(retrievedCountedWords.getTimestamp()).isEqualTo(timestamp);
        assertThat(retrievedCountedWords.getLettersCount()).isEqualTo(lettersCount);
    }
}

