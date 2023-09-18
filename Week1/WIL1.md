# 1. java와 객체지향에 대해서 정리하기 : https://www.notion.so/java-a86f19f9bbc64169925469d6d9676204
나의 노션 링크이다. 여기서 java와 객체 지향에 대해서 정리하고 관련 자료들을 읽어보았다. 이 과정에서 생활코딩의 유튜브를 많이 참고하였다.

# 2. 코드 구현
Main.java 코드
```
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator() {
            @Override
            public int plus(int a, int b) {
                return a+b;
            }
            @Override
            public int minus(int a, int b) {
                return a-b;
            }
            @Override
            public int mul(int a, int b) {
                return a*b;
            }

            @Override
            public int div(int a, int b) {
                return a/b;
            }
        };

        System.out.println("1234 + 4321 = " + calculator.plus(1234, 4321));
        System.out.println("911 - 170 = " + calculator.minus(911, 170));
        System.out.println("2 * 3 = " + calculator.mul(2, 3));
        System.out.println("33 / 11 = " + calculator.div(33, 11));

        System.out.println("\n");

        class Cat extends Animal {
            public void speak(){
                System.out.println("cat says Meow!");
            }
        }
        class Dog extends Animal {
            public void speak(){
                System.out.println("dog says Woof!");
            }
        }
        Cat cat = new Cat();
        Dog dog = new Dog();
        Animal[] animals = {cat, dog};
        for (Animal animal : animals) {
            animal.speak();
        }
    }
}
```
Animal.java 코드
```
public abstract class Animal {
    public abstract void speak();
}
```
Calculator.java 코드
```
public interface Calculator {
    int plus(int a, int b);
    int minus(int a, int b);
    int mul(int a, int b);
    int div(int a, int b);
}
```
