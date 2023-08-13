package com.factory.persistence.repository;

import com.factory.persistence.entity.CountedWords;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountedWordsRepository extends CrudRepository<CountedWords, UUID> {
}
