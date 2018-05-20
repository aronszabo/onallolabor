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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class MutationClassVisitor extends ClassVisitor {
    public static final int DEFAULT_API = 262144;
    private MethodId mid;
    private Mutation mutation;

    public MutationClassVisitor(ClassVisitor classVisitor, MethodId mid, Mutation m) {
        super(DEFAULT_API, classVisitor);
        this.mid = mid;
        this.mutation = m;
    }

    public static void mutateMethod(MethodId mid, String cp, Mutator mut) throws IOException {
        File originalFile = new File(cp + "/" + mid.owner + ".class");
        File renamedOriginalFile = new File(cp + "/" + mid.owner + ".class.original");
        try {
            Files.copy(originalFile.toPath(), renamedOriginalFile.toPath());
        }catch (FileAlreadyExistsException e){

        }
        Mutation m;
        do {
            m = mut.createMutation();
            ClassReader cr = new ClassReader(new FileInputStream(renamedOriginalFile));
            ClassWriter cw = new ClassWriter(cr, 0);
            MutationClassVisitor cv = new MutationClassVisitor(cw, mid, m);
            cr.accept(cv, 0);
            byte[] newBytecode = cw.toByteArray();
            if (m.label == null) break;
            File mutationFile = new File(cp + "/" + mid.owner + ".class." + m.label + ".mutation");
            System.out.println("WRITTEN " + mutationFile.getName());
            FileOutputStream fos = new FileOutputStream(mutationFile);
            fos.write(newBytecode);
            fos.flush();
            fos.close();
        } while (true);//!m.label.isEmpty()

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        // System.out.println(name+" "+descriptor);
        // System.out.println(signature);
        return new MutationMethodVisitor(DEFAULT_API, super.visitMethod(access, name, descriptor, signature,
                exceptions), mutation, Math.abs((name + descriptor + signature + mid.owner + mid.name).hashCode()));
    }
}
