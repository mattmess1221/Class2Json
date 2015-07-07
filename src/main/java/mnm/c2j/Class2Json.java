package mnm.c2j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mnm.c2j.asm.ClassJsonVisitor;
import mnm.c2j.json.AnnotationJson;
import mnm.c2j.json.ClassJson;
import mnm.c2j.json.EnumJson;
import mnm.c2j.json.InterfaceJson;
import mnm.c2j.json.PackageJson;

public abstract class Class2Json<T> {

    private List<T> toProcess = Lists.newArrayList();
    private Map<String, PackageJson> processed = Maps.newHashMap();

    public static void main(String[] args) throws IOException {
        Class2Json<String> c2j = new StringClass();
        for (String s : args) {
            c2j.addToProcess(s);
        }
        c2j.process();
        c2j.saveFile(new File("output.json"));
    }

    /**
     * Gets a {@link ClassReader} for the object given. Extenders may want to
     * give the exception a more descriptive message.
     *
     * @param t The object to be read
     * @return A class reader for the object
     * @throws IOException Thrown by ClassReader
     */
    protected abstract ClassReader getClassReader(T t) throws IOException;

    /**
     * Processes the declared objects for class structures.
     */
    public void process() {
        for (T c : toProcess) {
            try {
                ClassReader cr = getClassReader(c);
                ClassJsonVisitor cv = new ClassJsonVisitor();
                cr.accept(cv, ClassReader.SKIP_FRAMES);
                if (!processed.containsKey(cv.getPackage())) {
                    PackageJson j = new PackageJson();
                    processed.put(cv.getPackage(), j);
                }
                PackageJson pkg = processed.get(cv.getPackage());
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
            } catch (SyntheticClassException e) {
                // ignore synthetic classes
            } catch (IOException e) {
                LogManager.getLogger().warn(e);
            }
        }
    }

    /**
     * Adds an object to be processed.
     *
     * @param t The object
     */
    public void addToProcess(T t) {
        toProcess.add(t);
    }

    public Map<String, PackageJson> getProcessed() {
        return processed;
    }

    /**
     * Serializes discovered classes structures and writes it to a file.
     *
     * @param file The file name
     * @throws IOException If a write error occurs
     */
    public void saveFile(File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer w = new FileWriter(file);
        w.write(gson.toJson(processed));
        w.flush();
        w.close();
    }

    /**
     * For a class names on the classpath.
     */
    public static class StringClass extends Class2Json<String> {

        @Override
        protected ClassReader getClassReader(String t) throws IOException {
            return new ClassReader(t);
        }
    }

    /**
     * For byte arrays
     */
    public static class BytesClass extends Class2Json<byte[]> {

        @Override
        protected ClassReader getClassReader(byte[] bytes) throws IOException {
            return new ClassReader(bytes);
        }
    }

    /**
     * For streams
     */
    public static class StreamClass extends Class2Json<InputStream> {

        @Override
        protected ClassReader getClassReader(InputStream t) throws IOException {
            return new ClassReader(t);
        }
    }
}
