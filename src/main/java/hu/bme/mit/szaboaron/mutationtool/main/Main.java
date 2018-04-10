package hu.bme.mit.szaboaron.mutationtool.main;

import hu.bme.mit.szaboaron.mutationtool.mutator.MutationClassVisitor;
import hu.bme.mit.szaboaron.mutationtool.mutator.Mutator;
import hu.bme.mit.szaboaron.mutationtool.testanalyzer.MethodId;
import hu.bme.mit.szaboaron.mutationtool.testanalyzer.TestClassVisitor;
import hu.bme.mit.szaboaron.mutationtool.testanalyzer.TestMethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by aronszabo on 02/04/2018.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        List<MethodId> methods = TestClassVisitor.analyzeTest(new File(args[0]), args[1]);


        String classpath=getClassesPath(new File(args[0]));
        Mutator mutator = new Mutator();
        mutator.addMutationOperator(Opcodes.IF_ICMPEQ, Opcodes.IF_ICMPNE);
        mutator.addMutationOperator(Opcodes.IF_ICMPNE, Opcodes.IF_ICMPEQ);
        mutator.addMutationOperator(Opcodes.IF_ICMPGE, Opcodes.IF_ICMPLT);
        mutator.addMutationOperator(Opcodes.IF_ICMPLE, Opcodes.IF_ICMPGT);
        mutator.addMutationOperator(Opcodes.IF_ICMPGT, Opcodes.IF_ICMPLE);
        mutator.addMutationOperator(Opcodes.IF_ICMPLT, Opcodes.IF_ICMPGE);

        for(MethodId method : methods){
            MutationClassVisitor.mutateMethod(method,classpath,mutator);
        }

    }
    private static String getClassesPath(File testPath){
        // todo https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
        Path path = Paths.get(testPath.getParent());
        while(!path.endsWith("target"))path=path.getParent();
        return Paths.get(path.toAbsolutePath().toString(),"classes").toString();
    }
}
