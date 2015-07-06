package mnm.c2j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mnm.c2j.json.AnnotationJson;
import mnm.c2j.json.ClassJson;
import mnm.c2j.json.EnumJson;
import mnm.c2j.json.InterfaceJson;
import mnm.c2j.json.PackageJson;

public class Class2Json {

    public static void main(String[] args) throws IOException {
        Map<String, PackageJson> packages = new HashMap<>();
        for (String c : args) {
            try {
                ClassReader cr = new ClassReader(c);
                ClassJsonVisitor cv = new ClassJsonVisitor();
                cr.accept(cv, ClassReader.SKIP_CODE);
                if (!packages.containsKey(cv.getPackage())) {
                    PackageJson j = new PackageJson();
                    packages.put(cv.getPackage(), j);
                }
                PackageJson pkg = packages.get(cv.getPackage());
                switch (cv.getType()) {
                case CLASS:
                    pkg.addClass((ClassJson) cv.getJson());
                    break;
                case ANNOTATION:
                    pkg.addAnnotation((AnnotationJson) cv.getJson());
                    break;
                case ENUM:
                    pkg.addEnum((EnumJson) cv.getJson());
                    break;
                case INTERFACE:
                    pkg.addInterface((InterfaceJson) cv.getJson());
                    break;
                }
            } catch (IllegalArgumentException e) {

            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer w = new FileWriter("output.json");
        w.write(gson.toJson(packages));
        w.flush();
        w.close();
    }
}
