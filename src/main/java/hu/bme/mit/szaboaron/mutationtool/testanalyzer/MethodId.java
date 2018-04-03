package hu.bme.mit.szaboaron.mutationtool.testanalyzer;

/**
 * Created by aronszabo on 03/04/2018.
 */
public class MethodId {
    public String owner;
    public String name;
    public String descriptor;
    public boolean isInterface;

    public MethodId(String owner, String name, String descriptor, boolean isInterface) {
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.isInterface = isInterface;
    }
}