package com.zhy.code_block;

public class CodeBlock {

    int a = 0;
    static int sa = 1;

    public static void main(String[] args) {
        CodeBlock codeBlock = new CodeBlock();
    }

    private CodeBlock() {
        System.out.println("construct method" );
    }

    {
        System.out.println( ++a );
        System.out.println( ++sa);
        System.out.println("construct code block" );
    }

    static {
        //a++; a error
        System.out.println(++sa);
        System.out.println("static code block");
    }

    private void method() {
        synchronized (this) {
            System.out.println("synchronized code block");
        }
    }
}
