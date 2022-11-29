package fr.istic.vv;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        if(!declaration.isPublic()) return;

        //Init variables
        List<String> haveAGetter = new ArrayList<String>();
        List<String> doesnthaveAGetter = new ArrayList<String>();

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
                
                //Check in the methods if there is a getter
                Boolean hasGetter = false;
                searchgetter:
                for(MethodDeclaration method : declaration.getMethods()) {
                    if(method.isPublic()){
                        String methodName = method.getDeclarationAsString(false, false).split(" ")[1];
                        methodName = methodName.substring(0,methodName.indexOf("(",0));
                        if (methodName.compareToIgnoreCase("get" + fieldName) == 0){
                            hasGetter = true;
                            haveAGetter.add(fieldName);
                            break searchgetter;
                        }
                    }
                }
                if (!hasGetter){
                    doesnthaveAGetter.add(fieldName);
                }
            }
        }
        System.out.println("In class "+ declaration.getNameAsString() +" Fields without getter : " + doesnthaveAGetter.toString());
        System.out.println("In class "+ declaration.getNameAsString() +" Fields with getter : " + haveAGetter.toString());
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
