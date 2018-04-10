package hu.bme.mit.szaboaron.mutationtool.mutator;

/**
 * Created by aronszabo on 10/04/2018.
 */
public class Mutation {
    Mutator mut;
    String label="";

    public Mutation(Mutator mut) {
        this.mut = mut;
    }
    public boolean canMutate(String label, int opcode){
        return (!mut.mutatedLabels.contains(label)) && mut.mutationOperators.containsKey(opcode);
    }
    public int mutate(String label, int opcode){
        this.label=label;
        mut.mutatedLabels.add(label);
        return mut.mutationOperators.get(opcode);

    }
}
