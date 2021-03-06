package cn.myspring.core.type.classreading;

import cn.myspring.core.type.ClassMetadata;
import cn.myspring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 * 获取表示类的元数据
 * 重写了继承自ClassVisitor的visit()
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    //一系列类信息
    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;

    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }

    /**
     * 核心方法
     * 利用ASM给上述定义的类信息赋值
     * className、isInterface、isAbstract、isFinal、superClassName、interfaces
     * @param access bitFiled 用来和某些定义好的常数相与，屏蔽掉某些字段，得到某些属性
     * @param name classpath下的类名 用/分割
     * @param signature
     * @param supername 继承的父类
     * @param interfaces 实现的接口集合
     */
    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        // 用/分割->用.分割
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (supername != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }


    public boolean isConcrete() {
        return !(this.isInterface || this.isAbstract);
    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public boolean isInterface() {
        return isInterface;
    }

    @Override
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public boolean hasSuperClass() {
        return (this.superClassName != null);
    }

    @Override
    public String getSuperClassName() {
        return superClassName;
    }

    @Override
    public String[] getInterfaceNames() {
        return this.interfaces;
    }
}
