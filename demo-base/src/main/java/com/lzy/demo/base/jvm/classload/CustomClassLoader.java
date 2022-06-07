package com.lzy.demo.base.jvm.classload;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CustomClassLoader extends ClassLoader {
    /**
     * {@inheritDoc}
     * 只要重写这个方法就可以了
     * 只有双亲委托加载不到类才会调用这一个
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadClassData(name);
        return super.defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 这里为了测试而重写了这个方法,不然不需要重写此方法
     *
     * @param name name
     * @return class
     * @throws ClassNotFoundException ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name)
            throws ClassNotFoundException {
        if (name.startsWith("com.lzy.demo")) {
            return findClass(name);
        }
        //这里还会加载其它类,因此还要调用super#loadClass()
        return super.loadClass(name);
    }


    /**
     * 这个方法去获取class文件就可以了
     *
     * @param name className
     * @return byte[]
     */
    private byte[] loadClassData(String name) {
        String fileName = name.replace(".", "/") + ".class";
        String basePath = this.getClass().getResource("/").getPath();
        String file = basePath + fileName;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = null;
        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fileChannel = fis.getChannel();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int read;
            while ((read = fileChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                baos.write(byteBuffer.array(), 0, read);
            }
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
