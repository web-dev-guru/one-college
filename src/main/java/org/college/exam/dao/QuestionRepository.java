package org.college.exam.dao;

import org.college.exam.dao.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionRepository extends MongoRepository<Question,String> {
    Question save(Question question);

}
