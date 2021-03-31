package com.project.devidea.modules.account;

import com.project.devidea.modules.ModuleGenerator;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class InterestRepositoryTest {
    ModuleGenerator moduleGenerator;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    InterestRepository interestRepository;

    @Autowired
    AccountRepository accountRepository;

    List<Tag> tags;
    Tag spring,java,react,c,etc,python;
    Account account1,account2,account3;
    List<Interest> interestList;
    @BeforeEach
    void init(){
        Random rand=new Random();
         spring=tagRepository.findByFirstName("spring");
         java=tagRepository.findByFirstName("java");
        react= tagRepository.findByFirstName("react");
         c=tagRepository.findByFirstName("c");
         python=tagRepository.findByFirstName("python");
         etc=tagRepository.findByFirstName("etc");
        tags=Arrays.asList(spring,java,react,c);
        account1=moduleGenerator.generateAccount("account1",null);
        account2=moduleGenerator.generateAccount("account2",null);
        account3=moduleGenerator.generateAccount("account3",null);
        List<Account> accountList=Arrays.asList(account1,account2,account3);
        accountRepository.saveAll(accountList);

            interestList=
                accountList.stream()
                .map(account -> {
                    return new Interest()
                            .builder()
                            .account(account)
                            .tag(tags.get(rand.nextInt(4)))
                            .build();

                }).collect(Collectors.toList());
        //공통적으로 다python가지고있음
        List<Interest> common= accountList.stream()
                .map(account -> {
                    return new Interest()
                            .builder()
                            .account(account)
                            .tag(python)
                            .build();

                }).collect(Collectors.toList());
        interestList.addAll(common);
        interestRepository.saveAll(interestList);
    }

    @Test
    @DisplayName("커스텀쿼리: Tag기반 맴버 찾기 ")
    void findAccountByTagContainsTest(){
            //given python포함하는 태그
            Set<Tag> Allinclude=new HashSet<Tag>(Arrays.asList(python));
            Set<Tag> NoneInclude=new HashSet<Tag>(Arrays.asList(etc));
            Set<Tag> AnyOneInclude=new HashSet<Tag>(Arrays.asList(spring,react));
            //when python 포함하는 account찾기
            List<Account> all=interestRepository.findAccountByTagContains(Allinclude);
            List<Account> none=  interestRepository.findAccountByTagContains(NoneInclude);
            List<Account> anyone=  interestRepository.findAccountByTagContains(AnyOneInclude);
            //
            Assertions.assertEquals(all.size(),3);
            Assertions.assertEquals(none.size(),0);
            for(Account account:anyone){
                List<Interest> cur=interestRepository.findByAccount(account);
                cur.stream().forEach(interest -> {
                    Assertions.assertTrue(AnyOneInclude.contains(interest.getTag()) ||
                            interest.getTag()==python);
                });
            }
    }
}
