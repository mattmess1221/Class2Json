# Class2Json
Serializes java class structures to json

##Commandline Usage
Main Class: `mnm.c2j.Class2Json`  
Usage: `(<class names>... | -jar <jar file> [<class names>...]) [-out <output file>]`

When not using a jar, class names are from the classpath. If no class names are provided with a jar, all class files are processed.

Options

| Option | Description
|---|---
`-help or -?` | Shows this help screen
`-jar <file>` | The jar to process
`-out <file>` | The output file (default: output.json)

Examples:  
`-out output.json java.util.List java.util.Map java.util.Map$Entry`  
`-jar example.jar -out output.json`  
`-jar example.jar com.example.ExampleClass -out example_class.json`  
