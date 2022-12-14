package com.example.restfulwebservice.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



@Service
public class MemberService {



//=====================================================================================================================


    //임시로 '사용자 Users 들에 대한 리스트 객체'를 여기에 만듦. 관계형 DB 사용은 추후 강의에서 나옴.
    //이건 진짜 임시로만 사용하기 위해 아래처럼 만들어준 것임.
    private static List<Member> members = new ArrayList<>();

    private static int usersCount = 3; //'최초 초기 사용자'는 3명으로 잡음(?)

    static{
        //'User 객체 속 모든 필드들'에 해당하는 값들을 아래에 차례차례 넣어줘야 한다! 안그러면 에러난다!
        members.add(new Member(1, "Kenneth", new Date(), "pass1", "701010-1111111"));
        members.add(new Member(2, "Alice", new Date(), "pass2", "801010-2222222"));
        members.add(new Member(3, "Elena", new Date(), "pass3", "901010-1111111"));
    }


//=====================================================================================================================


    //< 전체 사용자 조회 >
    public List<Member> findAll(){
        return members;
    }


//=====================================================================================================================


    //< 사용자 추가 >
    public Member save(Member member){
        if(member.getId() == null){ //매개변수로 전달된 'user정보 내부'에 '기본키'에 해당하는 id가 존재하지 않는다면,
            member.setId(++usersCount); //여기서 우리가 id값을 하나씩 증가시키면서 해당 사용자에게 id값 부여해줌
        }
        members.add(member);
        return member;
    }


//=====================================================================================================================


    //< 전체 사용자 조회 >
    public Member findOne(int id){
        for(Member member : members){ //int a : intArr  (자료형 변수명(for-each문 내부에서만 통용) : 배열명)
            if(member.getId()==id){ //if, 'User 객체 내부의 id 값' == '메소드 findOne의 매개변수로 들어온 id값' 이라면,
                return member; //해당 user 정보를 리턴해주고,
                             //(='클라이언트가 보낸 특정 user의 id'가 서버에 있을 경우, '그 user 데이터'를 리턴해주고,)
            }
        }
        return null; //같지 않다면, null을 리턴해줌.('클라이언트가 보낸 특정 user의 id'가 서버에 없을 경우, null을 반환해줌)
    }


//=====================================================================================================================


    //< 특정 사용자 삭제 >
    public Member deleteById(int id){ //'매개변수로 들어온 id'에 따라 삭제작업을 진행하겠음
                                    //1.매개변수로 들어온 'id'가 db에 존재하는지 여부를 먼저 파악해야 함
        Iterator<Member> iterator = members.iterator(); //2.'for문' 대신, 이번에는 'iterator 반복자'를 한 번 사용해봄.
                                                    //db 속에 있는 '사용자 전체 users'에 대한 정보를 먼저 하나씩 훑어보고
        while(iterator.hasNext()){
            Member member = iterator.next();

            if(member.getId() == id){ //3.만약, 전달받은 매개변수 id에 해당하는 데이터가 db에 있다면,
                iterator.remove(); //4.그 매개변수 id에 해당하는 id값을 삭제하고

                return member; //5.그 db에서 찾아서 삭제시킨 사용자 데이터값을 리턴해줌.

           }
        }
        return null; //6.만약, 그 전달받은 매개변수 id에 해당하는 데이터가 db에 없다면, null을 리턴해준다!
                     //즉, '해당하는 데이터를 db에서 찾지 못했다!'는 말을 리턴값으로 돌려주는 것임.
    }
}
