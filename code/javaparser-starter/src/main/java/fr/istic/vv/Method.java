package fr.istic.vv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Method {
    public ArrayList<String> fields;
    public List<String> body;
    public Set<Method> linkedMethods;

    public Method(ArrayList<String> fields, List<String> body) {
        this.fields = fields;
        this.body = body;
        this.linkedMethods = new HashSet<Method>();
    }
}
