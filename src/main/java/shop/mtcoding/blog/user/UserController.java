package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // TODO: 회원정보 조회 API 필요 -> @GetMapping("/api/users/{id}")
    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> userinfo(@PathVariable Integer id){
        UserResponse.DTO respDTO = userService.회원조회(id);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserRequest.UpdateDTO reqDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        SessionUser newSessionUser = userService.회원수정(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);

        // 이 친구만 DTO 생성위치 예외
        return ResponseEntity.ok(new ApiUtil(newSessionUser));
    }

    @PostMapping("/join") // DTO로 반환하는게 좋음
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO reqDTO) {
        SessionUser sessionUser = userService.로그인(reqDTO); // 섹션에 저장한 객체가 다라짐 User -> SessionUser
        session.setAttribute("sessionUser", sessionUser);
        return ResponseEntity.ok(new ApiUtil(null));
        // USER는 모든 객체의 부모라 괜찮음 -> 연관된 객체가 없음
        // 연관된 객체가 있다면
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil(null));
    }
}
