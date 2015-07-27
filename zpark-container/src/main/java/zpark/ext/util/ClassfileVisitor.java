package zpark.ext.util;

import java.io.IOException;

public interface ClassfileVisitor {

    public void visit(String className) throws IOException;

}
