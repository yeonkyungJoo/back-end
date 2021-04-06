package com.project.devidea.modules.content.suggestion;

import com.project.devidea.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    @Query("select s from Suggestion s where s.from =:account or s.to =:account")
    List<Suggestion> findByAccount(@Param("account") Account account);
}
