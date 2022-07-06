package com.lzy.demo.base.misc.generics;

import com.lzy.demo.base.bean.Apple;
import com.lzy.demo.base.bean.Fruit;
import com.lzy.demo.base.bean.Peach;
import com.lzy.demo.base.bean.RedApple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class WildcardTest {
    private List<Fruit> fruits = new ArrayList<>(Collections.singleton(new Fruit("fruit")));
    private List<Apple> apples = new ArrayList<>(Collections.singleton(new Apple("apple")));
    private List<Peach> oranges = new ArrayList<>(Collections.singleton(new Peach("peach")));

    /**
     * 测试通配符的extends
     */
    @Test
    public void testExtent() {
        fruitsExtends(fruits);
        fruitsExtends(apples);
        fruitsExtends(oranges);
    }

    /**
     * 测试通配符的super
     */
    @Test
    public void testSuper() {
        fruitsSuper(fruits);
        fruits.forEach(fruit -> System.out.println("class: " + fruit.getClass().getName() + " ,name: " + fruit.getName()));
    }

    /**
     * 测试PECS
     */
    @Test
    public void testPECS() {
        //PECS
        Predicate<Apple> applePredicate = apple -> apple.getName().equals("apple");
        Predicate<Fruit> fruitPredicate = fruit -> fruit.getName().equals("fruit");
        //Predicate<Orange> orangePredicate = fruit -> fruit.getName().equals("orag");
        //Predicate#and(Predicate<? super T> other)方法中的other参数并没有获取出泛型的数据,而是去使用它,因此是consumer,因此使用super

        System.out.println(applePredicate.and(fruitPredicate).test(new Apple("apple")));
    }


    /**
     * 测试Comparable
     */
    @Test
    public void testComparable() {
        //Apple implement Comparable<Fruit>
        //Apple是Fruit的子类,因此可以转换成Fruit,这里实际调用的是<Fruit>testComparable()而不是<Apple>testComparable(),所以不会报错
        this.<Fruit>testComparable(new Apple("apple"));

        //List<Apple>不是List<Fruit>的子类,这里调用的是<Apple>testListComparable(),要求Apple要实现Comparable<Apple>,因此会报错
        //testListComparable(apples);

        //List<Apple>不是List<Fruit>的子类,这里调用的是<Apple>testListComparableSuper(),要求Apple 要实现 Comparable<? super Apple>,因此不会报错
        testListComparableSuper(apples);

        //Fruit implement Comparable<Fruit>
        //实际调用<Fruit>testComparable()
        testComparable(new Fruit("fruit"));
        //实际调用<Fruit>testListComparable()
        testListComparable(fruits);
        //实际调用<Fruit>testListComparableSuper()
        testListComparableSuper(fruits);
    }


    /**
     * <code><? extends Fruit></code>
     * 可以当入参的有
     * <code>List<Fruit></code>
     * <code>List<Orange></code>
     * <code>List<Apple></code>
     *
     * @param fruits the fruit list
     */
    private void fruitsExtends(List<? extends Fruit> fruits) {
        //读取
        //可以读取出Fruit的类型
        Fruit fruit = fruits.get(0);
        System.out.println("fruit:" + fruit.getName());
        //不能读取出Apple的类型,因为fruits可能是List<Orange>,否则会发生Orange转Apple
        //不能读取出Orange的类型,因此fruits可能是List<Apple>,否则会发生Apple转Orange

        //赋值
        //不能添加Fruit,因为fruits可能是List<Apple>或者List<Orange>,否则会发生Fruit转换成Apple或Orange
        //不能添加Orange,因为fruits可能是List<Apple>,否则会发生Orange转换成Apple
        //不能添加Apple,因为fruits可能是List<Orange>,否则会发生Apple转换成Orange
    }

    /**
     * <code><? super Apple></code>
     * 可以当入参的有
     * <code>List<Apple></code>
     * <code>List<Fruit></code>
     * <code>List<Object></code>
     *
     * @param apples the Apple List
     */
    private void fruitsSuper(List<? super Apple> apples) {
        //读取
        //不能读取出Apple的类型,因为apples可能是List<Fruit>或List<Object>,否则会发生Fruit或Object转Apple
        //不能读取出Fruit的类型,因为apples可能是List<Object>,否则会发生Object转Fruit
        //唯一能读的就是Object类型(建议不要用来强制转换,因为可能会发生不安全的转换)
        Object object = apples.get(0);
        System.out.println("object:" + object);

        //赋值
        //可以添加Apple类型
        apples.add(new Apple("Apple"));
        //也可以添加Apple的子类
        apples.add(new RedApple("redApple"));
        //不能添加Fruit的类型,因为apples可能是List<Apple>,否则会发生Fruit转Apple
        //不能添加Object的类型,因为apples可能是List<Apple>或者List<Fruit>,否则会发生Object转Apple或者Fruit
    }


    /**
     * T要实现<code>Comparable<T></code>
     *
     * @param <T>   the type parameter
     * @param fruit the fruit
     */
    private  <T extends Comparable<T>>  void testComparable(T fruit) {
        System.out.println("class: " + fruit.getClass().getName());
    }

    /**
     * T要实现<code>Comparable<T></code>
     *
     * @param <T>     the type parameter
     * @param animals the animals
     */
    private <T extends Comparable<T>> void testListComparable(List<T> animals) {
        System.out.println("class: " + animals.get(0).getClass().getName());
    }

    /**
     * T要实现<code>Comparable<SuperClass></code>
     * SuperClass是T的父类
     *
     * @param <T>     the type parameter
     * @param animals the animals
     */
    private <T extends Comparable<? super T>> void testListComparableSuper(List<T> animals) {
        System.out.println("class: " + animals.get(0).getClass().getName());
    }
}
