package com.example.restfulwebservice.user;

//< Hibernate >
//- 자바에서 db와 관련된 어플리케이션을 개발하기 위해 사용하는 API
//- '자바의 객체'와 'db의 엔티티'를 매핑하기 위한 프레임워크를 제공해줌

//< HTTP 헤더 >
//- HTTP 전송에 필요한 모든 부가 정보
//  e.g) 메시지 바디의 내용, 메시지 바디의 크기, 압축, 인증, 요청 클라이언트, 서버 정보, 캐시 관리 정보 등..
//- 형식
//  e.g) Host: www.google.com


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService){ //생성자를 통한 의존성주입!
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<User> retrieveAllUsers(){

        //return userService.findAll(); //이거를 'ctrl + alt + v' 또는 '마우스 우클릭 -> Refactor -> Introduce Variable' 누르면
                                        //쪼개서 코드 작성할 수 있음. 즉, 바로 아래처럼 할 수 있음.
                                        //List<User> users = userService.findAll();
                                        //return users;
                                        //이렇게 써 줄 수 있다!
        return userService.findAll();
    }


    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){ //'개별 사용자'를 조회

        User user = userService.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }
        return userService.findOne(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
                                        //- @RequestBody : 사용자가 작성하여 보낸 'JSON 객체에 담겨진 내용'이 넘어올 때
                                        //이를 받기 위해 여기에 컨트롤러에 작성함. 전달받고자 하는 이 데이터가
                                        //'RequestBody 형식'의 역할을 하겠다고 선언하는 것.
                                        //'RequestBody'로 'user 엔티티'를 건네줬다는 것은, 클라이언트가 서버로
                                        //'user 엔티티 객체' 내부의 필드들 'name'과 'joinDate'를 JSON으로
                                        //건네줬다는 것임. 즉, postman에 입력되는 json 정보는 예를 들어 아래와 같음.
                                        //{
                                        //    "name": "Yujong",
                                        //    "joinDate": "2020-03-26T10:25:17.407+0000"
                                        //}
                                        //'유효성 체크를 위한 Validation API 사용'강 03:10~
                                        //- @Valid:

        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() //'Http Status Code 제어'강 3:00~'
                                                                        //'새로운 URI'를 생성하는 과정임.                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build(); //'created는

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){

        User user = userService.deleteById(id);

        if(user == null){ //- 'url을 통해 클라이언트가 보낸 '사용자 id''에 해당하는 정보가 db에 존재하지 않는다면,
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); //'UserNotFoundException 에러'를
                                                                                    //발생시켜 준다!

        }
    }
}
