package fr.istic.vv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;


public class PublicElementPrinterPart2 extends VoidVisitorWithDefaults<Void>{

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        if(!declaration.isPublic()) return;

        //Init variables
        List<Field> Fields = new ArrayList<Field>();
        List<Method> Methods = new ArrayList<Method>();
        
        for(BodyDeclaration<?> member : declaration.getFields()) {
            //pass if field is private
            if (!member.isCallableDeclaration()){

                String fieldName = member.toString();
                
                //Remove the doc of the field 
                int index = fieldName.indexOf("/\n", 1);
                if (index != -1){
                    fieldName = fieldName.substring(index+2, fieldName.length());
                }
                
                //Get the name of the field
                int namePlace = 1;
                String [] fieldInfos = fieldName.split(" |\n");
                for (int i = 0; i < fieldInfos.length; i++){
                    if (fieldInfos[i].equals("private") || fieldInfos[i].equals("public") || fieldInfos[i].equals("protected") || fieldInfos[i].equals("static") || fieldInfos[i].equals("final") || fieldInfos[i].equals("@Deprecated")){
                        namePlace++;
                    }
                }
                fieldName = fieldInfos[namePlace];
                if (fieldName.contains(";")){
                    fieldName = fieldName.substring(0, fieldName.length()-1);
                }
                
                //add the field to the list
                Fields.add(new Field(fieldName));
            }
        }

        //Get the methods of the class and their body
        for (BodyDeclaration<?> methode : declaration.getMethods()) {
            String methodBody = methode.toString();
            methodBody = methodBody.split("\\{|\\}")[1];
            methodBody = methodBody.replaceAll(";|\n|[(]|\"|!|[?]|[)]|[.]", " ");
            Methods.add(new Method(new ArrayList<String>(), Arrays.asList(methodBody.split(" "))));
        }
        
        //Check the fields used in the methods
        for (Method method : Methods){
            for (String word : method.body){
                for (Field field : Fields){
                    if (word.equals(field.name)){
                        field.frequency++;
                        method.fields.add(field.name);
                    }
                }
            }
        }

        //Check the methods that are linked by the fields
        for (Method method : Methods){
            for (Method method2 : Methods){
                for (String word : method.fields){
                    for (String word2 : method2.fields){
                        if (word.equals(word2)){
                            method.linkedMethods.add(method2);
                            method2.linkedMethods.add(method);
                        }
                    }
                }
            }
        }

        //Print the results
        int maxLinkedMethods = Methods.size()*(Methods.size()-1)/2;
        int linkedMethods = 0;
        for (Method method : Methods){
            linkedMethods += method.linkedMethods.size();
        }
        linkedMethods = (linkedMethods-1)/2; // Remove the main method and divide by 2 because the linkedMethods are counted twice
        System.out.println("In class "+ declaration.getNameAsString() +" The Fields are : " + Fields.toString());
        System.out.println("The number of maxLinkedMethods is : " + maxLinkedMethods);
        System.out.println("The number of linkedMethods is : " + linkedMethods);
        System.out.println("The TCC is : " + (maxLinkedMethods == 0? 0 :(double)linkedMethods/maxLinkedMethods));
        System.out.println();

        //Sort the fields by their frequency
        Fields.sort((Field f1, Field f2) -> f2.frequency - f1.frequency);


        // Histogram showing the usage of CC values in the project
        for (Field field : Fields) {
            int count = field.frequency;
            System.out.println(field.name + ": " + "*".repeat(count));
        }
        System.out.println();

    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;
        //System.out.println("  " + declaration.getDeclarationAsString(true, true));
    }
    
}
