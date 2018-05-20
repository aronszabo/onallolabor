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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aronszabo on 02/04/2018.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        List<MethodId> methods;
        if (args.length>1){
            System.out.println("ANALYZING TEST "+args[0]+" METHOD "+args[1]);
             methods = TestClassVisitor.analyzeTest(new File(args[0]), args[1]);
        }else{
            System.out.println("ANALYZING TEST "+args[0]);
             methods = TestClassVisitor.analyzeTest(new File(args[0]), "");
        }

        Mutator mutator = new Mutator();
        mutator.addMutationOperator(Opcodes.IF_ICMPEQ, Opcodes.IF_ICMPNE);
        mutator.addMutationOperator(Opcodes.IF_ICMPNE, Opcodes.IF_ICMPEQ);
        mutator.addMutationOperator(Opcodes.IF_ICMPGE, Opcodes.IF_ICMPLT);
        mutator.addMutationOperator(Opcodes.IF_ICMPLE, Opcodes.IF_ICMPGT);
        mutator.addMutationOperator(Opcodes.IF_ICMPGT, Opcodes.IF_ICMPLE);
        mutator.addMutationOperator(Opcodes.IF_ICMPLT, Opcodes.IF_ICMPGE);
        mutator.addMutationOperator(Opcodes.IFNE, Opcodes.IFEQ);
        mutator.addMutationOperator(Opcodes.IFEQ, Opcodes.IFNE);
        mutator.addMutationOperator(Opcodes.IFGE, Opcodes.IFLT);
        mutator.addMutationOperator(Opcodes.IFLE, Opcodes.IFGT);
        mutator.addMutationOperator(Opcodes.IFGT, Opcodes.IFLE);
        mutator.addMutationOperator(Opcodes.IFLT, Opcodes.IFGE);
        mutator.addMutationOperator(Opcodes.FADD, Opcodes.FSUB);
        mutator.addMutationOperator(Opcodes.DADD, Opcodes.DSUB);
        mutator.addMutationOperator(Opcodes.LADD, Opcodes.LSUB);
        mutator.addMutationOperator(Opcodes.IADD, Opcodes.ISUB);
        mutator.addMutationOperator(Opcodes.FSUB, Opcodes.FADD);
        mutator.addMutationOperator(Opcodes.DSUB, Opcodes.DADD);
        mutator.addMutationOperator(Opcodes.LSUB, Opcodes.LADD);
        mutator.addMutationOperator(Opcodes.ISUB, Opcodes.IADD);
        mutator.addMutationOperator(Opcodes.IFNULL, Opcodes.IFNONNULL);
        mutator.addMutationOperator(Opcodes.IFNONNULL, Opcodes.IFNULL);
        //mutator.addMutationOperator(Opcodes.INVOKEVIRTUAL, Opcodes.NOP);
        //mutator.addMutationOperator(Opcodes.INVOKESTATIC, Opcodes.NOP);
        //mutator.addMutationOperator(Opcodes.INVOKEINTERFACE, Opcodes.NOP);
        String classpath=getClassesPath(new File(args[0]));
        for(MethodId method : methods){
            System.out.println("METHOD "+method.owner+"/"+ method.name);
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
