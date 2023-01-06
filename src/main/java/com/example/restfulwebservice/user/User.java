package com.example.restfulwebservice.user;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data//@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 다 포함되어있음.
     //@RequiredArgsConstructor와 @AllArgsConstructor와 @NoArgsConstructor 차이점 확인하기!
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"}) //- JSON 통신을 통해서 프론트로부터 전달받은 데이터들 중에서,
                                                   //보안 상 컨트롤러로 보내고 싶지 않은 데이터를, 그에 해당하는 아래 필드 위에
                                                   //일일이 '@JsonIgnore'를 붙여줘도 되고,
                                                   //아니면 아예 여기 '클래스 블록'으로 '@JsonIgnoreProperties' 적고,
                                                   //괄호 안에 내가 보안 상 컨트롤러에 보내고 싶지 않은 필드를 넣어줘도 된다!
                                                   //포스트맨 결과 화면 조회에서 확인할 수 있음
//그러면, 이제 '클라이언트로부터 User 엔티티 요청'이 넘어올 때, 해당 데이터는 '컨트롤러 UserController'로 넘어가지 않게 된다!

@JsonFilter("UserInfo") //- 여기에 붙게 되는 '필터'은 이제 '컨트롤러'와 'service'에서 사용될 것임.
                        //- "UserInfo": 필터 이름.
                        // '컨트롤러' 또는 'service'에서 '어떤 bean'을 대상으로 사용될 필터인지' 그 필터 이름임.
                        //  여기서는 'AdminUser 객체의 메소드 retrieveUser'에서 사용했음. 해당 부분 확인하기.
public class User {


    private Integer id;
    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.") //'Postman 통신'할 때 전달되는 해당 JSON 데이터의 입력 조건임
    private String name;
    @Past //현재 등록된 회원이 가입했던 날짜는, '미래'가 아니라 당연히 '과거'이기 때문에, '과거 데이터'만 사용 가능하다는 조건 부여함
    private Date joinDate;



    //'Response 데이터 제어를 위한 Filtering'강 05:30~
    //외부에 노출시키고 싶지 않은 데이터인 'password'와 'ssn(주민번호 같은 것)'
    //'@JsonIgnore'를 추가시켜줌으로써,
    //JSON 통신을 통해서 프론트로부터 전달받은 데이터들 중에서, 보안 상 컨트롤러로 보내고 싶지 않은 데이터를,
    //그에 해당하는 아래 필드 위에 일일이 '@JsonIgnore'를 붙여준다!
    //그러면, 이제 '프론트로부터 User 엔티티 정보'가 넘어올 때, 아래 두 데이터는 '컨트롤러 UserController'로 넘어가지 않게 된다!
    //@JsonIgnore
    private String password;
    //@JsonIgnore
    private String ssn; //이렇게 해당 필드에 일일이 '@JsonIgnore'를 붙여줘도 되고, 아니면 아예 저 위에 '클래스 블록'으로 
                        //'@JsonIgnoreProperties' 적고, 괄호 안에 내가 보안 상 가리고 싶은 필드를 넣어줘도 된다!


}
