package com.kuang.other;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: JunYu
 * @Date: 2024/6/7 20:32
 * @Description:
 * @Version: V1.0.0
 */
public class TestEither {
    public static void main(String[] args) {
        // Stream中不允许抛出异常
        List<Either<String, User>> users = Stream.iterate(1, i -> i + 1)
                .limit(100)
                .map(TestEither::readLine)
                .collect(Collectors.toList());

        Either<String, List<User>> either = Either.sequence(users, (s1, s2) -> s1 + "\n" + s2);

        if (either.isLeft()) {
            System.out.println(either.getLeft());
        } else {
            for (User user : either.getRight()) {
                System.out.println(user);
            }
        }

    }

    private static Either<String, User> readLine(int i) {
        if (new Random().nextInt(100) <= 50) {
            return Either.right(new User("张三", 20, 1));
        } else {
            return Either.left("第" + i + "行数据错误");
        }
    }
}
