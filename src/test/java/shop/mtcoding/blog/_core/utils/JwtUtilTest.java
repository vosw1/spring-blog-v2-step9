package shop.mtcoding.blog._core.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.mtcoding.blog.user.User;

@DataJpaTest
public class JwtUtilTest {
    @Test
    public void create_test() {
        //given
        User user = User. builder ()
                .id(1)
                .username("ssar")
                .build();

        //when
        String jwt = JwtUtil . create (user);
        System.out.println("jwt: " + jwt);
    }
}
