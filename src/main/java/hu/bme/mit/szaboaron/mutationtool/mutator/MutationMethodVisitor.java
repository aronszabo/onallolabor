package hu.bme.mit.szaboaron.mutationtool.mutator;

import org.objectweb.asm.*;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class MutationMethodVisitor extends MethodVisitor {
    private static String[] ops = {"NOP", "ACONST_NULL", "ICONST_M1", "ICONST_0", "ICONST_1", "ICONST_2", "ICONST_3",
            "ICONST_4", "ICONST_5", "LCONST_0", "LCONST_1", "FCONST_0", "FCONST_1", "FCONST_2", "DCONST_0", "DCONST_1","BIPUSH","SIPUSH","LDC","","","ILOAD","LLOAD","FLOAD","DLOAD","ALOAD","","","","","","","","","","","","","","","","","","","","",
            "IALOAD", "LALOAD", "FALOAD", "DALOAD", "AALOAD", "BALOAD", "CALOAD", "SALOAD", "ISTORE","LSTORE","FSTORE","DSTORE","ASTORE","","","","","","","","","","","","","","","","","","","","","IASTORE", "LASTORE",
            "FASTORE", "DASTORE", "AASTORE", "BASTORE", "CASTORE", "SASTORE", "POP", "POP2", "DUP", "DUP_X1", "DUP_X2",
            "DUP2", "DUP2_X1", "DUP2_X2", "SWAP", "IADD", "LADD", "FADD", "DADD", "ISUB", "LSUB", "FSUB", "DSUB",
            "IMUL", "LMUL", "FMUL", "DMUL", "IDIV", "LDIV", "FDIV", "DDIV", "IREM", "LREM", "FREM", "DREM", "INEG",
            "LNEG", "FNEG", "DNEG", "ISHL", "LSHL", "ISHR", "LSHR", "IUSHR", "LUSHR", "IAND", "LAND", "IOR", "LOR",
            "IXOR", "LXOR", "IINC", "I2L", "I2F", "I2D", "L2I", "L2F", "L2D", "F2I", "F2L", "F2D", "D2I", "D2L", "D2F", "I2B",
            "I2C", "I2S", "LCMP", "FCMPL", "FCMPG", "DCMPL", "DCMPG", "IFEQ","IFNE","IFLT","IFGE","IFGT","IFLE",
            "IF_ICMPEQ","IF_ICMPNE","IF_ICMPLT","IF_ICMPGE","IF_ICMPGT","IF_ICMPLE","IF_ACMPEQ","IF_ACMPNE","GOTO","JSR",
            "RET","TABLESWITCH","LOOKUPSWITCH","IRETURN", "LRETURN", "FRETURN", "DRETURN",
            "ARETURN", "RETURN", "GETSTATIC", "PUTSTATIC", "GETFIELD", "PUTFIELD", "INVOKEVIRTUAL", "INVOKESPECIAL",
            "INVOKESTATIC","INVOKEINTERFACE","INVOKEDYNAMIC","NEW","NEWARRAY","ANEWARRAY","ARRAYLENGTH", "ATHROW", "CHECKCAST","INSTANCEOF","MONITORENTER", "MONITOREXIT",
            "","MULTIANEWARRAY","IFNULL","IFNONNULL","",""};


    public MutationMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }


    @Override
    public void visitJumpInsn(int opcode, Label label) {
        // System.out.println("  JMP  " + ops[opcode] + " " + label);
        if(opcode==Opcodes.IF_ICMPLE){
            opcode=Opcodes.IF_ICMPGT;
            System.out.println("  JMP  " + ops[opcode] + " " + label);
            System.out.println("MUTATED");
        }
        super.visitJumpInsn(opcode, label);
    }
}