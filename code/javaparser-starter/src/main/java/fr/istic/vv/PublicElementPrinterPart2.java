package fr.istic.vv;

import java.util.ArrayList;
import java.util.List;

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
        List<String> Fields = new ArrayList<String>();
        
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
                
                Fields.add(fieldName);

                
            }
        }
        
        //TODO Get Functions Body and check if they use the field 

        System.out.println("In class "+ declaration.getNameAsString() +" The Fields are : " + Fields.toString());
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
