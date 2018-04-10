package hu.bme.mit.szaboaron.mutationtool.mutator;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class Mutator {
    HashSet<String> mutatedLabels=new LinkedHashSet<>();
    Map<Integer,Integer> mutationOperators=new HashMap<>();
    public Mutation createMutation(){
        return new Mutation(this);
    }
    public void addMutationOperator(int original, int replacement){
        mutationOperators.put(original,replacement);
    }

}
