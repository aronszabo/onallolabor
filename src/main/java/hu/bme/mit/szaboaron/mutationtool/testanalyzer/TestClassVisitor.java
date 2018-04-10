package hu.bme.mit.szaboaron.mutationtool.testanalyzer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class TestClassVisitor extends ClassVisitor{
    public static final int DEFAULT_API = 262144;
    List<MethodId> visitedMethods = new LinkedList<>();
    String methodname;
    String testClassName;

    public void pushMethod(MethodId method){
        if(!checkPackage(method.owner))return;
        for(MethodId mid : visitedMethods){
            if(mid.equals(method))return;
        }

        visitedMethods.add(method);
    }
    public TestClassVisitor(ClassVisitor tv, String methodname) {

        super(DEFAULT_API,tv);
        this.methodname=methodname;
    }
    public List<MethodId> getList(){
        return visitedMethods;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(!name.equals(methodname))return super.visitMethod(access, name, descriptor, signature, exceptions);
        return new TestMethodVisitor(DEFAULT_API, super.visitMethod(access, name, descriptor, signature, exceptions),this); //;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        testClassName=name;
        super.visit(version, access, name, signature, superName, interfaces);
    }
    public boolean checkPackage(String owner){
        String package1=owner.substring(0,owner.lastIndexOf("/"));
        String package2=testClassName.substring(0,testClassName.lastIndexOf("/"));
        return package1.equals(package2);

    }
    public static List<MethodId> analyzeTest(File f, String method) throws IOException {
        ClassReader cr = new ClassReader(new FileInputStream(f));

        ClassWriter cw = new ClassWriter(cr,0);
        TestClassVisitor cv = new TestClassVisitor(cw, method);
        cr.accept(cv, 0);
        return cv.getList();
    }
}
