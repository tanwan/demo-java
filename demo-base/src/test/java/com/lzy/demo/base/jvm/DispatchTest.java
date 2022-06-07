package com.lzy.demo.base.jvm;

import com.lzy.demo.base.bean.Animal;
import com.lzy.demo.base.bean.Apple;
import com.lzy.demo.base.bean.Cat;
import com.lzy.demo.base.bean.Dog;
import com.lzy.demo.base.bean.Fruit;
import com.lzy.demo.base.bean.Peach;
import org.junit.jupiter.api.Test;

/**
 * 测试静态分派和动态分派
 *
 * @author lzy
 * @version v1.0
 */
public class DispatchTest {
    private Fruit apple = new Apple("apple");
    private Fruit peach = new Peach("peach");
    private Animal cat = new Cat();
    private Animal dog = new Dog();

    /**
     * 测试分派
     */
    @Test
    public void testDispatch1() {
        //编译时,根据静态多分派,编译成Animal#eatFruit(Fruit)
        //运行时,根据动态单分派,调用实际类型的Cat#eatFruit(Fruit)
        cat.eatFruit(apple);
        cat.eatFruit(peach);
        //编译时,根据静态多分派,编译成Animal#eatFruit(Fruit)
        //运行时,根据动态单分派,调用实际类型的Dog#eatFruit(Fruit)
        dog.eatFruit(apple);
        dog.eatFruit(peach);
    }

    /**
     * 测试分派
     */
    @Test
    public void testDispatch2() {
        //编译时,根据静态多分派,编译成 Cat#eatFruit(Fruit)
        //运行时,根据动态单分派,调用实际类型的Cat#eatFruit(Fruit)
        ((Cat) cat).eatFruit(apple);
        ((Cat) cat).eatFruit(peach);
        //编译时,根据静态多分派,编译成 Dog#eatFruit(Fruit)
        //运行时,根据动态单分派,调用实际类型的Dog#eatFruit(Fruit)
        ((Dog) dog).eatFruit(apple);
        ((Dog) dog).eatFruit(peach);
    }

    /**
     * 测试分派
     */
    @Test
    public void testDispatch3() {
        //编译时,根据静态多分派,编译成 Animal#eatFruit(Apple)
        //运行时,根据动态单分派,调用实际类型的Cat#eatFruit(Apple)
        cat.eatFruit((Apple) apple);
        //编译时,根据静态多分派,编译成 Animal#eatFruit(Apple)
        //运行时,根据动态单分派,调用实际类型的Dog#eatFruit(Apple)
        dog.eatFruit((Apple) apple);
        //编译时,根据静态多分派,编译成 Animal#eatFruit(Peach)
        //运行时,根据动态单分派,调用实际类型的Cat#eatFruit(Peach)
        cat.eatFruit((Peach) peach);
        //编译时,根据静态多分派,编译成 Animal#eatFruit(Peach)
        //运行时,根据动态单分派,调用实际类型的Dog#eatFruit(Peach)
        dog.eatFruit((Peach) peach);
    }

    /**
     * 测试分派
     */
    @Test
    public void testDispatch4() {
        //编译时,根据静态多分派,编译成 Cat#eatFruit(Apple)
        //运行时,根据动态单分派,调用实际类型的Cat#eatFruit(Apple)
        ((Cat) cat).eatFruit((Apple) apple);
        //编译时,根据静态多分派,编译成 Cat#eatFruit(Peach)
        //运行时,根据动态单分派,调用实际类型的Cat#eatFruit(Peach)
        ((Cat) cat).eatFruit((Peach) peach);
        //编译时,根据静态多分派,编译成 Dog#eatFruit(Apple)
        //运行时,根据动态单分派,调用实际类型的Dog#eatFruit(Apple)
        ((Dog) dog).eatFruit((Apple) apple);
        //编译时,根据静态多分派,编译成 Dog#eatFruit(Peach)
        //运行时,根据动态单分派,调用实际类型的Dog#eatFruit(Peach)
        ((Dog) dog).eatFruit((Peach) peach);
    }
}
