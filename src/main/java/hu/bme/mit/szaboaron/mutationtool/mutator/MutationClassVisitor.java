package hu.bme.mit.szaboaron.mutationtool.mutator;

import hu.bme.mit.szaboaron.mutationtool.testanalyzer.MethodId;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class MutationClassVisitor extends ClassVisitor {
    public static final int DEFAULT_API = 262144;
    private MethodId mid;
    public MutationClassVisitor(ClassVisitor classVisitor, MethodId mid) {
        super(DEFAULT_API, classVisitor);
        this.mid=mid;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // System.out.println(name+" "+descriptor);
        // System.out.println(signature);
        return new MutationMethodVisitor(DEFAULT_API, super.visitMethod(access, name, descriptor, signature, exceptions));
    }

    public static void mutateMethod(MethodId mid, String cp) throws IOException {
        File originalFile = new File(cp+"/"+mid.owner+".class");
        File renamedFile = new File(cp+"/"+mid.owner+".class.original");

        ClassReader cr = new ClassReader(new FileInputStream(originalFile));

        ClassWriter cw = new ClassWriter(cr,0);
        MutationClassVisitor cv = new MutationClassVisitor(cw, mid);
        cr.accept(cv, 0);
        byte[] newBytecode = cw.toByteArray();
        originalFile.renameTo(renamedFile);
        FileOutputStream fos = new FileOutputStream(originalFile);
        fos.write(newBytecode);
        fos.flush();
        fos.close();
    }
}