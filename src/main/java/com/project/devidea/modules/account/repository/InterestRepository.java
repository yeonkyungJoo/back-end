package com.project.devidea.modules.account.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.Interest;
import com.project.devidea.modules.tagzone.tag.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface InterestRepository extends JpaRepository<Interest, Long> {

    @Modifying
    @Query("delete from Interest i where i.account = :account")
    int deleteByAccount(@Param("account") Account account);

    List<Interest> findByAccount(Account account);
    @EntityGraph(attributePaths = {"account"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Interest> findByTag(Tag tag);

    @Query("select i.account from Interest i where i.tag in :tags")
    List<Account> findAccountByTagContains(Set<Tag> tags);
}
