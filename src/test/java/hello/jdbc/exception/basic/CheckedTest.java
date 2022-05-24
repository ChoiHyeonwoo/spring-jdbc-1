package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
public class CheckedTest {
    static class MyCheckedException extends Exception {

        /**
         * Exception을 상속받은 예외는 체크예외가 된다.
         * @param message
         */
        public MyCheckedException(String message) {
            super(message);
        }
    }

    @Test
    void checked_catch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw()  {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow()).isInstanceOf(MyCheckedException.class);
    }
    /**
     * Checked 예외는 잡아서 처리 하거나, 상위로 전달하거나 선택해야한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아 처리하는 코드
         */
        public void callCatch(){
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외처리, 메세지={}", e.getMessage(), e);
            }
        }

        /**
         * 체크예외를 밖으로 던지는 코드
         * 체크예외는 예외를 잡지않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다,.
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }

    }

    static class Repository {
        public void call () throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }
}
