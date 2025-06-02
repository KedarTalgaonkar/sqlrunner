package com.eeft.ren.sqlrunner.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SqlScriptParser {

    private static final String DELIMITER = ";"; // or customize if your scripts use '/'

    public static List<String> splitSqlScript(String script) {
        List<String> statements = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(script)) {
			while (scanner.hasNextLine()) {
			    String line = scanner.nextLine().trim();
			    if (line.startsWith("--") || line.isEmpty()) continue;
			    sb.append(line).append(" ");
			    if (line.endsWith(DELIMITER)) {
			        statements.add(sb.toString().replace(DELIMITER, "").trim());
			        sb.setLength(0);
			    }
			}
		}
        if (!sb.isEmpty()) {
            statements.add(sb.toString().trim());
        }
        return statements;
    }
}

