package hu.bme.mit.szaboaron.mutationtool.testanalyzer;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class TestMethodVisitor extends MethodVisitor {
    TestClassVisitor mtv;
    public TestMethodVisitor(int api, MethodVisitor methodVisitor, TestClassVisitor mtv) {
        super(api, methodVisitor);
        this.mtv=mtv;
    }
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        if(opcode== Opcodes.INVOKEVIRTUAL){
            //System.out.println(MutationMethodVisitor.ops[opcode]+" : "+owner+"/"+name);
            mtv.pushMethod(new MethodId(owner,name,descriptor,isInterface));
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
